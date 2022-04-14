package com.carpark_hdb.rest.common.utils;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;

import com.carpark_hdb.constants.APIConstants;
import com.carpark_hdb.rest.common.exceptions.RecordNotFoundException;
import com.carpark_hdb.rest.dto.CarParkAvailabilityDTO;

/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class CarParkAvailabilityUtils {
	private ExternalAPI externalAPI = new ExternalAPI();

	@SuppressWarnings("unchecked")
	public List<CarParkAvailabilityDTO> fetchCarAvailability() {
		String api_path = APIConstants.CAR_PARK_HDB_BASE_API+APIConstants.CAR_PARK_HDB_CHECK_AVAILABILITY_URI ;
		List<CarParkAvailabilityDTO> carParkAvailabilityDtos = new ArrayList<CarParkAvailabilityDTO>();

		String dateString = DateTimeConvertor.getCurrentInstantAsPerDefaultTZ().toString();
		LinkedMultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
		vars.add("date_time", dateString);

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		HashMap<String, ArrayList<HashMap<String, Object>>> json = externalAPI.requestRestServerWithGetMethod(api_path, headers, vars, HashMap.class);
		ArrayList<HashMap<String, Object>> items = json.getOrDefault("items", new ArrayList<HashMap<String,Object>>());
		if(items.isEmpty()) {
			throw new RecordNotFoundException("No car park availability information available.");
		}
		HashMap<String, Object> carParkAllInfo = items.get(0);
		ArrayList<HashMap<String, Object>> carParkAvailabilityInfo = (ArrayList<HashMap<String, Object>>) carParkAllInfo.getOrDefault("carpark_data", new ArrayList<HashMap<String,Object>>());
		if(carParkAvailabilityInfo.isEmpty()) {
			throw new RecordNotFoundException("No car park availability information available.");
		}
		/***
		 * keys in car par availability
		 * car_park_info: list, carpark_number, updated_datetime
		 */
		Collections.sort(carParkAvailabilityInfo, new MapComparatorByDate("update_datetime"));
		
		Map<String, HashMap<String, Object>> allPoints = new HashMap<String, HashMap<String,Object>>();
		carParkAvailabilityInfo.stream().forEach(point -> {
			
			if(!allPoints.containsKey(point.get("carpark_number"))) {
				allPoints.put((String)point.get("carpark_number"), point);
				CarParkAvailabilityDTO availability = new CarParkAvailabilityDTO();
				availability.setId((String) point.get("carpark_number"));
				String updatedDateTime = (String) point.getOrDefault("update_datetime", null);
				if(!updatedDateTime.contains("Z")) {
					updatedDateTime += ".000Z";
				}
				availability.setUpdatedDate(Timestamp.from((Instant)DateTimeConvertor.getDateFromStringDate(updatedDateTime)));
				List<HashMap<String, String>> carParkInfoList = (List<HashMap<String, String>>) point.getOrDefault("carpark_info",new ArrayList<HashMap<String,String>>());
				if(carParkInfoList.isEmpty()) {
					throw new RecordNotFoundException("No car park availability information available.");
				}
				HashMap<String, String> carParkInfoDetail = carParkInfoList.get(0);
				availability.setLotType(carParkInfoDetail.get("lot_type"));
				availability.setTotalLots(Integer.parseInt(carParkInfoDetail.get("total_lots")));
				availability.setLotsAvailable(Integer.parseInt(carParkInfoDetail.get("lots_available")));
				carParkAvailabilityDtos.add(availability);
			}

		});
		

		return carParkAvailabilityDtos;
	}
}
