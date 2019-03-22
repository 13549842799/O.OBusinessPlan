package com.oo.businessplan.common.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.text.ParseException;
import java.util.Locale;


//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


public class DateUtil {

//    static Logger logger = LogManager.getLogger(DateUtil.class);

    public static Date getStartDate(Date date){

        date.setHours(0);
        date.setSeconds(0);
        date.setMinutes(0);
        return date;

    }

    public static Date getEndDate(Date date){

        date.setHours(23);
        date.setSeconds(59);
        date.setMinutes(59);
        return date;

    }

    public static String getAmountDate(int field, int amount, DateFormat format)
    {
        return getAmountDate(field, amount, format.toString());
    }

    public static String getAmountDate(int field, int amount, String format)
    {
        String strDate = new SimpleDateFormat(format).format(getAmountDate(field, amount));
        return strDate;
    }

    public static String getAmountDate(Date date, int field, int amount, DateFormat format)
    {
        return getAmountDate(date, field, amount, format.toString());
    }

    public static String getAmountDate(Date date, int field, int amount, String format)
    {
        String strDate = new SimpleDateFormat(format).format(getAmountDate(date, field, amount));
        return strDate;
    }

    public static Date getAmountDate(int field, int amount)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(field, amount);
        return cal.getTime();
    }

    public static Date getAmountDate(Date date, int field, int amount)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

    public static String formatDate(DateFormat format, Date date)
    {
        return formatDate(format.toString(), date);
    }

    public static String formatDate(String format, Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false);
        try
        {
            return formatter.format(date);
        }
        catch (Exception ex)
        {
//            logger.fatal(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        return null;
    }

    public static String formatDate(String inFormat, String toFormat, String strDate)
    {
        String formatDate = strDate;
        SimpleDateFormat formatter = new SimpleDateFormat(toFormat);
        formatter.setLenient(false);
        try
        {
            formatDate = formatter.format(parseDate(strDate, inFormat));
        }
        catch (Exception ex)
        {
//            logger.fatal(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        return formatDate;
    }

    public static String getSomeDay(Date date, String dateFormat, int days)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static String getDateByMonth(int count)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, count);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }

    public static Date getTimesMonthmorning()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date parseDate(String strDate, String format)
    {
        Date date = null;
        try
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            date = simpleDateFormat.parse(strDate);
        }
        catch (Exception ex)
        {
//            logger.fatal(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        return date;
    }

    public static long parseDatetoLong(String strDate, DateFormat format)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format.toString());
        try
        {
            return simpleDateFormat.parse(strDate).getTime();
        }
        catch (ParseException e)
        {
//            logger.fatal(e.getMessage(), e);
            e.printStackTrace();
        }
        return 0;
    }

    public static Date parseDate(String strDate, DateFormat format)
    {
        return parseDate(strDate, format.toString());
    }

    public static Date parseDate(Date date, String format)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String strDate = simpleDateFormat.format(date);
        return parseDate(strDate, format.toString());
    }

    public static long getDistance(Date startDate, Date endDate, int field)
    {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(startDate);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(endDate);
        int[] p1 = {c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DATE),
                c1.get(Calendar.HOUR)};
        int[] p2 = {c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DATE),
                c1.get(Calendar.HOUR)};

        long distance = 0;
        switch (field)
        {
            case Calendar.YEAR:
                distance = p2[0] - p1[0];
                break;
            case Calendar.MONTH:
                distance = p2[0] * 12 + p2[1] - p1[0] * 12 - p1[1];
                break;
            case Calendar.DAY_OF_WEEK:
                distance = (long)Math.ceil(
                        (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24 * 7.0));
                break;
            case Calendar.DATE:
                distance = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
                break;
            case Calendar.HOUR:
                distance = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);
                break;
            default:
                distance = 0;
                break;
        }
        return distance;
    }

    public static String getTomorrow(String dateFormat)
    {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    /**
     * getDateTomorrow:(获取指定日期的明天).
     *
     * @author zev
     * @param dateStr
     * @param dateFormat
     * @return
     * @since JDK 1.8
     */
    public static String getDateTomorrow(String dateStr, String dateFormat)
    {
        Date date = parseDate(dateStr, dateFormat);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    // 获取当前时间所在年的周数
    public static int getWeekOfYear(Date date)
    {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    // 获取当前时间所在年的最大周数
    public static int getMaxWeekNumOfYear(int year)
    {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return getWeekOfYear(c.getTime());
    }

    /**
     * getDateFromYmdToYmdhms yyyy-MM-dd To yyyy-MM-dd HH:mm:ss
     *
     * @param dateI
     * @return
     */
    public static Date getDateFromYmdToYmdhms(String dateI)
    {
        return parseDate(dateI + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * getDateFromYmdToYmdhms yyyy-MM-dd To yyyy-MM-dd HH:mm:ss
     *
     * @param dateI
     * @return
     */
    public static Date getDateFromYmdToYmd(String dateI)
    {
        return parseDate(dateI, "yyyy-MM-dd ");
    }

    /**
     * @param dateI
     * @param dateFormat
     * @param dis
     * @return
     */
    public static Date getDateDistance(Date dateI, String dateFormat, int dis)
    {
        Date date = parseDate(dateI, dateFormat);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dis);// 把日期往后增加dis.整数往后推,负数往前移动
        return calendar.getTime();
    }

    public static Date getDateDistanceFromYmdToYmdhms(String dateI, int dis, String dateFormat)
    {
        return getDateDistance(getDateFromYmdToYmdhms(dateI + " 00:00:00"), dateFormat, dis);
    }

    public static String formatMYDDate(Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String formatMYDDDate(Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String formatyyyyMMddHHmmss(Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    public static String formatyyyy_MM_dd_HH_mm_ss(Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    // 获取指定日期间日期字符串数组 ["2017-01-01","2017-01-01","2017-01-03", ...]
    public static String[] getDateArray(String beginDate, String endDate)
    {
        Calendar beginCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        Date begin = DateUtil.parseDate(beginDate, "yyyy-MM-dd");
        Date end = DateUtil.parseDate(endDate, "yyyy-MM-dd");
        beginCal.setTime(begin);// 设置开始日期
        endCal.setTime(end);// 设置结束日期

        long between_days = (endCal.getTimeInMillis() - beginCal.getTimeInMillis())
                / (1000 * 3600 * 24);
        int size = Integer.parseInt(String.valueOf(between_days)) + 1;
        if (size < 1)
        {
            return null;
        }
        String[] dateArray = new String[size];
        for (int i = 0; i < size; i++ )
        {
            if (i > 0)
            {
                beginCal.add(Calendar.DAY_OF_MONTH, 1);
            }
            Date begintDate = beginCal.getTime();
            String dateStr = DateUtil.formatMYDDDate(begintDate);
            dateArray[i] = dateStr;
        }
        return dateArray;
    }

    public static void main(String[] args)
    {
//        Date date = DateUtil.parseDateFromWX("20180504121212");
//        System.out.println(getDisMonthDate(date,2));
//        String dateStr = formatyyyyMMddHHmmss(new Date());
//        System.out.println(dateStr);
//        Date date = DateUtil.parseDate(new Date(), "yyyy-MM-dd");
        String dateStr = DateUtil.formatyyyy_MM_dd_HH_mm_ss(new Date());
        System.out.println(dateStr);
    }

    /**
     * 获取某个时间往后多少天
     *
     * @param calDate
     * @param addDate
     * @return
     */
    public static Date addCalendarDay(Date calDate, long addDate)
    {
        long time = calDate.getTime();
        addDate = addDate * 24 * 60 * 60 * 1000;
        time += addDate;
        return new Date(time);
    }

    /**
     * 获取两个时间间隔天数
     * @return
     */
    public static int getIntervalDays(Date maxDate, Date minDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            minDate = sdf.parse(sdf.format(minDate));
            maxDate = sdf.parse(sdf.format(maxDate));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(minDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(maxDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取两个时间间隔小时
     * @return
     */
    public static int getIntervalHours(Date maxDate, Date minDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            minDate = sdf.parse(sdf.format(minDate));
            maxDate = sdf.parse(sdf.format(maxDate));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(minDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(maxDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String addMonth(Date date, int num)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        return formatter.format(date);
    }

    public static String getDateYesterday(String dateFormat)
    {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static Date getYesterday()
    {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        return date;
    }


    public static Date getDisMonthDate(Date date, int num)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);
        return calendar.getTime();
    }

    /**
     * 时间戳转换为指定格式的Date
     * @param timestamp
     * @param formatStr
     * @return
     */
    public static Date parseDateFromTimestamp(String timestamp, String formatStr){
        SimpleDateFormat format =  new SimpleDateFormat(formatStr);
        Long time = new Long(timestamp);
        String dateStr = format.format(time);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将微信接口中返回的时间格式转换为普通时间格式
     * 例: 20180504121212 --- 2018-05-04 12:12:12
     * @param datetime
     * @return
     */
    public static Date parseDateFromWX(String datetime){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime ldt = LocalDateTime.parse(datetime,dtf);

        DateTimeFormatter fa = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String datetime2 = ldt.format(fa);
        Date date = DateUtil.parseDate(datetime2, "yyyy-MM-dd HH:mm:ss");
        return date;
    }

    /**
     * 获取指定日期一定周期后的结束日期
     * 例: 指定日期：2018-05-04 12:12:12 周期数：3 周期：年
     *     结束日期：2021-05-04 12:12:12
     * @param startDate
     * @param number
     * @param period
     * @return
     */
    public static Date getEndDate(Date startDate, int number, int period){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        switch (period){
            case 1:
                calendar.add(Calendar.YEAR, number);
                break;
            case 2:
                calendar.add(Calendar.MONTH, number);
                break;
            case 3:
                calendar.add(Calendar.WEEK_OF_YEAR, number);
                break;
            case 4:
                calendar.add(Calendar.DATE, number);
                break;
        }
        startDate = calendar.getTime();
        return startDate;
    }

    public static String getDateFromCST(String dateStr, String formatStr){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String resultDate = new SimpleDateFormat(formatStr).format(date);
        return resultDate;
    }

}
