package com.carpark_hdb.rest.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.carpark_hdb.rest.entity.CarParkInfoEntity;

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
public class CarParkAvailabilityDTO {
	
	@NotNull
	public String id;

	@NotNull
	public Integer totalLots;
	
	@NotNull
	public Integer lotsAvailable;
	
	@NotNull
	public String lotType;

	@Getter
	public Timestamp createdDate;
	
	
	public Timestamp updatedDate;

	public CarParkInfoEntity carParkInfo;
}
