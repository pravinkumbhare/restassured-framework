package com.demo.utils;

//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;


//Utility to read JSON and Excel test data.

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class DataUtils {

    private static final Logger logger = LogManager.getLogger(DataUtils.class);


    // Read JSON data into Map to use in Payload
    public static Map<String, Object> readJsonAsMap(String jsonFilePath){

        try{
//            File file = new File(jsonFilePath);
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(file, Map.class);

            // above code converted to single line of code.
            return new ObjectMapper().readValue(new File(jsonFilePath), Map.class);

       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    // ✅ Read Excel into List of Maps (each row = Map)
    public static List<Map<String, String>> readExcelAsList(String filePath, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Map<String, String> rowMap = new HashMap<>();

                for (int j = 0; j < row.getLastCellNum(); j++) {
                    String key = headerRow.getCell(j).getStringCellValue();
                    String value = row.getCell(j).getStringCellValue();
                    rowMap.put(key, value);
                }
                dataList.add(rowMap);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }
        return dataList;
    }

}
