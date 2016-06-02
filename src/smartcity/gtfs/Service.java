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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Service extends GTFSObject {
	private boolean[] available;
	
	private LocalDate startDate;
	private LocalDate endDate;
	
	public enum Weekday {
		MONDAY(0),
		TUESDAY(1),
		WEDNESDAY(2),
		THURSDAY(3),
		FRIDAY(4),
		SATURDAY(5),
		SUNDAY(6);
		public final int index;
		private Weekday(int index) { this.index = index; }
	}
	
	public Service(String id) {
		super(id);
		this.available = new boolean[7];
		for (int i = 0; i < 7; i++)
			this.available[i] = true;
		this.startDate = LocalDate.now();
		this.endDate = LocalDate.now();
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setAvailable(Weekday day, boolean value) {
		this.available[day.index] = value;
	}
	
	public boolean isAvailable(Weekday day) {
		return this.available[day.index];
	}
	
	/**
	 * Returns the same representation as used in the file service.txt.
	 */
	@Override
	public String toString() {
		String sdate=startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String edate=endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String res = "";
		for (boolean b : available) {
			res += (b ? "1," : "0,");
		}
		return String.format("%s,%s%s,%s", getId(),res,sdate,edate);
	}
}
