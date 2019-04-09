package com.bs.wms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat datetime = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    public static Date getNowDate() {
        return new Date();
    }
    // ****************************当前时间相关****************************

    /***
     *
     * @param date 时间
     * @return  cron类型的日期
     */
    public static String getCron(final Date  date){
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * 获得以 yyyy-MM-dd 为形式的当前时间字符串
     *
     * @return String
     */
    public static String getCurrentTimeByDay() {
        String time = date.format(new Date(System.currentTimeMillis()));
        return time;
    }

    /**
     * 获得以 yyyy-MM-dd HH:mm:ss 为形式的当前时间字符串
     *
     * @return String
     */
    public static String getCurrentTimeBySecond() {
        String time = datetime.format(new Date(System.currentTimeMillis()));
        return time;
    }

    /**
     * 获得给定格式的当前时间字符串
     *
     * @param give String 给定的时间格式
     * @return String
     */
    public static String getCurrentTime(String give, Date date) {
        SimpleDateFormat temp = new SimpleDateFormat(give);
        return temp.format(date);
    }

    // ****************************String转换为Date****************************

    /**
     * 将String转化成date
     *
     * @throws ParseException
     */
    public static Date pStringToDate(String str, String sfgs)
            throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(sfgs);
        return sf.parse(str);
    }

    /**
     * 将String转化成date 格式为yyyy-MM-dd hh:mm:ss
     *
     * @throws ParseException
     */
    public static Date pStringToDate(String str) throws ParseException {
        return datetime.parse(str);
    }

    // ****************************Date转换为String****************************

    /**
     * 转换成日期格式的字符串 格式为yyyy-MM-dd
     *
     * @param o
     * @return String
     */
    public static String dateFormat(Date o) {
        if (o == null) {
            return "";
        }
        return date.format(o);
    }

    /**
     * 转换成时间格式的字符串 格式为yyyy-MM-dd hh:mm:ss
     *
     * @param o
     * @return String
     */
    public static String dateTimeFormat(Date o) {
        if (o == null) {
            return "";
        }
        return datetime.format(o);
    }

    /**
     * 转换成给定时间格式的字符串
     *
     * @param d
     * @param format
     * @return String
     */
    public static String getDateFormat(Date d, String format) {
        return new SimpleDateFormat(format).format(d);
    }

    /**
     * 日期格式化(yyyy年MM月dd日)
     *
     * @param date
     * @return String
     */
    public static String fDateCNYR(Date date) {
        return getDateFormat(date, "yyyy年MM月dd日");
    }

    /**
     * 日期格式化(yyyy年MM月dd日 HH:mm)
     *
     * @param date
     * @return String
     */
    public static String fDateCNYRS(Date date) {
        return getDateFormat(date, "yyyy年MM月dd日 HH点");
    }

    /**
     * 日期格式化(yyyy年MM月dd日 HH:mm)
     *
     * @param date
     * @return String
     */
    public static String fDateCNYRSF(Date date) {
        return getDateFormat(date, "yyyy年MM月dd日 HH:mm");
    }

    /**
     * 日期格式化(yyyy年MM月dd日 HH:mm:ss)
     *
     * @param date
     * @return String
     */
    public static String fDateCNYRSFM(Date date) {
        return getDateFormat(date, "yyyy年MM月dd日 HH:mm:ss");
    }

    // ****************************时间格式的String转换为String****************************

    /**
     * 根据给定的时间格式字符串截取给定格式的字符串
     *
     * @param d      String 给定时间格式为yyyy-MM-dd HH:mm:ss
     * @param format String 给定的格式
     * @return String
     */
    public static String getDateFormat(String d, String format)
            throws ParseException {
        Date date = datetime.parse(d);
        return getDateFormat(date, format);
    }

    /**
     * 获取当前时间戳，单位秒
     *
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间戳，单位毫秒
     *
     * @return
     */
    public static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }

    /**
     * 获取下个月
     * @param date
     * @param m
     * @return
     */
    public static Date getNextMonth(Date date, int m) {
        Calendar calendar  =   Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, m);
        return calendar.getTime();
    }


    /**
     * 获取日期是哪一天
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据 年、月 获取对应的月份 的 天数
     */
    public static int getDaysByYearMonth(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
