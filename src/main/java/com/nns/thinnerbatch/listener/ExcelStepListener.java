package com.nns.thinnerbatch.listener;

import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExcelStepListener implements StepExecutionListener {

	public XSSFWorkbook workbook;
	public XSSFSheet xssfSheet;

	private String fileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_log.xlsx";
	private String outPath = "output/" + fileName;

	@Override
	public void beforeStep(StepExecution stepExecution){


	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution){
		try{
			FileOutputStream out = new FileOutputStream(outPath);
			workbook.write(out);
			workbook.close();
			out.close();
			return ExitStatus.COMPLETED;
		}catch (IOException e){
			log.error("IOException error : " + e.getMessage());
			return ExitStatus.FAILED;
		}
	}


}
