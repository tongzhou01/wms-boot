package com.bs.wms.vo;

import java.math.BigDecimal;

/**
 * 统计展示对象
 */
public class CountVO {

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 年
     */
    private Integer year;

    /**
     * 月
     */
    private Integer month;

    /**
     * 日
     */
    private Integer day;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    public CountVO(String companyName, Integer year, Integer month, Integer day, BigDecimal totalAmount) {
        this.companyName = companyName;
        this.year = year;
        this.month = month;
        this.day = day;
        this.totalAmount = totalAmount;
    }

    public CountVO() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
