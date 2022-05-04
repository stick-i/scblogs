package cn.sticki.blog.enumeration.type;

/**
 * 博客状态类型
 */
public enum BlogStatusType {

	/**
	 * 博客总数，全部博客
	 */
	ALL(0),

	/**
	 * 公开博客
	 */
	PUBLISH(1),

	/**
	 * 未发表的（草稿箱）
	 */
	DRAFT(2),

	/**
	 * 私有的，仅自己可见
	 */
	PERSONAL(3),

	/**
	 * 被删除的（回收站）
	 */
	DELETED(4),

	/**
	 * 审核中的
	 */
	AUDIT(5);

	private Integer value;

	BlogStatusType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
