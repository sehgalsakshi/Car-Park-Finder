package com.carpark_hdb.rest.repo;
import java.util.List;

import com.carpark_hdb.rest.dto.CarParkDistanceDTO;

/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
interface CustomCarParkInfoRepo {

    List<CarParkDistanceDTO> fetchAvailableCarParksSortedByDistanceLimitedTo(
			Double latitude,
			Double longitude,
			Integer page,
			Integer perPage);
}