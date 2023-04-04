package cn.jokeo.lovepig.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.jokeo.lovepig.common.exception.LoveException;
import cn.jokeo.lovepig.config.LoveConfig;
import cn.jokeo.lovepig.entity.enums.LoveErrorEnum;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Date;

/**
 * 腾讯云oss组件
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/22 23:21
 */
@Component
@ConfigurationProperties(prefix = "love.tencent-cos")
@Data
@Slf4j
public class TencentOssUtil {
    //COSClient 是线程安全的类，允许多线程访问同一实例。
    // 因为实例内部维持了一个连接池，创建多个实例可能导致程序资源耗尽，请确保程序生命周期内实例只有一个，并在不再需要使用时，
    // 调用 shutdown 方法将其关闭。如果需要新建实例，请先将之前的实例关闭。
    private static COSClient cosClient = null;

    private String platform;
    private String enableStorage;
    private String secretId;
    private String secretKey;
    private String region;
    private String bucketName;
    private String domain;
    private String basePath;


    @Resource
    private LoveConfig loveConfig;


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 初始化客户端
     */
    public void init() {
        //如果客户端存在。不要再创建了
        if (cosClient != null) {
            return;
        }
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台进行查看和管理
        // String secretId = properties.getSecretId();
        // String secretKey = properties.getSecretKey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(this.region);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
    }

    /**
     * 上传文件(时间戳文件名)
     *
     * @param file     文件流
     * @param size     文件长度
     * @param filePath 上传文件路径
     * @return
     */
    public PutObjectResult upload(InputStream file, long size, String filePath) {
        init();
        // 指定文件将要存放的存储桶
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(size);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath.toString(), file, metadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        close();
        return putObjectResult;
    }

    /**
     * 构建上传到云服务器的路径
     * 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
     *
     * @param fileName 原文件名
     * @return
     */
    public String genFilePath(String fileName) {
        String dataDir = DateUtil.format(new Date(), "yyyyMM");
        String dataName = DateUtil.format(new Date(), "ddHHmmss");
        StringBuilder filePath = new StringBuilder(basePath);
        filePath.append(dataDir)
                .append("/")
                .append(dataName)
                .append(RandomUtil.randomNumbers(4))
                .append(".")
                .append(FileUtil.extName(fileName));
        return filePath.toString();
    }

    /**
     * 删除一个文件
     *
     * @param filePath 指定被删除的文件在 COS 上的路径
     */
    public void delete(String filePath) {
        init();
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        // 指定被删除的文件在 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示删除位于 folder 路径下的文件 picture.jpg
        cosClient.deleteObject(bucketName, filePath);
        close();
    }

    /**
     * (建议用完关闭)关闭客户端
     */
    public void close() {
        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();
        cosClient = null;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file) {
        Assert.isTrue(!file.isEmpty(), "上传的文件不能为空!");
        String filePath = genFilePath(file.getOriginalFilename());
        //保存文件到oss
        try {
            upload(file.getInputStream(), file.getSize(), filePath);
            String fileUrl = loveConfig.getStaticUrl() + filePath;
            log.info("向腾讯云cos上传文件,路径为:" + fileUrl);
            return fileUrl;
        } catch (Exception e) {
            log.error("上传文件异常:" + e.getMessage());
            throw new LoveException(LoveErrorEnum.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 处理文件类型逻辑
     *
     * @param filename
     */
    public int handleFileType(String filename) {
        String type = FileUtil.extName(filename);
        if (loveConfig.getImageTypes().contains(type)) {
            return 0;
        } else if (loveConfig.getVideoTypes().contains(type)) {
            return 1;
        } else {
            throw new LoveException(LoveErrorEnum.FILE_TYPE_ERROR);
        }
    }

}
