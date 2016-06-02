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

import java.util.LinkedList;
import java.util.List;

import smartcity.util.GPSCoordinate;

public class Trip extends GTFSObject {
	private Route route;
	private Service service;
	private Shape shape;
	private int direction;
	private boolean wheelchair;
	private List<Stop> stops;
	
	public Trip(String id, Route route, Service service, Shape shape,
				int direction, boolean w)
	{
		super(id);
		this.route = route;
		this.service = service;
		this.shape = shape;
		this.direction = direction;
		this.wheelchair = w;
		
		this.stops = new LinkedList<Stop>();
	}
	
	public Route getRoute() {
		return route;
	}
	
	public Service getService() {
		return service;
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public boolean isOneWay() {
		return direction == 0;
	}
	
	public boolean hasWeelchair() {
		return wheelchair;
	}
	
	@Override
	public String toString() {
		return String.format("%s,%s,%s,,,%d,,,%d",
				route.getId(), service.getId(),
				getId(), isOneWay()?0:1, wheelchair?1:2);
	}

	public void addStop(Stop stop) {
		stops.add(stop);
	}
	
	public boolean hasStopNear(GPSCoordinate place, double threshold) {
		for (Stop s : stops) {
			if (s.getGPSCoordinate().distance(place) < threshold)
				return true;
		}
		return false;
	}
	
}
