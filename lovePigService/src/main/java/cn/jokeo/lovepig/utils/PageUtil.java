package cn.jokeo.lovepig.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author joke
 */
public class PageUtil<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页数
     */
    private int currPage;
    /**
     * 列表数据
     */
    private List<T> data;

    /**
     * 分页
     *
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     * @param list       列表数据
     */
    public PageUtil(int totalCount, int pageSize, int currPage, List<T> list) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
        this.data = list;
    }

    /**
     * 将mybatis的IPage转换成我们需要的数据对象
     */
    public PageUtil(IPage<T> page) {
        this.data = page.getRecords();
        this.totalCount = (int) page.getTotal();
        this.pageSize = (int) page.getSize();
        this.currPage = (int) page.getCurrent();
        this.totalPage = (int) page.getPages();
    }

    /**
     * 页面显示的起始页码
     *
     * @return
     */
    public int getStart() {
        int start = currPage - 2;
        if (currPage == totalPage) {
            start = currPage - 4;
        } else if (currPage == totalPage - 1) {
            start = currPage - 3;
        }
        return start < 1 ? 1 : start;
    }

    /**
     * 页面显示的结束页码
     *
     * @return
     */
    public int getEnd() {
        int end = currPage + 2;
        if (currPage == 1) {
            end = currPage + 4;
        } else if (currPage == 2) {
            end = currPage + 3;
        }
        return end > totalPage ? totalPage : end;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
