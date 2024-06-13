package reusableMethod;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

public class Reusable {

    public String CreateJsonBody(String id,String petName) {
        String body = "{\n" +
                "    \"id\":"+id+",\n" +
                "    \"category\":{\n" +
                "        \"id\":0,\n" +
                "        \"name\":\"string\"\n" +
                "    },\n" +
                "    \"name\":\""+petName+"\",\n" +
                "    \"PhotoUrls\":[\n" +
                "        \"string\"\n" +
                "    ],\n" +
                "    \"tags\":[\n" +
                "        {\n" +
                "            \"id\":0,\n" +
                "            \"name\":\"string\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"status\":\"available\"\n" +
                "}";
        return body;
    }

    public static void readAndPrintDataFromExcel(){
        try {
            String xlFile = "C:\\Users\\akshatan\\Desktop\\PetData.xlsx";
            FileInputStream f = new FileInputStream(xlFile);
            Workbook workbook = new XSSFWorkbook(f);
            Sheet sheet = workbook.getSheet("Sheet1");
            int lastRow = sheet.getLastRowNum();

            Map<String, List<String>> dataMap = new HashMap<>();

            for (int i = 0; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    int lastCell = row.getLastCellNum();
                    String key = row.getCell(0).getStringCellValue();
                    List<String> values = new ArrayList<>();

                    for (int j = 1; j < lastCell; j++) {
                        values.add(row.getCell(j).getStringCellValue());
                    }
                    dataMap.put(key, values);

                }
            }

            for (Map.Entry<String,List<String>> entry : dataMap.entrySet()) {
                System.out.println("ID = " + entry.getKey() +
                        ", Name = " + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
