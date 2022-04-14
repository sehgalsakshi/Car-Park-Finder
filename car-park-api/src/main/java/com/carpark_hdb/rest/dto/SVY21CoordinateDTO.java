package com.carpark_hdb.rest.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAlias;
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
public class SVY21CoordinateDTO {
	
	@NotNull
	@JsonProperty
	@JsonAlias({"X"})
	private Double x;

	@NotNull
	@JsonProperty
	@JsonAlias({"Y"})
	private Double y;
	
	}
