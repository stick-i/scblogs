package cn.sticki.blog.util;

import cn.sticki.blog.exception.userException.SqlLimitException;

public class SqlUtils {

	/**
	 * 生成limit语句
	 *
	 * @param count 总条数
	 * @param page  当前请求的页
	 * @param size  当前请求的页大小
	 * @return sql字句，如 limit 10,10
	 */
	public static String limit(Long count, Long page, Long size) {
		String sql;
		// 小于等于1默认返回第一页
		if (page <= 1) {
			if (count > size)
				sql = "limit " + size;
			else
				sql = "";
		} else {
			// 总数大于当前的请求
			if (count < (page - 1) * size)
				throw new SqlLimitException();
			else
				sql = "limit" + (page - 1) * size + "," + size;
		}
		return sql;
	}

}
