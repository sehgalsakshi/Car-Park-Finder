package com.carpark_hdb.rest.common.exceptions;

import java.util.List;

/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class ErrorResponse {
	
	public ErrorResponse(String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
	}

	private String message;
	
	private List<String> details;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}
}