package com.carpark_hdb.rest.common.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.carpark_hdb.constants.AppConstants;

/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class DateTimeConvertor {
	public static Instant getDateFromStringDate(String date) {
		Instant parsedDate = Instant.parse(date);
		return parsedDate;
	}
	
	public static Instant getCurrentInstantAsPerDefaultTZ() {
		ZoneId z = ZoneId.of(AppConstants.DEFAULT_TIMEZONE) ;
		ZonedDateTime zdtNow = ZonedDateTime.now(z) ;
		Instant instantNow = zdtNow.toInstant() ;  // Adjust from time zone to UTC. Same moment, different wall-clock time.
		return instantNow;
	}

}
