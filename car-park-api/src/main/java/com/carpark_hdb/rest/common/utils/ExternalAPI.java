package com.carpark_hdb.rest.common.utils;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.carpark_hdb.rest.common.exceptions.ExternalAPIException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class ExternalAPI {
	private RestTemplate restTemplate = new RestTemplate();
	
	public String executeGetRequest(String url, HttpHeaders requestHeaders, 
			LinkedMultiValueMap<String, String> vars) {
		HttpEntity<?> entity = new HttpEntity<>(requestHeaders);
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
	    		.queryParams(vars); // The allRequestParams must have been built for all the query params
	    UriComponents uriComponents = builder.build().encode(); // encode() is to ensure that characters like {, }, are preserved and not encoded. Skip if not needed.
	    ResponseEntity<String> response = (ResponseEntity<String>) restTemplate.exchange(
	    		uriComponents.toUri(), HttpMethod.GET,
	            entity, String.class);	 
	    HttpStatus code = response.getStatusCode();
	    if (code == HttpStatus.OK || code == HttpStatus.CREATED) {
	    	return response.getBody();
	
	        } else if (code == HttpStatus.NO_CONTENT) {
	            return null;
	        } else if (code.series() == HttpStatus.Series.SERVER_ERROR)
	            throw new ExternalAPIException("Server Exception occured while requesting! Exception details: " + response.getBody());
	        else if (code == HttpStatus.UNAUTHORIZED)
	            throw new ExternalAPIException
	            ("Unauthorized Exception occured while requesting! Exception details: " + response.getBody());
	        else {
	            throw new ExternalAPIException("Exception occured! Exception details: " + response.getBody()+code.value());
	        }
	}
	public <T> T requestRestServerWithGetMethod(String url, HttpHeaders requestHeaders, 
			LinkedMultiValueMap<String, String> vars, Class<T> type)
	{
		String response_body = executeGetRequest(url, requestHeaders, vars);
		ObjectMapper objectMapper = new ObjectMapper();
    	T obj;
		try {
			obj = objectMapper.readValue(response_body, type);
		} catch (IOException e) {
			throw new ExternalAPIException("Error while mapping external api values");
		}	
    	return obj;
	}
}
