package com.bs.wms.constant;

import java.io.File;

public class BaseConstant {

    /**
     * 用户名
     */
    public static final String USERNAME = "username";

    /**
     * 真实姓名
     */
    public static final String REAL_NAME = "realName";

    /**
     * 邮箱用户名
     */
    public static final String EMAIL_USERNAME = "568309196@qq.com";

    /**
     * 邮箱密码
     */
    public static final String EMAIL_PASSWORD = "";

    /**
     * 邮箱密码
     */
    public static final String EMAIL_HOST_NAME = "smtp.qq.com";//smtp.163.com

    /**
     * 邮箱授权码
     */
    public static final String EMAIL_AUTH = "eninbfljnwusbefe";

    /**
     * 主体
     */
    public static final String EMAIL_PERSONAL = "订单管理系统";

    /**
     * 端口号
     */
    public static final Integer EMAIL_PORT = 465;

    /**
     * 日期格式化
     */
    public static final String YEAR_DAY_FORMAT = "yyyy-MM";

    /**
     * 图表类型
     */
    public static final String LINE_TYPE = "line";

    /**
     * 生成的EXCEL保存路径
     */
    public static final String FILE_OUTPUT_EXCEL_PATH = File.separator.concat("Users").concat(File.separator).concat("zhangcj").concat(File.separator).concat("Downloads").concat(File.separator).concat("excel").concat(File.separator);

    /**
     * 商品excel标题
     */
    public static final String[] ITEM_TITLE = {"序号", "品名规格", "预定数量", "配送数量", "单价", "金额", "备注"};
}
