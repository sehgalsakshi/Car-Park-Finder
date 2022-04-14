package com.carpark_hdb.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import
org.springframework.http.HttpStatus;
import
org.springframework.http.ResponseEntity;
import
org.springframework.stereotype.Controller;
import
org.springframework.web.bind.annotation.PostMapping;
import
org.springframework.web.bind.annotation.RequestMapping;
import
org.springframework.web.bind.annotation.RequestParam;
import
org.springframework.web.multipart.MultipartFile;

import com.carpark_hdb.rest.service.CarParkInfoService;

@Controller

/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 * Controller to add car park info from csv file
 */
@RequestMapping("/csv") public class CSVController {

	@Autowired CarParkInfoService fileService;
	
	/**
	 * @param csv file containing car park info with header columns: 
	 * car_park_no	address	x_coord	y_coord	car_park_type	type_of_parking_system	short_term_parking	
	 * free_parking	night_parking	car_park_decks	gantry_height	car_park_basement
	 * 
	 * @return success string
	 */
	@PostMapping("/upload") public ResponseEntity<String>
	
	uploadFile(@RequestParam("file") MultipartFile file) { 
		String message = "";
		if (com.carpark_hdb.rest.service.CSVCarParkInfoUploadService.hasCSVFormat(file)) { 
			try {
				fileService.save(file); 
				message = "Uploaded the file successfully: " +
						file.getOriginalFilename(); 
				return ResponseEntity.status(HttpStatus.OK).body(message); 
			} 
			catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message); }
		} 
		message = "Please upload a csv file!"; 
		return	ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message); } }
