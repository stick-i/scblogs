package cn.sticki.common.redis.utils;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/21 21:58
 */
public interface ILock {

	/**
	 * 尝试获取锁
	 *
	 * @param timeout 锁持有的超时时间，过期后自动释放，防止服务器宕机导致死锁
	 * @return true代表获取锁成功; false代表获取锁失败
	 */
	boolean tryLock(long timeout);

	/**
	 * 释放锁
	 */
	void unlock();

}
