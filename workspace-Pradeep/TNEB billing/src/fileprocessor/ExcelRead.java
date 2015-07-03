package fileprocessor;



import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import tneb.BatchDao;
import tneb.ReportDto;

public class ExcelRead {
	public HashMap<Integer, Double> getRates(HSSFSheet sheet) {
		HashMap<Integer, Double> upperRateLimit = new HashMap<Integer,Double>();
		try {
		    HSSFRow row;
		    HSSFCell cell;
	
		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();
	
		    int cols = 0; // No of columns
		    int tmp = 0;
	
		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    for(int i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		        }
		    }
	
		    for(int r = 1; r < rows; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
	                cell = row.getCell(2);
	                if(cell != null && Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
	                    // Your code here
                		try {
                			upperRateLimit.put((int) row.getCell(1).getNumericCellValue(), row.getCell(2).getNumericCellValue());
                		} catch (Exception e) {
                			upperRateLimit.put(0, row.getCell(2).getNumericCellValue());
                		}
                	}
		        }
		    }
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
		return upperRateLimit;
	}
	
	public HashMap<Integer, Integer> getUniqueMeterIDs(HSSFSheet sheet) {
		HashMap<Integer, Integer> meterID = new HashMap<Integer,Integer>();
		try {
			HSSFRow row;
		    HSSFCell cell;
		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();
			for(int r = 0; r < rows; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
	                cell = row.getCell(0);
	                if(cell != null && Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
		                meterID.put((int) cell.getNumericCellValue(),r);
	                }
		        }
		    }
		} catch (Exception e) {
			System.out.println("Excpetion geting unique meter id " + e);
		}
		return meterID;
	}
	
	public HashMap<String, HashMap<Integer, Integer>> getMinimumAndMaximumReadingByDate(HSSFSheet sheet, HashMap<Integer, Integer> meterID, String dateformat, String fromDate, String toDate) {
		HashMap<Integer, Integer> minReading = new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> maxReading = new HashMap<Integer,Integer>();
		HashMap<String, HashMap<Integer, Integer>> minAndMaxReading = new HashMap<String, HashMap<Integer, Integer>>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
		try {
			dateFormat.parse(fromDate);
			HSSFRow row;
		    HSSFCell cell;
		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();
		    for (int id: meterID.keySet()) {
		    	LinkedList<Integer> minList = new LinkedList<Integer>();
		    	LinkedList<Integer> maxList = new LinkedList<Integer>();
				for(int r = 0; r < rows; r++) {
			        row = sheet.getRow(r);
			        if(row != null) {
		                cell = row.getCell(2);
		                if(cell != null && Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
		        	        if(id == (int)row.getCell(0).getNumericCellValue()) {
		        	        	minList.add((int)cell.getNumericCellValue());
		        	        	maxList.add((int)row.getCell(4).getNumericCellValue());
		        	        }
		                }
		            }
			    }
				minReading.put(id, Collections.min(minList,null));
				maxReading.put(id, Collections.max(maxList,null));
		    }
		} catch (Exception e) {
			System.out.println("getMinimumReadingByDate " + e);
		}
		minAndMaxReading.put("MINReading", minReading);
		minAndMaxReading.put("MAXReading", maxReading);
		return minAndMaxReading;
	}
	
	public void ReadFile() {
		try {
			String dateFormat = "MM/dd/yyyy hh:mm a";
			String fromDate = "6/01/2015 00:00 AM";
			String toDate = "6/30/2015 11:59 PM";
			HashMap<Integer, Double> upperRateLimit = new HashMap<Integer,Double>();
			HashMap<Integer, Integer> meterID = new HashMap<Integer,Integer>();
			HashMap<Integer, Integer> minReading = new HashMap<Integer,Integer>();
			HashMap<Integer, Integer> maxReading = new HashMap<Integer,Integer>();
			HashMap<String, HashMap<Integer, Integer>> minAndMaxReading = new HashMap<String, HashMap<Integer, Integer>>();
			String file = "C:\\Users\\Administrator\\Downloads\\apache-tomcat-7.0.62-windows-x64\\apache-tomcat-7.0.62\\lib\\Samplefile.xls";
		    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
		    try {
		    	HSSFSheet sheet = wb.getSheet("rate table");
		    	upperRateLimit = getRates(sheet);
		    } catch (Exception e) {
		    	System.out.println("Sheet not found " + e);
		    }
			try {
			    HSSFSheet sheet = wb.getSheet("usage template");
			    HSSFRow row;
		
			    int rows; // No of rows
			    rows = sheet.getPhysicalNumberOfRows();
		
			    int cols = 0; // No of columns
			    int tmp = 0;
		
			    // This trick ensures that we get the data properly even if it doesn't start from first few rows
			    for(int i = 0; i < 10 || i < rows; i++) {
			        row = sheet.getRow(i);
			        if(row != null) {
			            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
			            if(tmp > cols) cols = tmp;
			        }
			    }
		
			    // All unique MeterIds from excel sheet
			    meterID = getUniqueMeterIDs(sheet);
			    
			    // Minimum and Maximum Reading per meter Id
			    minAndMaxReading = getMinimumAndMaximumReadingByDate(sheet, meterID, dateFormat, fromDate, toDate);
			    
			    minReading = minAndMaxReading.get("MINReading");
			    maxReading = minAndMaxReading.get("MAXReading");
			    
			    for (int id: upperRateLimit.keySet()) {
	    	        Double value = upperRateLimit.get(id);	    	        
	    	        System.out.println("Rate " + id + ":" + value);
			    }
			    
			    for (int id: meterID.keySet()) {
	        	        int value = meterID.get(id);
	        	        System.out.println("masterID " + id + ":" + value);
			    }
			    
			    List<ReportDto> recordList = new ArrayList<ReportDto>();
			    
			    for (int id: minReading.keySet()) {
			    	ReportDto dto=null;
			    	System.out.println(id);
			    	for (int id2: maxReading.keySet()) {
			    		
			    		if(id == id2){
			    			System.out.println("two :"+id2);
			    			int valuemin = minReading.get(id);
			    			int valuemax = maxReading.get(id);
			    			dto = new ReportDto();
			    			dto.setCustId(Integer.toString(id));
			    			dto.setStartId(Integer.toString(valuemin));
			    			dto.setEndId(Integer.toString(valuemax));
			    			dto.setMonth("June");
			    		}
				    }
			    	recordList.add(dto);
			    }
			    BatchDao obj =  new BatchDao();
			    obj.batchReportInsert(recordList);
			    
			} catch(Exception ioe) {
			    ioe.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public static void main(String arg[]) {
		ExcelRead in = new ExcelRead();
		in.ReadFile();
	}*/
}
