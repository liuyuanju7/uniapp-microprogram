package cn.jokeo.lovepig.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: joke
 * @time: 2020/6/8 10:52
 */
public class UploadUtil {
    /**
     * 上传文件
     *
     * @param file         文件
     * @param request      请求
     * @param abstractPath 上传的相对路径
     * @return
     */
    public static R UploadImg(MultipartFile file, HttpServletRequest request, String abstractPath) {
        String name = file.getOriginalFilename();
        String xts = name.substring(name.lastIndexOf('.'));
        if (!(".png".equals(xts) || ".jpg".equals(xts) || ".jpeg".equals(xts))) {
            return R.fail("非法文件类型!");
        }
        return upload(file, request, abstractPath);

    }

    /**
     * 上传文件不做类型判断
     *
     * @param file
     * @param request
     * @param abstractPath
     * @return
     */
    public static R upload(MultipartFile file, HttpServletRequest request, String abstractPath) {
        String realPath = request.getServletContext().getRealPath(abstractPath);
        String timePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File dir = new File(realPath + "/" + timePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = file.getOriginalFilename();
        String xts = name.substring(name.lastIndexOf('.'));
        String timeName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + xts;
        File saveFile = new File(realPath + "/" + timePath, timeName);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            return R.fail(e.getMessage());
        }
        return R.ok("", abstractPath + "/" + timePath + "/" + timeName);
    }

    public static String downloadAvatar(String url, HttpServletRequest request) {
        String avatarPath = "/upload/user/";
        String realPath = request.getServletContext().getRealPath(avatarPath);
        String timePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File dir = new File(realPath + "/" + timePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BufferedImage bufferedImage = null;
        try {
            //获取大图
            url = url.substring(0, url.lastIndexOf('/') + 1) + "0";
            bufferedImage = ImageIO.read(new URL(url));
            String timeName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".jpg";
            File file = new File(dir.getAbsolutePath(), timeName);
            boolean jpg = ImageIO.write(bufferedImage, "jpg", file);
            return avatarPath + timePath + "/" + timeName;
        } catch (IOException e) {
            return "";
        }

    }

    /**
     * 获取文章中的临时图片地址替换为新的图片的地址
     *
     * @param path
     * @param html
     * @param request
     * @return
     */
    public static Map<String, String> downloadArticle(String path, String html, HttpServletRequest request) {
        List<String> imgs = getImgSrc(html);
        Map<String, String> result = new HashMap<>();
        String avatarPath = "/upload/punch/";
        String realPath = request.getServletContext().getRealPath(avatarPath);
        String timePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File dir = new File(realPath + "/" + timePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BufferedImage bufferedImage = null;

        try {
            for (String img : imgs) {
                //获取大图
                img = img.substring(0, img.lastIndexOf('/') + 1) + "0";
                bufferedImage = ImageIO.read(new URL(img));
                String xts = img.substring(img.lastIndexOf('.'));
                String timeName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + xts;
                File file = new File(dir.getAbsolutePath(), timeName);
                boolean jpg = ImageIO.write(bufferedImage, xts.substring(1), file);
                if (jpg)
                    result.put(img, "https://s.jokeo.cn" + avatarPath + timePath + "/" + timeName);
            }
            return result;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }


    }


    /**
     * 获取html中的图片地址
     *
     * @param htmlStr
     * @return
     */
    public static List<String> getImgSrc(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }
}
