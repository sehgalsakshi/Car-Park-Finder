package com.carpark_hdb.rest.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carpark_hdb.rest.entity.CarParkAvailabilityEntity;

/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public interface CarParkAvailabilityRepo extends JpaRepository<CarParkAvailabilityEntity, String> {

	public Optional<CarParkAvailabilityEntity> findById(String id);
	
	public List<CarParkAvailabilityEntity> findByLotsAvailableGreaterThan(int min_availability);
}
