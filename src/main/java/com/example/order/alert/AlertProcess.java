package com.example.order.alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.example.order.entity.KddReview;
import com.example.order.entity.KddReviewOwner;
import com.example.order.service.KddReviewOwnerService;
import com.example.order.service.KddReviewService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AlertProcess {

	private final KddReviewOwnerService kddReviewOwnerService;

	private final KddReviewService kddReviewService;

	// TODO required to changes base path as per server path
	public static String baseFolder = "C:\\Java Practice\\alert_demo";

	public AlertProcess(KddReviewOwnerService kddReviewOwnerService, KddReviewService kddReviewService) {
		this.kddReviewOwnerService = kddReviewOwnerService;
		this.kddReviewService = kddReviewService;
	}


	//@Scheduled(cron = "0 0 10,14,17 * * MON-FRI")
	@Scheduled(cron = "0 */1 * ? * *")
	public void batchFileScheduler() {
		
		this.processBatchFile();
	}

	public void processBatchFile() {

		File folder = new File(baseFolder);
		File[] listOfFiles = folder.listFiles();
		
		List<File> processedFileList = new ArrayList<>();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String fileName = file.getName();
				String ex = getFileExtension(file.getName());
				if ("xls".equalsIgnoreCase(ex) || "xlsx".equalsIgnoreCase(ex)) {

					if (fileName.contains("Batch_Reassignment")) {

						List<Map<Object, Object>> fileData = readExcelFile(file);
						
						// update table using fileData
						processedFileList.add(file);
					}

				} else {
					System.err.println("this file not allow :: extention -> " + ex);
				}
			}
		}
		
		createZipUsingFileList(processedFileList);
	}

	public static String getFileExtension(String fileName) {
		String fileExtension = null;
		if (fileName != null && !fileName.isEmpty()) {
			fileExtension = FilenameUtils.getExtension(fileName);
		}
		return fileExtension;
	}

	public List<Map<Object, Object>> readExcelFile(File file) {
		List<Map<Object, Object>> responseList = new ArrayList<>();
		XSSFWorkbook workBook = null;
		List<Long> ownerSeqIds = new ArrayList<>();

		try {
			if (file.isFile()) {

				workBook = new XSSFWorkbook(file);
				Sheet sheet = workBook.getSheetAt(0);

				final int lastRowCount = sheet.getLastRowNum();
				final int columnCount = sheet.getRow(0).getLastCellNum();

				if (lastRowCount > 0) {
					DataFormatter formatter = new DataFormatter();
					Row headerRow = sheet.getRow(0);

					for (int i = 1; i <= lastRowCount; i++) {
						Map<Object, Object> cellMap = new HashMap<>();
						Row row = sheet.getRow(i);
						if (row != null) {
								Long ownerSeqId = this.getOwnerSeqId(Long.valueOf(formatter.formatCellValue(row.getCell(1))));
								if(ownerSeqId != null){
									updateKddReview(ownerSeqId);
								}

							for (int j = 0; j < columnCount; j++) {
								String cellValue = formatter.formatCellValue(row.getCell(j));
								cellMap.put(formatter.formatCellValue(headerRow.getCell(j)),
										cellValue.isEmpty() ? null : cellValue);
							}
							responseList.add(cellMap);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

	public Long getOwnerSeqId(Long ownerId)
	{
		KddReviewOwner kddReviewOwner =  kddReviewOwnerService.findByKddReviewOwnerId(ownerId);
		if(kddReviewOwner != null)
			return kddReviewOwner.getOwnerSeqId();
		else
			return null;
	}

	public void updateKddReview(Long ownerSeqId){
		KddReview kddReview = kddReviewService.findByOwnerSeqId(ownerSeqId);
		if(kddReview != null){
			kddReview.setRemark("Updated on " + new Date().toString());
			kddReviewService.save(kddReview);
		}
	}
	
	public static void createZipUsingFileList(List<File> processedFileList) {

        try {
        	
        	if(processedFileList != null && !processedFileList.isEmpty()) {
        		
        		String archiveFolderPath = baseFolder+"\\archive\\";
        		
        		File theDir = new File(archiveFolderPath);
        		if (!theDir.exists()){
        		    theDir.mkdirs();
        		}
        		
        		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-YYYY-hhmmaa");
        		String zipFolderName = formatter.format(new Date());
        		
        		String outputZipFile = archiveFolderPath + zipFolderName + ".zip";
                FileOutputStream fos = new FileOutputStream(outputZipFile);
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                for (File fileToZip : processedFileList) {
                    FileInputStream fis = new FileInputStream(fileToZip);
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    fis.close();
                }
                zipOut.close();
                fos.close();
        		
        	}
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
