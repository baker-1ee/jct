package org.example.jct.report.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.jct.analyzer.AnalyzedQuery;
import org.example.jct.report.ReportGenerator;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ExcelReportGenerator implements ReportGenerator {

    @Override
    public void generate(List<AnalyzedQuery> queries) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("file path");
            headerRow.createCell(1).setCellValue("query iD");
            headerRow.createCell(2).setCellValue("sql");
            headerRow.createCell(3).setCellValue("oracle keywords");
            headerRow.createCell(4).setCellValue("자동 변환 가능 여부");
            headerRow.createCell(5).setCellValue("안내사항");

            // Fill data rows
            int rowNum = 1;
            for (AnalyzedQuery query : queries) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(query.getFilePath());
                row.createCell(1).setCellValue(query.getQueryId());
                row.createCell(2).setCellValue(query.getSql());
                row.createCell(3).setCellValue(query.getRules());
                row.createCell(4).setCellValue(query.isAbleAutoConversion());
                row.createCell(5).setCellValue(query.getGuidelines());
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
