package com.malaxiaoyugan.test.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * description: DateUtils
 * date: 2020/7/7 14:29
 * author: juquansheng
 * version: 1.0 <br>
 */
public class DateUtilsByYuuki {
    public static ThreadLocal<DateFormat> formatThreadLocalYYYYMMDDHHMMSS =  new ThreadLocal<DateFormat>() {
        @Override
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    public static ThreadLocal<DateFormat> formatThreadLocalYYYYMMDD =  new ThreadLocal<DateFormat>() {
        @Override
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    //string类型转为localDate 参数可以是字符串时间戳，也可以是字符串时间
    public static LocalDate stringToLoaclDate(String dateString){
        return stringToDate(dateString).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    //string类型转为localDate 参数可以是字符串时间戳，也可以是字符串时间
    public static LocalDateTime stringToLoaclDateTime(String dateString){
        return stringToDate(dateString).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    //string类型转为Date 参数可以是字符串时间戳，也可以是字符串时间
    public static Date stringToDate(String dateString){
        if(null == dateString) {
            return null;
        }
        Date date;
        try {
            date = new Date(Long.parseLong(dateString));
        }catch (Exception e){
            try {
                date = formatThreadLocalYYYYMMDDHHMMSS.get().parse(dateString);
            } catch (ParseException ex) {
                try {
                    date = formatThreadLocalYYYYMMDD.get().parse(dateString);
                }catch (Exception e1){
                    return null;
                }
            }
        }
        return date;
    }

    //获取当前时间字符串
    public static String getNowDateString(){
        return formatThreadLocalYYYYMMDDHHMMSS.get().format(new Date());
    }

    /**
     * 获取当天开始时间
     * @param date
     * @return
     */
    public static Date getStartTime(Date date) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        return dateStart.getTime();
    }
    /**
     * 获取当天结束时间
     * @param date
     * @return
     */
    public static Date getEndTime(Date date) {
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTime(date);
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        return dateEnd.getTime();
    }

    /**
     * 获取相差几天时间
     * @param date
     * @return
     */
    public static Date getDayDate(Date date,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
    /**
     * 获取相差几个月时间
     * @param date
     * @return
     */
    public static Date getMonthDate(Date date,int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 获取相差几天时间
     * @param date
     * @return
     */
    public static String getDayDateString(Date date,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return formatThreadLocalYYYYMMDD.get().format(calendar.getTime());
    }

    /**
     * 计算2个日期之间相差的天数
     *
     * @param smallDate
     *            较小的日期
     * @param bigDate
     *            较大的日期
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smallDate, Date bigDate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        smallDate = sdf.parse(sdf.format(smallDate));
        bigDate = sdf.parse(sdf.format(bigDate));

        Calendar calendar = Calendar.getInstance(Locale.CHINA);

        calendar.setTime(smallDate);
        long smallDateTime = calendar.getTimeInMillis();

        calendar.setTime(bigDate);
        long bigDateTime = calendar.getTimeInMillis();

        long betweenDays = (bigDateTime - smallDateTime) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     *
     * @param smallDate
     *            较小的日期
     * @param bigDate
     *            较大的日期
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String smallDate, String bigDate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance(Locale.CHINA);

        calendar.setTime(sdf.parse(smallDate));
        long smallDateTime = calendar.getTimeInMillis();

        calendar.setTime(sdf.parse(bigDate));
        long bigDateTime = calendar.getTimeInMillis();

        long betweenDays = (bigDateTime - smallDateTime) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     *
     * @param date
     * @throws
     */
    public static LocalDateTime dateTolocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    //获取当月开始时间
    public static Date getDateStartOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND,0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
    //获取当月结束时间
    public static Date getDateEndOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        //设置最后一天
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至0
        c.set(Calendar.MINUTE, 59);
        //将秒至0
        c.set(Calendar.SECOND,59);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    //获取日期年月
    public static String getMonthString(Date date) {
        String format = formatThreadLocalYYYYMMDD.get().format(date);

        return format.substring(0,7);
    }

    public static void main(String[] args) {
        Date date = new Date();
        Date dateStartOfMonth = getDateStartOfMonth(date);
        Date dateEndOfMonth = getDateEndOfMonth(date);
        System.out.println(formatThreadLocalYYYYMMDDHHMMSS.get().format(dateStartOfMonth));
        System.out.println(formatThreadLocalYYYYMMDDHHMMSS.get().format(dateEndOfMonth));
        String monthString = getMonthString(date);
        System.out.println(monthString);
    }
}
