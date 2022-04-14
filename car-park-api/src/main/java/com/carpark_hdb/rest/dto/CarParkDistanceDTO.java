package com.carpark_hdb.rest.dto;

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
public class CarParkDistanceDTO {
	
	String id;

	Double latitude;
	
	Double longitude;
	
	Integer totalLots;
	
	Integer lotsAvailable;
	
	Double distance;

	public CarParkDistanceDTO(String id, Double latitude, Double longitude, Integer lotsAvailable, Integer totalLots,
			Double distance) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.totalLots = totalLots;
		this.lotsAvailable = lotsAvailable;
		this.distance = distance;
	}
	
	
}
