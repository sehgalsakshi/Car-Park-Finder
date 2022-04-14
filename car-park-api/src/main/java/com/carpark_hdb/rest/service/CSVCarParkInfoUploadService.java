package com.carpark_hdb.rest.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.carpark_hdb.rest.dto.CarParkInfoDTO;
import com.carpark_hdb.rest.dto.WGS84CoordinateDTO;
@Service
/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class CSVCarParkInfoUploadService {
	@Autowired
	LatitudeLongitudeConversionService conversionService;
	public static String TYPE = "text/csv";
	static String[] HEADERs = {"car_park_no", "address", "x_coord", "y_coord", "car_park_type", "type_of_parking_system", "short_term_parking", "free_parking", "night_parking", "car_park_decks", "gantry_height", "car_park_basement"};
	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}
	@SuppressWarnings("deprecation")
	public List<CarParkInfoDTO> saveCsvData(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			List<CarParkInfoDTO> carParkInfoList = new ArrayList<CarParkInfoDTO>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				CarParkInfoDTO carParkInfo = new CarParkInfoDTO();
				carParkInfo.setId(csvRecord.get("car_park_no"));
				carParkInfo.setAddress(csvRecord.get("address"));
				Double XCoord = Double.parseDouble(csvRecord.get("x_coord"));
				Double YCoord = Double.parseDouble(csvRecord.get("y_coord"));
				WGS84CoordinateDTO coord = conversionService.convertFromSVY21ToWGS84(XCoord, YCoord);
				carParkInfo.setXCoord(coord.getLatitude());
				carParkInfo.setYCoord(coord.getLongitude());
				carParkInfo.setCarParkType(csvRecord.get("car_park_type"));
				carParkInfo.setParkingSystemType(csvRecord.get("type_of_parking_system"));
				carParkInfo.setShortTermParking(csvRecord.get("short_term_parking"));
				carParkInfo.setFreeParking(csvRecord.get("free_parking"));
				carParkInfo.setNightParking(csvRecord.get("night_parking")=="YES" ? true: false);
				carParkInfo.setCarParkBasement(csvRecord.get("car_park_basement")=="YES" ? true: false);
				carParkInfo.setCarParkDecks(Integer.parseInt(csvRecord.get("car_park_decks")));
				carParkInfo.setGantryHeight(Double.parseDouble(csvRecord.get("gantry_height")));
				carParkInfoList.add(carParkInfo);
			}
			return carParkInfoList;
		} catch (IOException e) {
			e.printStackTrace(System.out);	
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		} finally {
		}
	}
}