package com.zopSmart.genericUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class ExcelUtil {
	public String getDataFromExcel(String sheetName, int rownum, int cellnum)
			throws EncryptedDocumentException, IOException {
		FileInputStream file = new FileInputStream("./TestCase.xlsx");
		Workbook book = WorkbookFactory.create(file);
		Sheet sheet = book.getSheet(sheetName);
		Row row = sheet.getRow(rownum);
		Cell cell = row.getCell(cellnum);
		String stringCellValue = cell.getStringCellValue();
		return stringCellValue;
	}

	public int rowCount(String sheetName) throws EncryptedDocumentException, IOException {
		FileInputStream file = new FileInputStream("./TestCase.xlsx");
		Workbook book = WorkbookFactory.create(file);
		Sheet sheet = book.getSheet(sheetName);
		int lastRowNum = sheet.getLastRowNum();
		return lastRowNum;
	}

	public int colCount(String sheetName) throws EncryptedDocumentException, IOException {
		FileInputStream file = new FileInputStream("./TestCase.xlsx");
		Workbook book = WorkbookFactory.create(file);
		Sheet sheet = book.getSheet(sheetName);
		int numCellStyles = book.getNumCellStyles();
		short leftCol = sheet.getLeftCol();
		return numCellStyles;
	}

	public String getExcelData(String sheetName, String expTestID, String expColHEader) throws Throwable {

		int expTestRow = 0;
		int expHeader = 0;
		FileInputStream fis = new FileInputStream("./TestCase.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet(sheetName);
		int rowCount = sh.getLastRowNum();

		for (int i = 0; i <= rowCount; i++) {
			Row row = sh.getRow(i);
			String zeroColData = row.getCell(0).getStringCellValue();
			if (expTestID.contentEquals(zeroColData)) {
				// System.out.println("test is availbale");
				expTestRow = i;
				break;
			}
		}

	//	int expColHeader = expTestRow - 1;

		int colCount = sh.getRow(expTestRow).getLastCellNum();
		for (int j = 0; j < colCount; j++) {
			String actColHeader = sh.getRow(0).getCell(j).getStringCellValue();
			if (actColHeader.equals(expColHEader)) {
				// System.out.println("header is avibale ");
				expHeader = j;
				break;
			}
		}

		return sh.getRow(expTestRow).getCell(expHeader).getStringCellValue();

	}
}
