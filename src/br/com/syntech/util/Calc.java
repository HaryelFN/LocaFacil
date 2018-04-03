package br.com.syntech.util;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Calc {

	public static long calendarDaysBetween(Calendar startCal, Calendar endCal) {

		//Create copie calendar
		Calendar start = Calendar.getInstance();
		
		start.setTimeZone(startCal.getTimeZone());
		start.setTimeInMillis(startCal.getTimeInMillis());

		Calendar end = Calendar.getInstance();
		
		end.setTimeZone(endCal.getTimeZone());
		end.setTimeInMillis(endCal.getTimeInMillis());

		// Set the copies to be at midnight, but keep the day information.

		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);

		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);

		// At this point, each calendar is set to midnight on
		// their respective days. Now use TimeUnit.MILLISECONDS to
		// compute the number of full days between the two of them.

		return TimeUnit.MILLISECONDS.toDays(Math.abs(end.getTimeInMillis() - start.getTimeInMillis()));
	}

}
