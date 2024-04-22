package com.nns.thinnerbatch.common.excel;

import java.io.FileOutputStream;
import java.lang.reflect.Field;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class SimpleExcelFile<T> {

	private static final SpreadsheetVersion supplyExcelVersion = SpreadsheetVersion.EXCEL2007;
	private static final int ROW_START_INDEX = 0;
	private static final int COLUMN_START_INDEX = 0;

	private SXSSFWorkbook workbook;
	private Sheet sheet;
	private SimpleExcelMetaData excelMetaData;

	private String fileName;
	private String outPath = "output/" + fileName;

	public SimpleExcelFile(List<T> data, Class<T> type, String fileName){
		validateMaxRow(data);
		this.fileName = fileName;
		this.workbook = new SXSSFWorkbook();
		this.excelMetaData = new SimpleExcelMetaData(type);
		renderExcel(data);

	}

	private void validateMaxRow(List<T> data){
		int maxRows = supplyExcelVersion.getMaxRows();
		if(data.size() > maxRows){
			throw new IllegalArgumentException(
				String.format("This ExcelFile does not support over %s rows", maxRows));
		}
	}

	public void renderExcel(List<T> data) {
		// Create sheet and render headers
		sheet = workbook.createSheet();
		renderHeadersWithNewSheet(sheet, ROW_START_INDEX, COLUMN_START_INDEX);

		if (data.isEmpty()) {
			return;
		}

		// Render Body
		int rowIndex = ROW_START_INDEX + 1;
		for (Object renderedData : data) {
			renderBody(renderedData, rowIndex++, COLUMN_START_INDEX);
		}
	}

	private void renderHeadersWithNewSheet(Sheet sheet, int rowIdx, int columnStartIdx){
		Row row = sheet.createRow(rowIdx);
		int columnIdx = columnStartIdx;

		for(String dataFieldName : excelMetaData.getDataFieldNames()){
			Cell cell = row.createCell(columnIdx++);
			cell.setCellValue(excelMetaData.getExcelHeaderName(dataFieldName));
		}
	}

	private void renderBody(Object data, int rowIdx, int columnStartIdx){
		Row row = sheet.createRow(rowIdx);
		int columnIdx = columnStartIdx;

		for(String dataFieldName : excelMetaData.getDataFieldNames()){
			Cell cell = row.createCell(columnIdx++);

			try{
				Field field = data.getClass().getField(dataFieldName);
				field.setAccessible(true);
				renderCellValue(cell, field.get(data));

			}catch (Exception e){
					throw new ExcelInternalException(e.getMessage(), e);
			}
		}
	}

	private void renderCellValue(Cell cell, Object cellValue) {
		if(cellValue instanceof Number){
			Number numberValue = (Number) cellValue;
			cell.setCellValue(numberValue.doubleValue());
			return;

		}
		cell.setCellValue(cellValue == null ? "" : cellValue.toString());
	}

	public void write() throws IOException{

		FileOutputStream out = new FileOutputStream(outPath);
		workbook.write(out);
		workbook.close();
		workbook.dispose();
		out.close();
	}

}
