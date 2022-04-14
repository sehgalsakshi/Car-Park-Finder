package com.carpark_hdb.rest.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import com.carpark_hdb.constants.APIConstants;
import com.carpark_hdb.rest.common.utils.ExternalAPI;
import com.carpark_hdb.rest.dto.SVY21CoordinateDTO;
import com.carpark_hdb.rest.dto.WGS84CoordinateDTO;

@Service
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class LatitudeLongitudeConversionService {
	private ExternalAPI externalAPI = new ExternalAPI();
	
	public WGS84CoordinateDTO convertFromSVY21ToWGS84(double latitude, double longitude) {
		LinkedMultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
		vars.add("X", Double.toString(latitude));
		vars.add("Y", Double.toString(longitude));
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		
		String api_path = APIConstants.COORDINATE_CONVERSION_BASE_API+APIConstants.COORDINATE_SVY21_TO_WGS84_URI;
		WGS84CoordinateDTO coord = externalAPI.requestRestServerWithGetMethod(
				api_path, headers, vars, WGS84CoordinateDTO.class);
		return coord;
	}
	
	public SVY21CoordinateDTO convertFromWGS84ToSVY21(double latitude, double longitude) {
		LinkedMultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
		vars.add("latitude", Double.toString(latitude));
		vars.add("longitude", Double.toString(longitude));
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		
		String api_path = APIConstants.COORDINATE_CONVERSION_BASE_API+APIConstants.COORDINATE_WGS84_TO_SVY21_URI;
		SVY21CoordinateDTO coord = externalAPI.requestRestServerWithGetMethod(
				api_path, headers, vars, SVY21CoordinateDTO.class);
		return coord;
	}
}
