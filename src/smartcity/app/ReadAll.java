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

package smartcity.app;

import java.io.FileNotFoundException;
import java.util.Map;

import smartcity.gtfs.GTFSReader;
import smartcity.gtfs.Route;
import smartcity.gtfs.Service;
import smartcity.gtfs.Shape;
import smartcity.gtfs.Stop;
import smartcity.gtfs.Trip;

public class ReadAll {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Reading stops.");
		Map<String,Stop> stops =
				GTFSReader.loadStops("data/stops.txt");
		System.out.println("Reading routes.");
		Map<String,Route> routes =
				GTFSReader.loadRoutes("data/routes.txt");
		System.out.println("Reading shapes.");
		Map<String,Shape> shapes =
				GTFSReader.loadShapes("data/shapes.txt");
		System.out.println("Reading calendar.");
		Map<String,Service> services =
				GTFSReader.loadServices("data/calendar.txt");
		System.out.println("Reading trips.");
		Map<String,Trip> trips =
				GTFSReader.loadTrips("data/trips.txt",routes,services,shapes);
		System.out.println("Reading stop times.");
		long s = System.currentTimeMillis();
		GTFSReader.loadStopTimes("data/stop_times.txt", trips, stops);
		long e = System.currentTimeMillis();
		System.out.println("\nTempo = " + ((e-s)/1000.0));
	}
}
