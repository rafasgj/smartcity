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

import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class CSVReader {
	private String[] fields;
	private String separator;
	private Scanner sc;
	
	public CSVReader(String filename, String separator)
		throws FileNotFoundException
	{
		sc = new Scanner(new java.io.File(filename));
		configure(separator);
	}
	
	public CSVReader(java.io.InputStream is, String separator)
	{
		sc = new Scanner(is);
		configure(separator);
	}
	
	private void configure(String separator) {
		this.separator = separator;
		sc.useDelimiter("["+separator + "\r\n]");
		sc.useLocale(Locale.US);
		String firstline = sc.nextLine();
		fields = firstline.split(separator);
	}
	
	public String[] getFields() {
		String[] res = new String[fields.length];
		System.arraycopy(fields, 0, res, 0, fields.length);
		return res;
	}

	public boolean hasNext() {
		return sc.hasNext();
	}
	
	public String[] nextRecord() {
		return sc.nextLine().split(separator);
	}
	
	public String next() {
		return sc.next();
	}
	
	public int nextInt() {
		return sc.nextInt();
	}
	
	public double nextDouble() {
		return sc.nextDouble();
	}
	
	public void skipNext() {
		sc.next();
	}

	public void skipNext(int amount) {
		for (int i = 0; i < amount; i++)
			skipNext();
	}

	public int getRecordSize() {
		return fields.length;
	}
}
