package com.carpark_hdb.rest.dto;

import javax.validation.constraints.NotNull;

import com.carpark_hdb.rest.entity.CarParkAvailabilityEntity;

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
public class CarParkInfoDTO {
	
	public CarParkAvailabilityEntity carParkAvailability;
	
	@NotNull
	public String id;
	
	@NotNull
	public String address;
	
	@NotNull
	public Double xCoord;
	
	@NotNull
	public Double yCoord;
	
	@NotNull
	public String carParkType;
	
	@NotNull
	public String parkingSystemType;
	
	@NotNull
	public String shortTermParking;
	
	@NotNull
	public String freeParking;
	
	@NotNull
	public boolean nightParking = false;

	@NotNull
	public Integer carParkDecks;
	
	@NotNull
	public Double gantryHeight;
	
	@NotNull
	public boolean carParkBasement = false;

	public Double distanceFromUser;
}
