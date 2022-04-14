package com.carpark_hdb.rest.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.carpark_hdb.rest.common.utils.DateTimeConvertor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "hdb_car_park_availability")
@ToString
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class CarParkAvailabilityEntity implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Setter
    @Getter
    @Id
    @Column(name = "car_park_no", length = 5, nullable = false)
	public String id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("car_park_no")
    @JoinColumn(name = "car_park_no")
    public CarParkInfoEntity carParkInfo;

	@Setter
    @Getter
    @Column(name = "total_lots", nullable = false)
	public Integer totalLots;
	
	@Setter
    @Getter
    @Column(name = "lots_available", nullable = false)
	public Integer lotsAvailable;
	
	@Setter
    @Getter
    @Column(name = "lot_type", nullable = false)
	public String lotType;

	@Getter
    @Column(name = "created_date")
    public Timestamp createdDate;
	
	@Setter
    @Getter
    @Column(name = "update_date")
    public Timestamp updatedDate;

	@PrePersist
    public void prePersist() {
    	
        createdDate = Timestamp.from(DateTimeConvertor.getCurrentInstantAsPerDefaultTZ());
    }

}
