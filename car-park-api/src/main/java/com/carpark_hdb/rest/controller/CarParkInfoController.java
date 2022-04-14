package com.carpark_hdb.rest.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carpark_hdb.rest.common.messages.BaseResponse;
import com.carpark_hdb.rest.dto.CarParkDistanceDTO;
import com.carpark_hdb.rest.dto.CarParkInfoDTO;
import com.carpark_hdb.rest.service.CarParkInfoService;

@RestController
@RequestMapping("/CarParkInfo")
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class CarParkInfoController {

	@Autowired
	private CarParkInfoService carParkInfoService;
	
	@GetMapping(value = "/find")
	public ResponseEntity<List<CarParkInfoDTO>> getAllCarParkInfos() {
		List<CarParkInfoDTO> list = carParkInfoService.findCarParkInfoList();
		return new ResponseEntity<List<CarParkInfoDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findAllAvailableParkings")
	public ResponseEntity<List<CarParkDistanceDTO>> getAllAvailableParkings(
			@NotNull(message = "latitude can't be null") @RequestParam Double latitude, 
			@NotNull(message = "longitude can't be null") @RequestParam Double longitude,
			@NotNull(message = "page can't be null") @RequestParam Integer page,
			@NotNull(message = "records per page can't be null") @RequestParam Integer perPage		
		) 
	{
		List<CarParkDistanceDTO> list = carParkInfoService.findAllAvailableLotsSortedByDistance(latitude, longitude, page, perPage);
		return new ResponseEntity<List<CarParkDistanceDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/updateCarParkAvailability")
	public ResponseEntity<String> updateCarParkAvailability() {
		carParkInfoService.updateCarParkAvailability();
		String successMessage = "Car park availability updated sucessfully.";
		return new ResponseEntity<String>(successMessage, HttpStatus.OK);
	}
	
	@GetMapping(value = "/find/by-id")
	public ResponseEntity<CarParkInfoDTO> getCarParkInfoById(@NotNull(message = "Id can't be null") @RequestParam String id) {
		CarParkInfoDTO list = carParkInfoService.findById(id);
		return new ResponseEntity<CarParkInfoDTO>(list, HttpStatus.OK);
	}

	@PostMapping(value = { "/add", "/update" })
	public ResponseEntity<BaseResponse> createOrUpdateCarParkInfo( @RequestBody CarParkInfoDTO carParkInfoDTO) {
		BaseResponse response = carParkInfoService.createOrUpdateCarParkInfo(carParkInfoDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<BaseResponse> deleteCarParkInfoById(@PathVariable("id") String id) {
		BaseResponse response = carParkInfoService.deleteCarParkInfoById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
