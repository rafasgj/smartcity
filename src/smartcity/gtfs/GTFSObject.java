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

/**
 * Base classe for all GTFSObjects.
 */
public abstract class GTFSObject {
	private String id;
	
	protected GTFSObject(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public boolean hasId(String id) {
		return this.id.equals(id);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof GTFSObject) {
			return id.equals(((GTFSObject)o).id);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
