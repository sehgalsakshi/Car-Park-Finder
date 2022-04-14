package com.carpark_hdb.rest.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class CustomDataIntegrityViolationException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public CustomDataIntegrityViolationException(String string) {
        super(string);
    }
}
