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

import java.util.Locale;

import smartcity.util.GPSCoordinate;

public class Stop extends GTFSObject {
	private String name;
	private GPSCoordinate coord;
	
	public Stop(String id, String name, double latitude, double longitude) {
		super(id);
		this.name = name;
		this.coord = new GPSCoordinate(latitude, longitude);
	}

	public String getName() {
		return name;
	}

	public GPSCoordinate getGPSCoordinate() {
		return coord;
	}
	
	/**
	 * Returns the same representation as used in the file stops.txt.
	 */
	@Override
	public String toString() {
		return String.format(Locale.US,"%s,,%s,,%.8g,%.8g",
				getId(),name,coord.latitude,coord.longitude);
	}
}
