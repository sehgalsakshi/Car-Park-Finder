# <b>Car-Park-Finder</b>
<b>Problem Statement</b>: Find all the available car parks sorted by distance with the given latitude and longitude coordinates.

<b>Schema</b>: Solution consists of two database tables:
	a. CarParkInfo: Contains all the required information about a particular car park.
	b. CarParkAvailability: Contains most recently updated information about the available parking lots
	
Both these tables share one to one mapping as there is no reason to preserve all information about available parking lots instead of just the most recent one.
Also CarParkInfo is the parent entity with CarParkAvailability as child since CarParkAvailability standalone holds no relevance.

More info about dataset can be found here
https://data.gov.sg/dataset/hdb-carpark-information

Framework Chosen : <b>Spring Boot</b>
Server : <b>Embedded Tomcat</b>
## <b>Application Flow/ APIs </b>

1. <b>Upload CarParkInfo using CSV</b> (/csv/upload)
	Takes as input a csv file and persists all car park details in database.

2. <b>Coordinate conversion from SVY21 to WGS84 </b>(/coordinateConversion/SVY21ToWGS84?latitude=latitude_value&longitude=longitude_value)
	Takes as input request params : latitude and longitude
	Converts coordinates from SVY21 to more universal format WGS84 using below api internally
	https://www.onemap.gov.sg/docs/#coordinates-converters:  /commonapi/convert/3414to4326
	
3. <b>Coordinate conversion from WGS84 to SVY21</b> (/coordinateConversion/WGS84ToSVY21?latitude=latitude_value&longitude=longitude_value)
	Takes as input request params : latitude and longitude
	Converts coordinates from WGS84 to SVY21 using below api internally
	https://www.onemap.gov.sg/docs/#coordinates-converters:  /commonapi/convert/4326to3414
	
4. <b>Update Car Park Availability</b> (/CarParkInfo/updateCarParkAvailability)
	Requires no input
	Fetches the current timestamp as per default timezone
	Triggers the below api to fetch latest car park lot availability with time sent as current timestamp
	https://data.gov.sg/dataset/carpark-availability
	This api can be triggered manually and is also scheduled at fixed interval of 1 minutes so that at all times information about lot availability is up to date.
	(You can turn off scheduling by removing @Scheduled annotation updateCarParkAvailability method defined in CarParkInfoService
	
5. <b>Find all available parkings sorted by distance</b> (/CarParkInfo/findAllAvailableParkings?latitude=longitude_value&longitude=longitude_value&page=page_number&perPage=items_per_page)
	Takes as input request params : latitude and longitude, page_number and items_per_page
	Returns sorted list of all car parks that have some vacant parking lots. The list is sorted in ascending as per the distance between car park and user's distance as mentioned by latitude and longitude values.
	It internally uses custom spring jpa implementation for writing a query using entity manager that returns specific projections.
	
6. <b>All basic crud operations for CarParkInfo are added in routes</b>