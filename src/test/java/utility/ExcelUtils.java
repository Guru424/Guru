package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.Constants;

import executionEngine.DriverScript;
	
	/*Created ExcelUtils.java and Modified by Guru on 19/02/2018
	This class is used for extracting data from excel and writing data to sheet  */

public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static org.apache.poi.ss.usermodel.Cell Cell;
	private static XSSFRow Row;
	
	/*
	 * This function is used to set the Excel file.
	 */
	
	public static void setExcelFile(String Path) throws Exception {
		try {
			FileInputStream ExcelFile = new FileInputStream(Path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			}
		catch (Exception e){
			Log.error("Class Utils | Method setExcelFile | Exception desc : "+e.getMessage());
			DriverScript.bResult = false;
			}
		}
	
	/*
	 * This function is used to get the data from specified row, column and sheet.
	 */

	public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception{
		try{
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;
			}
		catch (Exception e){
			Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
			DriverScript.bResult = false;
			return"";
			}
		}

	
	/*
	 * This function is used to get the row count.
	 */

	public static int getRowCount(String SheetName){
		int iNumber=0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			iNumber=ExcelWSheet.getLastRowNum()+1;
			}
		catch (Exception e){
			Log.error("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());
			DriverScript.bResult = false;
			}
		return iNumber;
		}

	/*
	 * This function is used to get the row count in specified column which contains specified testcase name.
	 */
	
	public static int getRowContains(String sTestCaseName, int colNum,String SheetName) throws Exception{
		int iRowNum=0;	
		try {
			int rowCount = ExcelUtils.getRowCount(SheetName);
			for (; iRowNum<rowCount; iRowNum++){
				if  (ExcelUtils.getCellData(iRowNum,colNum,SheetName).equals(sTestCaseName)){
					break;
				}
			  }       			
			}
		catch (Exception e){
			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
			DriverScript.bResult = false;
			}
		return iRowNum;
		}
	
	/*
	 * This function is used to get the test steps count from specified test case start number which contains specified testcase name.
	 */

	public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception{
		try {
			for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(SheetName);i++){
				if(!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, SheetName))){
					int number = i;
					return number;      				
				}
			  }
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int number=ExcelWSheet.getLastRowNum()+1;
			return number;
			}
		catch (Exception e){
			Log.error("Class Utils | Method getTestStepsCount| Exception desc : "+e.getMessage());
			DriverScript.bResult = false;
			return 0;
			}
		}

	/*
	 * This function is used to set the data to specified sheet giving row and column number.
	 */
	
	@SuppressWarnings("static-access")
	public static void setCellData(String Result,  int RowNum, int ColNum, String SheetName) throws Exception    {
		try{
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Row  = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}
			FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
			ExcelWBook.write(fileOut);
			fileOut.close();
			ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
			}
		catch(Exception e){
			Log.error("Class Utils | Method setCellData| Exception desc : "+e.getMessage());
			DriverScript.bResult = false;

		}
	}
	
	/*
	 * This function is used to set the integer data to specified sheet giving row and column number.
	 */
	
	@SuppressWarnings("static-access")
	public static void setIntCellData(int Result,  int RowNum, int ColNum, String SheetName) throws Exception    {
		try{
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Row  = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				//Cell.setCellType(Result);
				 Cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				 Cell.setCellValue(Result);
			} else {
				Cell = Row.createCell(ColNum);
				Cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				Cell.setCellValue(Result);
			}
			FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
			ExcelWBook.write(fileOut);
			fileOut.close();
			ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
			}
		catch(Exception e){
			Log.error("Class Utils | Method setIntCellData| Exception desc : "+e.getMessage());
			DriverScript.bResult = false;

		}
	}

}