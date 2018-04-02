package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateUtils {
    
    private static Logger log = LogManager.getLogger(DateUtils.class);

    public static String format(Date date, String format){
        String result = "";
        try {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            result = sf.format(date);
        } catch (Exception e) {
            log.error("输入格式有误,请检查格式:" + format);
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 将日期时间(格式：yyyyMMddHHmmss)转换为字符串时间
     * @param date 
     * @return yyyyMMddHHmmss
     */
    public static String date2string(Date date) {
        if(date == null){
            date = new Date();
        }
        return format(date, "yyyyMMddHHmmss");
    }

    /**
     * 将字符串日期(格式：yyyyMMddHHmmss)转换为日期时间
     * @param datestr
     * @return
     */
    public static Date string2date(String datestr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date datetime = null;
        try {
            datetime = sdf.parse(datestr);
        } catch (ParseException e) {
            log.error("将字符串日期转换为日期时间发生异常，异常信息：" + e);
        }
        return datetime;
    }
    
    /**
     * 将字符串日期(格式：yyyyMMddHHmmss)转换为时间戳
     * @param datestr
     * @return
     */
    public static long string2timestampt(String datestr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        long timestampt = 0000000001;
        try {
            timestampt = sdf.parse(datestr).getTime()/1000;
        } catch (ParseException e) {
            log.error("将字符串日期转换为时间戳发生异常，异常信息：" + e);
            e.printStackTrace();
        }
        return timestampt;
    }

    /**
     * 将日期时间(格式：yyyy-MM-dd HH:mm:ss)转换为字符串时间
     * @param date 
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String date2string2(Date date) {
        if(date == null){
            date = new Date();
        }
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 将字符串日期(格式：yyyy-MM-dd HH:mm:ss)转换为日期时间
     * @param datestr
     * @return
     */
    public static Date string2date2(String datestr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime = null;
        try {
            datetime = sdf.parse(datestr);
        } catch (ParseException e) {
            log.error("将字符串日期转换为日期时间发生异常，异常信息：" + e);
            e.printStackTrace();
        }
        return datetime;
    }
    
    /**
     * 将字符串日期(格式：yyyy-MM-dd HH:mm:ss)转换为时间戳
     * @param datestr
     * @return
     */
    public static long string2timestampt2(String datestr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timestampt = 0000000001;
        try {
            timestampt = sdf.parse(datestr).getTime()/1000;
        } catch (ParseException e) {
            log.error("将字符串日期转换为时间戳发生异常，异常信息：" + e);
            e.printStackTrace();
        }
        return timestampt;
    }

    /**
     * 将字符串日期转换为指定格式的日期时间
     * @param datestr   待转换字符串日期
     * @param format    自定义日期的格式
     * @return
     */
    public static Date string2dateCustomer(String datestr, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date datetime = null;
        try {
            datetime = sdf.parse(datestr);
        } catch (ParseException e) {
            log.error("将字符串日期转换为自定义日期时间发生异常，异常信息：" + e);
            e.printStackTrace();
        }
        return datetime;
    }

    /**
     * 将日期时间转成指定格式的字符串日期
     * @param date      待转换日期时间
     * @param format    自定义日期的格式
     * @return
     */
    public static String date2stringCustomer(Date date, String format) {
        if(date == null){
            date = new Date();
        }
        return format(date, format);
    }

    /**
     * 将一种格式字符串日期转成另外一种格式字符串日期
     * @param datestr       带转换字符串日期
     * @param oldformat     带转换字符串日期格式
     * @param newformat     自定义日期的格式
     * @return
     */
    public static String formatChange(String datestr, String oldformat, String newformat){
        SimpleDateFormat sdf = new SimpleDateFormat(oldformat);
        Date datetime = null;
        try {
            datetime = sdf.parse(datestr);
            if(datetime == null){
                datetime = new Date();
            }
        } catch (ParseException e) {
            log.error("将格式为：" + oldformat + "的字符串日期转换为日期时间发生异常，异常信息：" + e);
        }

        return format(datetime, newformat);
    }

    /**
     * 时间差是否超过设定的分钟数
     * @param time1         对比时间
     * @param time2         被对比时间
     * @param minutes       时间间隔，分钟数
     * @return
     */
    public static boolean compareTime(Date time1, Date time2, int minutes){
        boolean flag = false;

        if(null != time1 && null != time2){
            long timestamp1 = time1.getTime();
            long timestamp2 = time2.getTime();

            long difference = Math.abs(timestamp1 - timestamp2);
            if(difference > minutes*60*1000){
                flag = true;
            }
        }else{
            log.error("TimeUtils.compareTime() 对比的两个时间不能为空！");
        }
        return flag;
    }

    /**
     * 时间相差毫秒数
     * @param time1         对比时间
     * @param time2         被对比时间
     * @return
     */
    public static long timeDifference(Date time1, Date time2){
        long difference = 0l;
        if(null != time1 && null != time2){
            long timestamp1 = time1.getTime();
            long timestamp2 = time2.getTime();

            difference = (timestamp1 - timestamp2);

        }else{
            log.error("TimeUtils.timeDifference() 对比的两个时间不能为空！");
        }
        return difference;
    }

    /**
     * 获取文件创建时间
     * @param filePath  文件路径
     */
    public static String getFileCreateTime(String filePath){
        String createtime = "";
        try {  
            Process p = Runtime.getRuntime().exec("cmd /C dir " + filePath + "/tc" );
            InputStream is = p.getInputStream();   
            BufferedReader br = new BufferedReader(new InputStreamReader(is));             
            String line;
            while((line = br.readLine()) != null){  
                if(line.endsWith(".csv")){
                    createtime = line.substring(0,17).replace("-", "/") + ":00";
                    break;
                }
            }
        } catch (Exception e) {
            log.info("获取文件创建时间发生异常，异常信息：" + e);
        }
        return createtime;
    }
}
