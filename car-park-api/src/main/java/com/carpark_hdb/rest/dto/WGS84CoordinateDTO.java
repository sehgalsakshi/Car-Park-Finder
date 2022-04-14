package com.carpark_hdb.rest.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class WGS84CoordinateDTO {
	
	@NotNull
	@JsonProperty
	private Double latitude;

	@NotNull
	@JsonProperty
	private Double longitude;
	
	}
