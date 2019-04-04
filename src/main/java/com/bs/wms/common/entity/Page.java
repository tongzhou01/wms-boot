package com.bs.wms.common.entity;

import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;

public class Page<T> extends RowBounds {

    /**
     * 页编号 : 第几页
     */
    private int pageNum = 1;

    /**
     * 页大小 : 每页的数量
     */
    private int pageSize = 10;

    /**
     * 查询结果
     */
    private List<T> rows = new ArrayList<T>();

    /**
     * 总条数
     */
    private int total;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 设置总记录数.
     */
    private void setTotal(List<T> rows) {
        if (rows instanceof com.github.pagehelper.Page) {
            this.total = (int)((com.github.pagehelper.Page)rows).getTotal();
            this.totalPages = ((com.github.pagehelper.Page)rows).getPages();
        } else {
            this.total = rows.size();
            this.totalPages = 1;
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
        setTotal(rows);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
