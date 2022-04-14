package com.carpark_hdb.rest.common.utils;

import java.time.Instant;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class MapComparatorByDate implements Comparator<Map<String, Object>>
{
	private final String key;

	public MapComparatorByDate(String key)
	{
		this.key = key;
	}

	public int compare(Map<String, Object> first,
			Map<String, Object> second)
	{
		String firstValue = (String) first.getOrDefault(key, null);
		String secondValue = (String) second.getOrDefault(key,null);
		if(Objects.isNull(firstValue) || Objects.isNull(secondValue)) {
			throw new InternalError();
		}
		if(!firstValue.contains("Z")) {
			firstValue += ".000Z";
		}
		if(!secondValue.contains("Z")) {
			secondValue += ".000Z";
		}
		Instant firstDate = DateTimeConvertor.getDateFromStringDate(firstValue);
		Instant secondDate = DateTimeConvertor.getDateFromStringDate(secondValue);
		return secondDate.compareTo(firstDate);
	}
}