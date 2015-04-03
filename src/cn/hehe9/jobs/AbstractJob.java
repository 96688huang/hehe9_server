package cn.hehe9.jobs;

import cn.hehe9.common.utils.DateUtil;

public abstract class AbstractJob {
	public long getUsedTimeAsSecond(long startTime) {
		return DateUtil.getIntervalSecond(startTime, System.currentTimeMillis());
	}
}
