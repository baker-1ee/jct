package org.example.jct.report;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.jct.data.OracleQuery;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class ExcelReportGenerator implements ReportGenerator {

    @Override
    public void generateReport(List<OracleQuery> queries) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("file path");
            headerRow.createCell(1).setCellValue("query iD");
            headerRow.createCell(2).setCellValue("sql");
            headerRow.createCell(3).setCellValue("oracle keywords");

            // Fill data rows
            int rowNum = 1;
            for (OracleQuery query : queries) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(query.getFilePath());
                row.createCell(1).setCellValue(query.getQueryId());
                row.createCell(2).setCellValue(query.getSql());
                row.createCell(3).setCellValue(String.join(",", query.getKeywords()));
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(generateFileName())) {
                workbook.write(fileOut);
            } catch (IOException e) {
                log.error("Failed to write report to file", e);
            }
        } catch (IOException e) {
            log.error("Failed to create excel workbook", e);
        }
    }

    private String generateFileName() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return "jct_report_" + timestamp + ".xlsx";
    }
}
