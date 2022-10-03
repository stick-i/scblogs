package cn.sticki.common.web.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author if
 * @Description: 时间日期工具类并通过ThreadLocal解决DateFormat的线程安全问题
 * ThreadLocal通过保存各个线程的SimpleDateFormat类对象的副本
 * 使每个线程在运行时，各自使用自身绑定的SimpleDateFormat对象
 * 互不干扰，执行性能比较高，推荐在高并发的生产环境使用。
 * @Date 2022-01-08 下午 03:20
 */
public class TimeUtil {

	/**
	 * 时间格式(yyyy-MM-dd)
	 */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	private static final ThreadLocal<DateFormat> DATE_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat(DATE_PATTERN));

	public static DateFormat getDateFormat() {
		return DATE_LOCAL.get();
	}

	/**
	 * 时间格式(yyyy-MM-dd HH:mm:ss)
	 */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final ThreadLocal<DateFormat> DATE_TIME_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat(DATE_TIME_PATTERN));

	public static DateFormat getDateTimeFormat() {
		return DATE_TIME_LOCAL.get();
	}


	/**
	 * 日期格式化
	 *
	 * @param date 日期
	 * @return 默认返回yyyy-MM-dd HH:mm:ss格式日期
	 */
	public static String format(Date date) {
		return format(date, DATE_TIME_PATTERN);
	}

	/**
	 * 计算相差分钟数，并保留两位小数
	 *
	 * @param start 起始时间
	 * @param end   截止时间
	 * @return 保留两位小数
	 */
	public static Double getSubMinutes(Date start, Date end) {
		long seconds = getSubSeconds(start, end);
		BigDecimal b = new BigDecimal(seconds / 60.0D);
		return b.setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 计算相差秒数
	 *
	 * @param start 起始时间
	 * @param end   截止时间
	 * @return 相差秒数
	 */
	public static Long getSubSeconds(Date start, Date end) {
		LocalDateTime startTime = LocalDateTime.parse(format(start), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		LocalDateTime endTime = LocalDateTime.parse(format(end), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		return Duration.between(startTime, endTime).toMillis() / 1000;
	}

	/**
	 * 日期格式化
	 *
	 * @param date    日期
	 * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
	 * @return 返回yyyy-MM-dd格式日期
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		switch (pattern) {
			case (DATE_PATTERN):
				return DATE_LOCAL.get().format(date);
			case (DATE_TIME_PATTERN):
				return DATE_TIME_LOCAL.get().format(date);
			default:
				return null;
		}
	}

	/**
	 * 判断今天是否在两个日期范围之内
	 *
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 */
	public static Boolean isTodayBetweenTwoDays(Date startTime, Date endTime) {
		return isSomeDayBetweenTwoDays(new Date(), startTime, endTime);
	}

	/**
	 * 判断某个日期是否在两个日期范围之内
	 *
	 * @param someTime  某个日期
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 */
	public static Boolean isSomeDayBetweenTwoDays(Date someTime, Date startTime, Date endTime) {
		return someTime.getTime() >= startTime.getTime() && someTime.getTime() <= endTime.getTime();
	}
}