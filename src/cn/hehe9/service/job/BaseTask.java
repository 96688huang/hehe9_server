package cn.hehe9.service.job;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;

import cn.hehe9.common.utils.DateUtil;

public class BaseTask {

	/** 换行符 */
	protected String LINE_SEP = SystemUtils.LINE_SEPARATOR;

	/** 每次查询出公会列表的数量 */
	protected final int QUERY_GUILD_COUNT_PER_TIME = 500;

	/** 昨天结束时间 */
	protected Long yesterdayEnd = null;

	/** 当前时间 */
	protected Long today = null;

	protected Long todayEnd = null;

	protected int CONN_TIME_OUT = 5000;
	protected int RECONN_COUNT = 3;
	protected long RECONN_INTERVAL = 2000;

	/**
	 * 创建计数器
	 * @return
	 */
	protected AtomicInteger createCouter() {
		return new AtomicInteger(0);
	}

	/**
	 * 创建同步锁对象
	 * @return
	 */
	protected Object createSyncObject() {
		return new Object();
	}

	/**
	 * 初始化
	 */
	protected void init(String calcDate) {
		if (StringUtils.isEmpty(calcDate)) {
			yesterdayEnd = DateUtil.getYesterdayEnd().getTime();
			today = System.currentTimeMillis();
		} else {
			yesterdayEnd = DateUtil.getDayEnd(DateUtil.formatStrToDate(calcDate, "yyyyMMdd")).getTime();
			today = DateUtil.getDayStart(DateUtil.getDayAfter(new Date(yesterdayEnd), 1)).getTime();
		}
		todayEnd = DateUtil.getDayEnd(new Date(today)).getTime();
	}

	/**
	 * 等待被唤醒
	 * @param counter			计数器
	 * @param totalCount		总数
	 * @param syncObj			同步锁对象
	 * @param isCalcByMember	是否按成员计算
	 * @return 最后计算的个数
	 */
	protected int waitingForNotify(AtomicInteger counter, int totalCount, Object syncObj, String logPrefix,
			Logger logger) {
		// 等待被唤醒(被唤醒后, 重置计数器)
		int currCount = 0;
		synchronized (syncObj) {
			try {
				syncObj.wait();
			} catch (InterruptedException e) {
				logger.error(logPrefix + "线程在等待被唤醒时, 发生异常.", e);
			}
			currCount = counter.get();
			counter.set(0);
		}
		return currCount;
	}

	/**
	 * 如果子线程计算的对象(例如 成员, 公会等)个数达到总数, 则唤醒主线程
	 * @param counter			计数器
	 * @param totalCount		总数
	 * @param syncObj			同步锁对象
	 * @param logMsg			日志信息
	 */
	protected void notifyMasterThreadIfNeeded(AtomicInteger counter, int totalCount, Object syncObj, String logMsg,
			Logger logger) {
		int executedCount = counter.incrementAndGet();
		if (executedCount == totalCount) {
			synchronized (syncObj) { // 计算最后一个对象的线程, 负责唤醒主线程
				if (StringUtils.isNotEmpty(logMsg)) {
					logger.debug(logMsg);
				}
				syncObj.notifyAll();
			}
		}
	}

}
