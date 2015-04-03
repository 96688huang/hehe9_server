package cn.hehe9.common.log4j_ext;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *  RollingPastCalendar is a helper class to DailyMaxRollingFileAppender.
 *  Given a periodicity type and the current time, it computes the
 *  past maxBackupIndex date.
 * */
public class RollingPastCalendar extends RollingCalendar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5445043935339468416L;

	RollingPastCalendar() {
		super();
	}

	RollingPastCalendar(TimeZone tz, Locale locale) {
		super(tz, locale);
	}

	public long getPastCheckMillis(Date now, int maxBackupIndex) {
		return getPastDate(now, maxBackupIndex).getTime();
	}

	public Date getPastDate(Date now, int maxBackupIndex) {
		this.setTime(now);

		switch (type) {
		case RollingCalendar.TOP_OF_MINUTE:
			this.set(Calendar.SECOND, this.get(Calendar.SECOND));
			this.set(Calendar.MILLISECOND, this.get(Calendar.MILLISECOND));
			this.set(Calendar.MINUTE, this.get(Calendar.MINUTE) - maxBackupIndex);
			break;

		case RollingCalendar.TOP_OF_HOUR:
			this.set(Calendar.MINUTE, this.get(Calendar.MINUTE));
			this.set(Calendar.SECOND, this.get(Calendar.SECOND));
			this.set(Calendar.MILLISECOND, this.get(Calendar.MILLISECOND));
			this.set(Calendar.HOUR_OF_DAY, this.get(Calendar.HOUR_OF_DAY) - maxBackupIndex);
			break;

		case RollingCalendar.HALF_DAY:
			this.set(Calendar.MINUTE, this.get(Calendar.MINUTE));
			this.set(Calendar.SECOND, this.get(Calendar.SECOND));
			this.set(Calendar.MILLISECOND, this.get(Calendar.MILLISECOND));
			int hour = get(Calendar.HOUR_OF_DAY);
			if (hour < 12) {
				this.set(Calendar.HOUR_OF_DAY, 12);
			} else {
				this.set(Calendar.HOUR_OF_DAY, 0);
			}
			this.set(Calendar.DAY_OF_MONTH, this.get(Calendar.DAY_OF_MONTH) - maxBackupIndex);

			break;

		case RollingCalendar.TOP_OF_DAY:
			this.set(Calendar.HOUR_OF_DAY, this.get(Calendar.HOUR_OF_DAY));
			this.set(Calendar.MINUTE, this.get(Calendar.MINUTE));
			this.set(Calendar.SECOND, this.get(Calendar.SECOND));
			this.set(Calendar.MILLISECOND, this.get(Calendar.MILLISECOND));
			this.set(Calendar.DATE, this.get(Calendar.DATE) - maxBackupIndex);
			break;

		case RollingCalendar.TOP_OF_WEEK:
			this.set(Calendar.DAY_OF_WEEK, getFirstDayOfWeek());
			this.set(Calendar.HOUR_OF_DAY, this.get(Calendar.HOUR_OF_DAY));
			this.set(Calendar.MINUTE, this.get(Calendar.MINUTE));
			this.set(Calendar.SECOND, this.get(Calendar.SECOND));
			this.set(Calendar.MILLISECOND, this.get(Calendar.MILLISECOND));
			this.set(Calendar.WEEK_OF_YEAR, this.get(Calendar.WEEK_OF_YEAR) - maxBackupIndex);
			break;

		case RollingCalendar.TOP_OF_MONTH:
			this.set(Calendar.DATE, this.get(Calendar.DATE));
			this.set(Calendar.HOUR_OF_DAY, this.get(Calendar.HOUR_OF_DAY));
			this.set(Calendar.MINUTE, this.get(Calendar.MINUTE));
			this.set(Calendar.SECOND, this.get(Calendar.SECOND));
			this.set(Calendar.MILLISECOND, this.get(Calendar.MILLISECOND));
			this.set(Calendar.MONTH, this.get(Calendar.MONTH) - maxBackupIndex);
			break;

		default:
			throw new IllegalStateException("Unknown periodicity type.");
		}

		return getTime();
	}
}
