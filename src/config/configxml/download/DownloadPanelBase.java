/*
Copyright (c) 2011,2012,2013,2014 Rohit Jhunjhunwala

The program is distributed under the terms of the GNU General Public License

This file is part of NSE EOD Data Downloader.

NSE EOD Data Downloader is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

NSE EOD Data Downloader is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with NSE EOD Data Downloader.  If not, see <http://www.gnu.org/licenses/>.
 */
package config.configxml.download;

import java.io.File;

import config.configxml.CheckBoxHolder;

public class DownloadPanelBase extends CheckBoxHolder {
	
	private String directory;

	public String getDirectory() {
		if(directory !=null && !directory.equals("")){
		File file = new File(directory);
		if(!file.exists())
			file.mkdirs();
		}
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}
}
