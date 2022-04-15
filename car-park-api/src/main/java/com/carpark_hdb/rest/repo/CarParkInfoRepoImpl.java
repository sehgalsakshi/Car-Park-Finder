package com.carpark_hdb.rest.repo;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.carpark_hdb.rest.dto.CarParkDistanceDTO;

@Repository
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
class CarParkInfoRepoImpl implements CustomCarParkInfoRepo {

	String DISTANCE_CALCULATION = " ROUND((6371 * acos( cos( radians(info.xCoord) ) * cos( radians( :latitude) ) "
			+ " * cos( radians( :longitude ) - radians(info.yCoord) ) + sin( radians(info.xCoord) ) * sin( radians( :latitude ) ) ) ),2) "
			+ " as distance ";	

	String FIND_AVAILABLE_CAR_PARKS_SORTED_BY_DISTANCE = " Select NEW com.carpark_hdb.rest.dto.CarParkDistanceDTO(info.id, "
			+ " info.address, info.xCoord, info.yCoord, avail.lotsAvailable,avail.totalLots, "
			+ DISTANCE_CALCULATION+") "
					+ " from CarParkInfoEntity info left join info.carParkAvailability avail"
			+ " where avail.id = info.id and "
			+ " avail.lotsAvailable>0 "
			+ " order by distance ";
	  
	
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CarParkDistanceDTO> fetchAvailableCarParksSortedByDistanceLimitedTo(
			Double latitude,
			Double longitude,
			Integer page,
			Integer perPage) {
    	TypedQuery<CarParkDistanceDTO> typedQuery =  entityManager
    			.createQuery(FIND_AVAILABLE_CAR_PARKS_SORTED_BY_DISTANCE,
    			CarParkDistanceDTO.class)
        		.setParameter("latitude", latitude)
        		.setParameter("longitude", longitude)
        		.setMaxResults(perPage)
        		.setFirstResult(page);
        List<CarParkDistanceDTO> availableCarParks = typedQuery.getResultList();
    	return availableCarParks;
    }
}