package com.carpark_hdb.rest.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carpark_hdb.rest.entity.CarParkInfoEntity;

/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public interface CarParkInfoRepo extends JpaRepository<CarParkInfoEntity, String>, CustomCarParkInfoRepo {

	public Optional<CarParkInfoEntity> findById(String id);

	public List<CarParkInfoEntity> findByCarParkAvailability_LotsAvailableGreaterThan(int min_availability);
}

