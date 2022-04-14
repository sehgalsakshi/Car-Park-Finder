package com.carpark_hdb.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.carpark_hdb.rest.common.exceptions.CustomDataIntegrityViolationException;
import com.carpark_hdb.rest.common.exceptions.RecordNotFoundException;
import com.carpark_hdb.rest.common.messages.BaseResponse;
import com.carpark_hdb.rest.common.messages.CustomMessage;
import com.carpark_hdb.rest.common.utils.EntityDTOConversion;
import com.carpark_hdb.rest.dto.CarParkAvailabilityDTO;
import com.carpark_hdb.rest.dto.CarParkDistanceDTO;
import com.carpark_hdb.rest.dto.CarParkInfoDTO;
import com.carpark_hdb.rest.dto.WGS84CoordinateDTO;
import com.carpark_hdb.rest.entity.CarParkAvailabilityEntity;
import com.carpark_hdb.rest.entity.CarParkInfoEntity;
import com.carpark_hdb.rest.repo.CarParkInfoRepo;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class CarParkInfoService {

	@Autowired
	private CarParkInfoRepo carParkInfoRepo;
	
	@Autowired
	private LatitudeLongitudeConversionService conversionService;

	@Autowired
	private CarParkAvailabilityService availabilityService;
	
	@Autowired
	private CSVCarParkInfoUploadService carParkInfoUploadService;
	
	public List<CarParkInfoDTO> findCarParkInfoList() {
		return carParkInfoRepo.findAll()
				.stream().map(entity-> EntityDTOConversion.copyEntityToDto(entity, CarParkInfoDTO.class))
				.collect(Collectors.toList());
	}
	
	@Scheduled(fixedDelay = 60000)
	public void updateCarParkAvailability() {
		
		List<CarParkAvailabilityDTO> availabilityDTOs = availabilityService.updateCarParkAvailability();
		availabilityDTOs.stream().forEach(available -> {
			try {
			CarParkInfoDTO infoDTO = findById(available.getId());
			available.setCarParkInfo(EntityDTOConversion.copyDtoToEntity(infoDTO, CarParkInfoEntity.class));
			infoDTO.setCarParkAvailability(EntityDTOConversion.copyDtoToEntity(available, CarParkAvailabilityEntity.class));
			createOrUpdateCarParkInfo(infoDTO);
			} catch (Exception e) {
				System.out.println("Info not found for car park number: "+available.getId());
			}
		});
	}
	public void save(MultipartFile file) {
		try {
			List<CarParkInfoDTO> carParkInfoDTOs = carParkInfoUploadService.saveCsvData(file.getInputStream());
			carParkInfoDTOs.stream().forEach(this::createOrUpdateCarParkInfo);

		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}
	
	public List<CarParkDistanceDTO> findAllAvailableLotsSortedByDistance(Double latitude, Double longitude, Integer page, Integer perPage) {
		WGS84CoordinateDTO convertedCoords = conversionService.convertFromSVY21ToWGS84(latitude, longitude);
		return carParkInfoRepo.fetchAvailableCarParksSortedByDistanceLimitedTo(convertedCoords.getLatitude(), convertedCoords.getLongitude(), page, perPage);
	}
	
	public List<CarParkInfoDTO> findAllAvailableLots() {
		List<CarParkInfoEntity> infoEntities = carParkInfoRepo.findByCarParkAvailability_LotsAvailableGreaterThan(0);
		List<CarParkInfoDTO> infoDTOs = new ArrayList<CarParkInfoDTO>();
		infoEntities.stream().forEach(entity -> {
			CarParkInfoDTO dto = EntityDTOConversion.copyEntityToDto(entity, CarParkInfoDTO.class);
			infoDTOs.add(dto);
		});
		return infoDTOs;
	}
	
	public CarParkInfoDTO findById(String id) {
		Optional<CarParkInfoEntity> carParkInfoEntity = carParkInfoRepo.findById(id);
		if (carParkInfoEntity.isEmpty()) {
			throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + id);	
		}
		return EntityDTOConversion.copyEntityToDto(carParkInfoEntity.get(), CarParkInfoDTO.class);
	}

	public BaseResponse createOrUpdateCarParkInfo(CarParkInfoDTO carParkInfoDTO) {
		try {
			CarParkInfoEntity carParkInfoEntity = null;
			if(carParkInfoRepo.existsById(carParkInfoDTO.getId())) {
				carParkInfoEntity = carParkInfoRepo.findById(carParkInfoDTO.getId()).get();
				BeanUtils.copyProperties(carParkInfoDTO, carParkInfoEntity);
				carParkInfoEntity.setCarParkAvailability(carParkInfoDTO.getCarParkAvailability());
			}else {
			carParkInfoEntity = EntityDTOConversion.copyDtoToEntity(carParkInfoDTO, CarParkInfoEntity.class);
			}
			carParkInfoRepo.save(carParkInfoEntity);
		}
		catch (DataIntegrityViolationException ex) {
			throw new CustomDataIntegrityViolationException(ex.getCause().getCause().getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		return new BaseResponse(carParkInfoDTO.getId() + CustomMessage.SAVE_SUCCESS_MESSAGE);
	}

	public BaseResponse deleteCarParkInfoById(String id) {
		if (carParkInfoRepo.existsById(id)) {
			carParkInfoRepo.deleteById(id);
		} else {
			throw new RecordNotFoundException(CustomMessage.NO_RECOURD_FOUND + id);
		}
		return new BaseResponse(id + CustomMessage.DELETE_SUCCESS_MESSAGE);

	}

}
