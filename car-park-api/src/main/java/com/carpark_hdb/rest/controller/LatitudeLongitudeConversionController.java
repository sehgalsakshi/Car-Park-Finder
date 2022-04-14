package com.carpark_hdb.rest.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carpark_hdb.rest.dto.SVY21CoordinateDTO;
import com.carpark_hdb.rest.dto.WGS84CoordinateDTO;
import com.carpark_hdb.rest.service.LatitudeLongitudeConversionService;

@RestController
@RequestMapping("/coordinateConversion")
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class LatitudeLongitudeConversionController {

	@Autowired
	private LatitudeLongitudeConversionService latLongConversion;
	
	@GetMapping(value = "/SVY21ToWGS84")
	public ResponseEntity<WGS84CoordinateDTO> convertSVY21ToWGS84(
			@NotNull(message = "Latitude can't be null") @RequestParam Double latitude, 
			@NotNull(message = "Longitude can't be null") @RequestParam Double longitude) {
		WGS84CoordinateDTO coordinate = latLongConversion.convertFromSVY21ToWGS84(latitude, longitude);
		return new ResponseEntity<WGS84CoordinateDTO>(coordinate, HttpStatus.OK);
	}
	
	@GetMapping(value = "/WGS84ToSVY21")
	public ResponseEntity<SVY21CoordinateDTO> convertWGS84ToSVY21(
			@NotNull(message = "Latitude can't be null") @RequestParam Double latitude, 
			@NotNull(message = "Longitude can't be null") @RequestParam Double longitude) {
		SVY21CoordinateDTO coordinate = latLongConversion.convertFromWGS84ToSVY21(latitude, longitude);
		return new ResponseEntity<SVY21CoordinateDTO>(coordinate, HttpStatus.OK);
	}

}
