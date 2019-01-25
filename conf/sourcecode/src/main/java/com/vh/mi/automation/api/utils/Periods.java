package com.vh.mi.automation.api.utils;

import com.google.common.base.Strings;
import com.vh.mi.automation.impl.comp.state.Period;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 
 * @author i80448
 *
 */
public class Periods {

	@SuppressWarnings("serial")
	private static Map<String, Integer> monthsMappings = new HashMap<String, Integer>() {
		{
			put("JAN", 1);
			put("FEB", 2);
			put("MAR", 3);
			put("APR", 4);
			put("MAY", 5);
			put("JUN", 6);
			put("JUL", 7);
			put("AUG", 8);
			put("SEP", 9);
			put("OCT", 10);
			put("NOV", 11);
			put("DEC", 12);

		}
	};

	public static Period newPeriod(int fromYear, int fromMonth, int toYear,
			int toMonth) {
		DateTime from = new DateTime(fromYear, fromMonth, 1, 0, 0);
		DateTime to = new DateTime(toYear, toMonth, 1, 0, 0);

		return new Period(from, to);
	}

	public static DateTime fromDateFormatedString(String strDate, String pattern) {
		// "MMM YYYY"
		return DateTimeFormat.forPattern(pattern).parseDateTime(strDate);
	}

	/**
	 * 
	 * @param miAPString
	 *            - Should be in "May 11 thru Apr 14" format.
	 * @return
	 */
	public static Period fromMIAnalysisPeriodString(String miAPString) {
		checkArgument(!Strings.isNullOrEmpty(miAPString),
				"Argument miAPString can not be null/empty.");

		String[] split = miAPString.toUpperCase().trim().split(" ");

		String sMth = split[0];
		int sIntMth = monthsMappings.get(sMth);

		String sYr = split[1];
		int sIntYr = Integer.valueOf(sYr);
		if (sIntYr < 100) {
			// year in two digit.
			sIntYr += 2000;
		}

		String eMth = split[3];
		int eIntMth = monthsMappings.get(eMth);

		String eYr = split[4];
		int eIntYr = Integer.valueOf(eYr);
		if (eIntYr < 100) {
			// year in two digit.
			eIntYr += 2000;
		}

		Period period = newPeriod(sIntYr, sIntMth, eIntYr, eIntMth);

		return period;
	}

	public static void main(String[] args) {
		String periodStr = "May 11 thru Apr 14";
		Period period = Periods.fromMIAnalysisPeriodString(periodStr);
		// System.out.println(period.toString());
		// System.out.println(period.getFromDateAsMonthYearStr());
		// System.out.println(period.getToDateAsMonthYearStr());
//		System.out.println("breakdowns = "
//				+ period.getMonthsBreakDown().toString());

		System.out.println(Periods.fromDateFormatedString("May 2011",
				"MMM YYYY"));
	}
}
