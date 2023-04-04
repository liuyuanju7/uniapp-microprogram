package cn.jokeo.lovepig.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author joke
 */
@Data
public class PageWebUtil<T> implements Serializable {
    private static final long serialVersionUID = -8611487541762626314L;
    /**
     * 总记录数
     */
    private long totalCount;
    /**
     * 列表数据
     */
    private List<T> data;

    /**
     * 分页
     *
     * @param totalCount 总记录数
     * @param list       列表数据
     */
    public PageWebUtil(long totalCount, List<T> list) {
        this.totalCount = totalCount;
        this.data = list;
    }

    /**
     * 将mybatis的IPage转换成我们需要的数据对象
     */
    public PageWebUtil(IPage<T> page) {
        this.data = page.getRecords();
        this.totalCount = page.getTotal();
    }

}
