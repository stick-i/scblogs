package cn.sticki.common.pojo;

import java.io.Serializable;
import java.util.List;

public interface IListVO<T> extends Serializable {

	/**
	 * 当前分页总页数
	 */
	default long getPages() {
		if (getSize() == 0) {
			return 0L;
		}
		long pages = getTotal() / getSize();
		if (getTotal() % getSize() != 0) {
			pages++;
		}
		return pages;
	}

	/**
	 * 内部什么也不干
	 * <p>只是为了 json 反序列化时不报错</p>
	 */
	default void setPages(long pages) {
	}

	/**
	 * 分页记录列表
	 *
	 * @return 分页对象记录列表
	 */
	List<T> getRecords();

	/**
	 * 设置分页记录列表
	 */
	void setRecords(List<T> records);

	/**
	 * 当前满足条件总行数
	 *
	 * @return 总条数
	 */
	long getTotal();

	/**
	 * 设置当前满足条件总行数
	 */
	void setTotal(long total);

	/**
	 * 获取每页显示条数
	 *
	 * @return 每页显示条数
	 */
	long getSize();

	/**
	 * 设置每页显示条数
	 */
	void setSize(long size);

	/**
	 * 当前页
	 *
	 * @return 当前页
	 */
	long getCurrent();

	/**
	 * 设置当前页
	 */
	void setCurrent(long current);

}
