package cn.sticki.blog.utils;

/**
 * @author durance
 * @version 1.0
 * @date 2022/10/5 17:31
 */
public class RankKeyUtils {

	/**
	 * 获取 day key
	 */
	public static long getDayKey() {
		return System.currentTimeMillis() / (1000 * 60 * 60 * 24);
	}

	/**
	 * 获取上一周的 week key
	 */
	@SuppressWarnings("AlibabaUndefineMagicConstant")
	public static long getWeekKey() {
		// 拿到 week key,和day key
		long week = System.currentTimeMillis() / (1000 * 60 * 60 * 24 * 7);
		long day = getDayKey();
		// 时间戳取模到周key，不是刚好从周一算起的，如果dayKey%7==4，这样这天才是刚好周一
		// 由于原来的week key并不是从周一开始算起，所以进行移位计算，拿到上周的week key
		long key = day % 7;
		if (key >= 4) {
			// 经计算，取模值大于等于 4 时 进入新的一周，将week key-1
			return week - 1;
		}
		//否则，在新的一周的周四时，week值将再加1，所以要拿到上一周的week key需要-2
		return week - 2;
	}

}
