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

package smartcity.util;

import static java.lang.Math.toRadians;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.atan2;

/**
 * Implements a GPS Coordinate that can be compared to others,
 * and provide a haversine distance method.
 */
public class GPSCoordinate {
	public final double latitude;
	public final double longitude;
	
	/**
	 * Initializes a new coordinate given Latitude and Longitude;
	 * @param latitude The coordinate latitude.
	 * @param longitude The coordinate longitude.
	 */
	public GPSCoordinate(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Computes the haversine distance, in meters, of this location
	 * to another location. As for any haversine distance, it is not
	 * precise for very small distances.
	 * @param other The location to compute the distance to.
	 * @return The haversine distance, in meters.
	 */
	public double distance(GPSCoordinate other) {
		//double R = 6378137; // Earth Equatorial radius in meters.
		double R = 6371000.8; // Earth volumetric radius in meters (IUGG).
	
		double dlat = toRadians(latitude - other.latitude);
		double dlon = toRadians(longitude - other.longitude);
		double coslat = cos(toRadians(latitude));
		double cosother = cos(toRadians(other.latitude));
		
		// compute haversine
		double a = pow(sin(dlat)/2.0,2) + coslat*cosother*pow(sin(dlon/2.0),2);  
		double c = 2 * atan2(sqrt(a), sqrt(1-a));
		double d = R * c;
		
		return d;
	}

	/**
	 * Checks if two coordinates mark the same location.
	 * @param o A GPSCoordinate to be compared to.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof GPSCoordinate) {
			GPSCoordinate other = (GPSCoordinate)o;
			return latitude==other.latitude && longitude==other.longitude;
		}
		return false;
	}
	
	/**
	 * Returns a hash code for the coordinate.
	 * @return An 'int' hash code.
	 */
	@Override
	public int hashCode() {
		return String.format("%.8g%.8g", latitude, longitude).hashCode();
	}
}
