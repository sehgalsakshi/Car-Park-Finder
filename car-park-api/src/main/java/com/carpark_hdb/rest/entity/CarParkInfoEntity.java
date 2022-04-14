package com.carpark_hdb.rest.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

import com.carpark_hdb.rest.common.utils.DateTimeConvertor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "hdb_car_park")
@ToString
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class CarParkInfoEntity {

	@Setter
    @Getter
    @Id
    @Column(name = "car_park_no", length = 5, nullable = false)
	public String id;
	
	@OneToOne(mappedBy = "carParkInfo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    public PostDetails details;
//	
//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "id")
	@Setter
	@Getter
    public CarParkAvailabilityEntity carParkAvailability;
//	
	@Setter
    @Getter
    @Column(name = "address", nullable = false)
	public String address;
	
	@Setter
    @Getter
    @Column(name = "x_coord", nullable = false)
	public Double xCoord;
	
	@Setter
    @Getter
    @Column(name = "y_coord", nullable = false)
	public Double yCoord;
	
	@Setter
    @Getter
    @Column(name = "car_park_type", nullable = false, length = 45)
	public String carParkType;
	
	@Setter
    @Getter
    @Column(name = "type_of_parking_system", nullable = false, length = 45)
	public String parkingSystemType;
	
	@Setter
    @Getter
    @Column(name = "short_term_parking", nullable = false, length = 45)
	public String shortTermParking;
	
	@Setter
    @Getter
    @Column(name = "free_parking", nullable = false, length = 45)
	public String freeParking;
	
	@Setter
    @Getter
    @Column(name = "night_parking", nullable = false)
	public boolean nightParking = false;

	@Setter
    @Getter
    @Version
	@Column(name = "version")
	public Integer version;
	
	@Setter
    @Getter
    @Column(name = "car_park_decks", nullable = false)
	public Integer carParkDecks;
	
	@Setter
    @Getter
    @Column(name = "gantry_height", nullable = false)
	public Double gantryHeight;
	
	@Setter
    @Getter
    @Column(name = "car_park_basement", nullable = false)
	public boolean carParkBasement = false;

	@Getter
    @Column(name = "created_date")
    public Timestamp createdDate;

    @Getter
    @Column(name = "update_date")
    public Timestamp updatedDate;

    @PrePersist
    public void prePersist() {
        createdDate = Timestamp.from(DateTimeConvertor.getCurrentInstantAsPerDefaultTZ());
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = Timestamp.from(DateTimeConvertor.getCurrentInstantAsPerDefaultTZ());
    }
}
