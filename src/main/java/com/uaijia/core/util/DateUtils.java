package com.uaijia.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("deprecation")
public class DateUtils extends org.apache.commons.lang.time.DateUtils {	
	public static final Date  DEFAULT_DATE_TIME;
    public static final String DEFAULT_DATE_PARTTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_SIMPLE_DATE_DAYTIME = " 00:00:00";
    public static final String DEFAULT_CHINA_DATE_PARTTERN = "yyyy年MM月dd日";
    //时间格式（可用于借款编号申请等）
    public static final String DEFAULT_SHORT_DATE = "yyyyMMddHHmmssSSS";
	
	static{
		Calendar cc  = Calendar.getInstance();
		cc.set(1900, 1,1);
		DEFAULT_DATE_TIME = cc.getTime();
	}
	
	public static Date parseDate(String str,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String toString(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		return sdf.format(date);
	}
	
	public static Date now(){
		return new Date();
	}
	
	public static Timestamp nowTimestamp(){
		Date date = now();
		return new Timestamp(date.getTime());
	}
	
	public static Timestamp fromDateToTimestamp(Date date){
		return new Timestamp(date.getTime());
	}
	
	public static int dayOfWeek(){
		return dayOfWeek(now());
	}
	
	public static int dayOfWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int di = cal.get(Calendar.DAY_OF_WEEK);
		return di;
	}
	
	public static Date thisWeekStart(){		
		int di = dayOfWeek();
		if(di > 0){
			return onlyDate(DateUtils.addDays(now(), -di + 1));
		}else{
			return onlyDate(now());
		}
	}
	
	public static Date thisWeekEnd(){
		int di = dayOfWeek();
		di = 6 - di + 1;
		
		if(di > 0){
			return onlyDate(DateUtils.addDays(now(), di));
		}else{
			return onlyDate(now());
		}
	}
	
	/**
	 * 每月最后一天
	 * @param date
	 * @return
	 */
	public static Date monthEnd(Date date){
		Calendar cDay1 = Calendar.getInstance();  
        cDay1.setTime(date);  
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);  
        Date lastDate = cDay1.getTime();  
        lastDate.setDate(lastDay);  
        return lastDate;
	}
	
	public static Date onlyDate(Date date){
		return new Date(date.getYear(),date.getMonth(),date.getDate());
	}
	
	/**
	 * 字符串转化为Timestamp
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Timestamp parseTimestamp(String str,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return fromDateToTimestamp(sdf.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static long daysBetween(Date date1,Date date2){
		return (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24)	;
	}
	
	public static long daysBetweenStrict(Date date1,Date date2){
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long differ = time2 - time1;
		long timeofday = 1000 * 60 * 60 * 24;
		long days = differ / timeofday;
		if(differ % timeofday > 0){
			days += 1;
		}
		
		return days;
		
	}
	
	/**
	 * 计算date2 - date1 = **天**小时
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String daysAndHoursBetween(Date date1,Date date2){
		String hours = (date2.getTime() - date1.getTime()) % (1000 * 60 * 60 * 24) / (date2.getTime() - date1.getTime()) % (1000 * 60 * 60)+ "";
		return (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24)	+ "天" + hours + "小时" ;
	}
	
	public static String dateToCron(Date date){
		String dateFormat="ss mm HH dd MM ? yyyy";  
        return toString(date, dateFormat);  
	}
	
	//获取当前时间的前后N天
	public static Date getNextDay(Date date,int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, num);
		date = calendar.getTime();
		return date;
	}
	
	public static Date addWorkDays(Date date,int num){
		if(num == 0) return date;
		
		Date r = addDays(date,0);
		int i = 0;
		while(true){
			r = addDays(r,1);
			
			int dow = DateUtils.dayOfWeek(r);
			if(dow == Calendar.SATURDAY || dow == Calendar.SUNDAY){
				continue;
			}
			
			i++;
			if(i == num)break;
		}
		return r;
	}
}
