package cn.hehe9.service.job.base;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
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
	protected long FUTURE_TIME_OUT = 10 * 60 * 1000;
	protected long FUTURE_TASK_CHECK_INTERVAL = 5000;

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
		if (StringUtils.isBlank(calcDate)) {
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
			Logger logger, String logMsg) {
		// 等待被唤醒(被唤醒后, 重置计数器)
		int currCount = 0;
		synchronized (syncObj) {
			try {
				logger.info(logMsg);
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
				if (StringUtils.isNotBlank(logMsg)) {
					logger.debug(logMsg);
				}
				syncObj.notifyAll();
			}
		}
	}

	protected void sleep(long millis, Logger logger) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 等待future task 完成, 或者超时返回.
	 *
	 * @param page
	 * @param source
	 * @param videoList
	 * @param futureList
	 * @param logger
	 * @param prefixLog
	 * @param partLog
	 */
	protected void waitForFutureTasksDone(List<Future<Boolean>> futureList, Logger logger, String prefixLog,
			String partLog) {
		long startTime = System.currentTimeMillis();
		while (true) {
			boolean isAllDone = true;
			for (Future<Boolean> future : futureList) {
				if (!future.isDone()) {
					isAllDone = false;
					break;
				}
			}

			long timeUsed = (System.currentTimeMillis() - startTime) / 1000;

			// 所有任务都已完成
			if (isAllDone) {
				logger.info(new StringBuilder(prefixLog).append(", ALL DONE. ").append(partLog).append(", timeUsed = ")
						.append(timeUsed).append(" s").toString());
				return;
			}

			// 任意一个未完成时, 判断是否超过最大等待时间, 若未超过, 则等待, 若超过, 则跳出循环;
			if (timeUsed >= FUTURE_TIME_OUT) {
				logger.error(new StringBuilder(prefixLog).append(", TIME OUT. ").append(partLog)
						.append(", timeUsed = ").append(timeUsed).append(" s").toString());

				// 取消运行未完成的 future task
				cancelNotDoneFutures(futureList);
				break;
			}

			// 睡眠一段时间
			logger.info(new StringBuilder(prefixLog).append(", not done yet. ").append(partLog).append(", timeUsed = ")
					.append(timeUsed).append(" s").append(", sleep ...").toString());
			sleep(FUTURE_TASK_CHECK_INTERVAL, logger);
		}
	}

	private void cancelNotDoneFutures(List<Future<Boolean>> futureList) {
		for (Future<Boolean> future : futureList) {
			if (!future.isDone()) {
				future.cancel(true);
			}
		}
	}
}
