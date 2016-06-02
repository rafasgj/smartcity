/*
Smart City - A class library to handle GTFS data.
Copyright (C) 2015-2016 Rafael Guterres Jeffman

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*/

package smartcity.gtfs;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import smartcity.util.CSVReader;
import smartcity.util.GPSCoordinate;

// import Weekdays.
import static smartcity.gtfs.Service.Weekday.*;

/**
 * Provides function to read GTFS files and return structures
 * with the data.
 * 
 * For more information on GTFS data see:
 *     https://developers.google.com/transit/gtfs/reference?
 */
public class GTFSReader {

	public static Map<String,Stop> loadStops(String filename)
		throws FileNotFoundException
	{
		Map<String,Stop> stops = new HashMap<>();
		
		CSVReader reader = new CSVReader(filename, ",");
		while (reader.hasNext()) {
			// read stop_id (it could be an int, but we'll treat as other ids.
			String id = reader.next();
			// ignore stop_code
			reader.skipNext();
			// read stop_name
			String name = reader.next();
			// ignore stop_desc
			reader.skipNext();
			// read stop location (stop_lat, sotp_lon).
			double lat = reader.nextDouble();
			double lon = reader.nextDouble();
			// add to resultset.
			stops.put(id,new Stop(id,name,lat,lon));
		}
		return stops;
	}

	public static Map<String,Route> loadRoutes(String filename)
		throws FileNotFoundException
	{
		Map<String,Route> routes = new HashMap<>();
				
		CSVReader reader = new CSVReader(filename, ",");
		while (reader.hasNext()) {
			// read route_id
			String id = reader.next();
			// ignore agency_id
			reader.skipNext();
			// read short name
			String shortname = reader.next();
			// read long name
			String longname = reader.next();
			// ignore route _desc, _type, _url, _color, _text_color
			reader.skipNext(5);
			routes.put(id,new Route(id,shortname, longname));
		}
		
		return routes;
	}

	public static Map<String,Shape> loadShapes(String filename)
		throws FileNotFoundException
	{
		Map<String,Shape> shapes = new HashMap<>();
		CSVReader reader = new CSVReader(filename, ",");
		String id = null, oldid = "";
		Shape s = null;
		while (reader.hasNext()) {
			// when id changes, it's a new shape
			id = reader.next();
			if (! id.equals(oldid)) {
				if (s != null)
					shapes.put(id,s);
				s = new Shape(id);
				oldid = id;
			}
			// read latitude and longitude
			double lat = reader.nextDouble();
			double lon = reader.nextDouble();
			s.appendPoint(new GPSCoordinate(lat, lon));
			// ignore shape_pt_sequence
			reader.nextInt();
		}
		// add last shape
		if (s != null)
			shapes.put(id,s);
		return shapes;
	}

	public static Map<String,Service> loadServices(String filename)
		throws FileNotFoundException
	{
		Map<String,Service> services = new HashMap<>();
		CSVReader reader = new CSVReader(filename, ",");
		while (reader.hasNext()) {
			String id = reader.next();
			Service service = new Service(id);
			service.setAvailable(MONDAY, (reader.nextInt()==1));
			service.setAvailable(TUESDAY, (reader.nextInt()==1));
			service.setAvailable(WEDNESDAY, (reader.nextInt()==1));
			service.setAvailable(THURSDAY, (reader.nextInt()==1));
			service.setAvailable(FRIDAY, (reader.nextInt()==1));
			service.setAvailable(SATURDAY, (reader.nextInt()==1));
			service.setAvailable(SUNDAY, (reader.nextInt()==1));
			service.setStartDate(StringToDate(reader.next()));
			service.setEndDate(StringToDate(reader.next()));
			services.put(id,service);
		}
		return services;
	}

	private static LocalDate StringToDate(String data) {
		return LocalDate.parse(data,
		                       DateTimeFormatter.ofPattern("yyyyMMdd"));
	}

	public static Map<String,Trip> loadTrips(String filename,
			Map<String,Route> routes, Map<String,Service> services,
			Map<String,Shape> shapes)
		throws FileNotFoundException
	{
		Map<String,Trip> trips = new HashMap<>();
		CSVReader reader = new CSVReader(filename, ",");
		while (reader.hasNext()) {
			// read route id
			String route_id = reader.next();
			// read service id
			String service_id = reader.next();
			// read this trip id
			String id = reader.next();
			// ignore headsign, short_name
			reader.skipNext(2);
			// read direction id
			int dir = reader.nextInt();
			// ignore block_id
			reader.skipNext();
			// read shape_id
			String shape_id = reader.next();
			// read wheelchair support
			boolean w = (reader.nextInt() == 1);
			// ignore extra fields
			for (int i = 9; i < reader.getRecordSize(); i++)
				reader.skipNext();
			
			Route r = searchObject(routes,route_id);
			Service s = searchObject(services, service_id);
			Shape sh = searchObject(shapes, shape_id);
			
			trips.put(id,new Trip(id, r, s, sh, dir, w));
		}
		return trips;
	}

	private static <K, T extends GTFSObject>
		T searchObject(Map<K,T> map, String id)
	{
		return map.get(id);
	}

	public static
	void loadStopTimes(String filename, Map<String,Trip> trips,
			Map<String,Stop> stops)
		throws FileNotFoundException
	{
		CSVReader reader = new CSVReader(filename, ",");
		String lastTripId = null;
		Trip lastTrip = null;
		while (reader.hasNext()) {
			// read trip id
			String trip_id = reader.next();
			// ignore arrival and departure time
			reader.skipNext(2);
			// read stop id
			String stop_id = reader.next();
			// ignore stop_sequence
			reader.skipNext();
			if (lastTrip == null || !trip_id.equals(lastTripId)) {
				Trip trip = searchObject(trips, trip_id);
				lastTrip = trip;
			}
			Stop stop = searchObject(stops, stop_id);
			lastTrip.addStop(stop);
		}
	}
}