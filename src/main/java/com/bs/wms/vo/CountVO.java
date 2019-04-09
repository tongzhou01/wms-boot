package com.bs.wms.vo;

import java.math.BigDecimal;

public class CountVO {

    private String companyName;

    private Integer year;

    private Integer month;

    private Integer day;

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
