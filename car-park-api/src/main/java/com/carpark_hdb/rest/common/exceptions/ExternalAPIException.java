package com.carpark_hdb.rest.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class ExternalAPIException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ExternalAPIException(String message) {
        super(message);
    }
}
