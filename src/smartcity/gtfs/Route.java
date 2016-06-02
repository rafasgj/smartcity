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

public class Route extends GTFSObject {
	private String shortName;
	private String longName;
	
	public Route(String id, String shortName, String longName) {
		super(id);
		this.shortName = shortName;
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public String getLongName() {
		return longName;
	}
}
