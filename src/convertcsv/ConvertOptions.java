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
package convertcsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import config.configxml.Settings;

public class ConvertOptions extends ConvertFile{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public File convertToDesiredFormat(String filePath) throws Exception {
		
		String date=filePath.substring(filePath.length()-12, filePath.length()-4);
		String expDate = null;
		date = formatFileDate(date);
	    //READ FILE
		FileReader reader=new FileReader(filePath);
		FileWriter writer =null;
		BufferedReader bufferReader=new BufferedReader(reader);
		String optionsDir= Settings.getSettings().getDownload().getOptions().getDirectory();
		File convertedFile = new File(optionsDir+"/OPT_"+date+".txt");
		convertedFile.getParentFile().mkdirs();
		writer=new FileWriter(convertedFile);
		PrintWriter printWriter=new PrintWriter(writer);
		String line=bufferReader.readLine();
/*		String previousStock=null;
		int count=1;*/
		while((line=bufferReader.readLine())!=null)
		{
			List<String> row=Arrays.asList(line.split(","));
			if(row.get(0).replaceAll(" ", "").equals("OPTIDX") || row.get(0).replaceAll(" ", "").equals("OPTSTK"))
			{
				String curr_stock=(String) row.get(1).replaceAll(" ", "");
/*			if(previousStock==null || !(previousStock.equals(curr_stock))){
				previousStock=curr_stock;
				count=1;
				}*/
				curr_stock=createOptionsSymbol(curr_stock, expDate==null?formatDate(row.get(2).replaceAll(" ", ""),expDate):expDate, removeTrailingZeros(removeLeadingZeros(row.get(3).replaceAll(" ", ""))), row.get(4).replaceAll(" ", ""));
//				count++;
		
			String line2=curr_stock+","+//Stock name
			date+","+//Date YYYYMMDD
			row.get(5).replaceAll(" ", "")+","+//Open
			row.get(6).replaceAll(" ", "")+","+//High
			row.get(7).replaceAll(" ", "")+","+//Low
			row.get(8).replaceAll(" ", "")+","+//Close
			row.get(10).replaceAll(" ", "")+","+//Volume
			row.get(12).replaceAll(" ", "")+","+//Open Interest
			row.get(13).replaceAll(" ", "")+","+//Change In Open Interest
			row.get(9).replaceAll(" ", "")+","+//SETTLE_PR
			row.get(0).replaceAll(" ", "")+","+//INSTRUMENT
			row.get(1).replaceAll(" ", "")+","+//SYMBOL
			row.get(2).replaceAll(" ", "")+","+//EXPIRY DATE
			row.get(3).replaceAll(" ", "")+","+//STRIKE PX
			row.get(4).replaceAll(" ", "");//OPT TYPE
			
			printWriter.println(line2);
		}
		else 
		{
			continue;
		}
			
		}
		printWriter.close();
		writer.close();
		bufferReader.close();
		reader.close();
		return convertedFile;
		}
	
	
	//New function added which will add -I,-II,-III Defect ID::3197916
	private String createOptionsSymbol(String stock,String date, String strikePrice, String optType){
		String var="";
		var= stock+date+(strikePrice.endsWith(".")?strikePrice.substring(0, strikePrice.length()-1):strikePrice)+optType;
		return var;
	}
	
	private String formatDate(String actualDate, String expDate){
		Date tradeDate = null;
		try {
			tradeDate = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH).parse(actualDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		expDate = new SimpleDateFormat("yyMMM", Locale.ENGLISH).format(tradeDate);
		return expDate.toUpperCase();
	}
	
	public String removeLeadingZeros( String str ){
		if (str == null){
		return null;}
		char[] chars = str.toCharArray();
		int index = 0;
		for (; index < str.length();index++)
		{
		if (chars[index] != '0'){
		break;}
		}
		return (index == 0) ? str :str.substring(index);
	}
	
	public java.lang.String removeTrailingZeros( java.lang.String str ){
		if (str == null){
		return null;}
		char[] chars = str.toCharArray();int length,index ;length = str.length();
		index = length -1;
		for (; index >=0;index--)
		{
		if (chars[index] != '0'){
		break;}
		}
		return (index == length-1) ? str :str.substring(0,index+1);
	}
	
	private String formatFileDate(String actualDate){
		Date tradeDate = null;
		try {
			tradeDate = new SimpleDateFormat("ddmmyyyy", Locale.ENGLISH).parse(actualDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actualDate = new SimpleDateFormat("yyyymmdd", Locale.ENGLISH).format(tradeDate);
		return actualDate;
	}
}
