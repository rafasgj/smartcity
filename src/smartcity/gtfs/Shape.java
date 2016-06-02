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
import java.util.Locale;

import smartcity.util.GPSCoordinate;

public class Shape extends GTFSObject {
	private List<GPSCoordinate> vertexList;
	
	public Shape(String id) {
		super(id);
		vertexList = new LinkedList<GPSCoordinate>();
	}
	
	public void appendPoint(GPSCoordinate point) {
		vertexList.add(point);
	}
	
	/**
	 * Returns the same representation as used in the file shapes.txt.
	 */
	@Override
	public String toString() {
		String res = "";
		int seq = 1;
		for (GPSCoordinate c : vertexList) {
			res += String.format(Locale.US, "%s,%.8g,%.8g,%d\n",
						getId(),c.latitude,c.longitude,seq++);
		}
		return res;
	}
}
