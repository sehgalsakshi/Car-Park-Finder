package com.carpark_hdb.rest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import com.carpark_hdb.rest.common.exceptions.CustomDataIntegrityViolationException;
import com.carpark_hdb.rest.common.exceptions.RecordNotFoundException;
import com.carpark_hdb.rest.common.messages.BaseResponse;
import com.carpark_hdb.rest.common.messages.CustomMessage;
import com.carpark_hdb.rest.common.utils.CarParkAvailabilityUtils;
import com.carpark_hdb.rest.common.utils.EntityDTOConversion;
import com.carpark_hdb.rest.common.utils.ExternalAPI;
import com.carpark_hdb.rest.dto.CarParkAvailabilityDTO;
import com.carpark_hdb.rest.dto.SVY21CoordinateDTO;
import com.carpark_hdb.rest.entity.CarParkAvailabilityEntity;
import com.carpark_hdb.rest.repo.CarParkAvailabilityRepo;
import com.carpark_hdb.rest.repo.CarParkInfoRepo;
@Service
@Transactional
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class CarParkAvailabilityService {
	private String base_url = "https://developers.onemap.sg/commonapi/convert";
	private ExternalAPI externalAPI = new ExternalAPI();
	private CarParkAvailabilityUtils carParkUtil = new CarParkAvailabilityUtils();
	@Autowired
	CarParkAvailabilityRepo carParkAvailabilityRepo;
	@Autowired
	CarParkInfoRepo carParkInfoRepo;
	
	public List<CarParkAvailabilityDTO> updateCarParkAvailability() {
		
		List<CarParkAvailabilityDTO> carParkAvailabilityDTOs = carParkUtil.fetchCarAvailability();
		//carParkAvailabilityDTOs.stream().forEach(this::createOrUpdateCarParkAvailability);
		
		return carParkAvailabilityDTOs;
	}
	
	public SVY21CoordinateDTO convertFromWGS84ToSVY21(double x, double y) {
		LinkedMultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
		vars.add("X", Double.toString(x));
		vars.add("Y", Double.toString(y));
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		
		String api_path = base_url+"/4326to3414";
		SVY21CoordinateDTO coord = externalAPI.requestRestServerWithGetMethod(
				api_path, headers, vars, SVY21CoordinateDTO.class);//, new CoordinateDTO());
		return coord;
	}
	
	public List<CarParkAvailabilityDTO> findCarParkAvailabilityList() {
		return carParkAvailabilityRepo.findAll()
				.stream().map(entity-> EntityDTOConversion.copyEntityToDto(entity, CarParkAvailabilityDTO.class))
				.collect(Collectors.toList());
	}
	
	public List<CarParkAvailabilityDTO> findCarParkAvailabilityListByMinLotsAvailable() {
		return carParkAvailabilityRepo.findByLotsAvailableGreaterThan(0)
				.stream().map(entity-> EntityDTOConversion.copyEntityToDto(entity, CarParkAvailabilityDTO.class))
				.collect(Collectors.toList());
	}

	public CarParkAvailabilityDTO findById(String id) {
		Optional<CarParkAvailabilityEntity> carParkAvailabilityEntity = carParkAvailabilityRepo.findById(id);
		if (carParkAvailabilityEntity.isEmpty()) {
			throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + id);	
		}
		return EntityDTOConversion.copyEntityToDto(carParkAvailabilityEntity.get(), CarParkAvailabilityDTO.class);
	}

	public BaseResponse createOrUpdateCarParkAvailability(CarParkAvailabilityDTO carParkAvailabilityDTO) {
		try {
			CarParkAvailabilityEntity carParkAvailabilityEntity = null;
			if(carParkAvailabilityRepo.existsById(carParkAvailabilityDTO.getId())) {
				carParkAvailabilityEntity = carParkAvailabilityRepo.findById(carParkAvailabilityDTO.getId()).get();
				BeanUtils.copyProperties(carParkAvailabilityDTO, carParkAvailabilityEntity);

			}else {
			carParkAvailabilityEntity = EntityDTOConversion.copyDtoToEntity(carParkAvailabilityDTO, CarParkAvailabilityEntity.class);
			}
			carParkAvailabilityRepo.save(carParkAvailabilityEntity);
		}
		catch (DataIntegrityViolationException ex) {
			throw new CustomDataIntegrityViolationException(ex.getCause().getCause().getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		return new BaseResponse(carParkAvailabilityDTO.getId() + CustomMessage.SAVE_SUCCESS_MESSAGE);
	}

	public BaseResponse deleteCarParkAvailabilityById(String id) {
		if (carParkAvailabilityRepo.existsById(id)) {
			carParkAvailabilityRepo.deleteById(id);
		} else {
			throw new RecordNotFoundException(CustomMessage.NO_RECOURD_FOUND + id);
		}
		return new BaseResponse(id + CustomMessage.DELETE_SUCCESS_MESSAGE);

	}

}
