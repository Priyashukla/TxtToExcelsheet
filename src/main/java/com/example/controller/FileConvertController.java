package com.example.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileConvertController {
	
	@RequestMapping(value="/convertToExcel",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Resource> getData(@RequestParam("file")  MultipartFile file) {
		 if (!file.isEmpty()) {
			 try {
				 byte[] bytes = file.getBytes();
		         String completeData = new String(bytes);
		         System.out.println(completeData);
		         Workbook workbook =convertIntoExcel(completeData);
		         bytes = new byte[1024];
		         String fileName=System.getProperty("java.io.tmpdir", "D:\\logs")+"\\tmp.xls";
		         ByteArrayInputStream in  = write(workbook);
		         FileOutputStream fos = write(workbook, fileName);
		          fos.write(bytes);
		          fos.flush();
		          fos.close();
		          ByteArrayResource resource=new ByteArrayResource(bytes);
		          return ResponseEntity.ok()
		        	       
		        	        .contentLength(resource.contentLength())
		        	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
		        	        .body(resource);
		         
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	private FileOutputStream write(final Workbook workbook, final String filename) throws IOException {
	      FileOutputStream fos = new FileOutputStream(filename);
	      workbook.write(fos);
	   
	      return fos;
	  }  
	
	private ByteArrayInputStream write(final Workbook workbook) throws IOException {
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		    try {
		        workbook.write(out);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return new ByteArrayInputStream(out.toByteArray());
	  }  
	
	private Workbook  convertIntoExcel(String txtData) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("student");
		String[] lines=txtData.split("\n");
		int rowCount = 0;
		for(String line:lines) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			String[] data=line.split("\\|");
			for(String colData:data) {
				Cell cell = row.createCell(columnCount++);
				cell.setCellValue((String) colData);
				
			}
			
		}
		
		return workbook;
	}
	

}
