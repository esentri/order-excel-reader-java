package com.esentri.order.excel.reader;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class OrderExcelReader {

    private static final Logger logger = LoggerFactory.getLogger(OrderExcelReader.class);

    public static void main(String... args) {
        long start = System.nanoTime();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (InputStream inp = classloader.getResourceAsStream("order_overview.xlsx")) {
            assert inp != null;
            try (Workbook wb = WorkbookFactory.create(inp)) {
                final Sheet sheetAt = wb.getSheetAt(0);

                List<Order> orders = new ArrayList<>();
                for (Row row : sheetAt) {
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    Company company = Company.fromString(row.getCell(0).getStringCellValue());
                    Department department = Department.fromString(row.getCell(1).getStringCellValue());
                    Article article = Article.fromString(row.getCell(2).getStringCellValue());
                    int amount = (int) row.getCell(3).getNumericCellValue();
                    Order order = new Order(company, department, article, amount);
                    orders.add(order);
                }

                try (
                    final Workbook outwb = new XSSFWorkbook();
                    final FileOutputStream fileOut = new FileOutputStream("order_overview_complete.xlsx")
                ) {
                    Sheet sheet = outwb.createSheet("Bestellungen");
                    Row row = sheet.createRow(0);
                    generateCaptions(row, "Firma", "Abteilung", "Artikel", "Menge", "Einzelpreis", "Gesamtpreis");
                    generateColumns(orders, sheet);
                    outwb.write(fileOut);
                }

                Map<Company, Map<Article, Integer>> leMap = new EnumMap<>(Company.class);

                Arrays.stream(Company.values()).forEach(company -> leMap.put(company, new EnumMap<>(Article.class)));

                leMap.forEach((key, value) -> Arrays.stream(Article.values()).forEach(article -> leMap.get(key).put(article, 0)));

                orders.forEach(order -> {
                    final Map<Article, Integer> articleIntegerMap = leMap.get(order.getCompany());
                    articleIntegerMap.put(order.getArticle(), articleIntegerMap.get(order.getArticle()) + order.getAmount());
                });


                try (
                    final Workbook finalwb = new XSSFWorkbook();
                    final FileOutputStream fileOut = new FileOutputStream("order_pivot.xlsx")
                ) {
                    Sheet sheet = finalwb.createSheet("Zusammenfassung");
                    Row row = sheet.createRow(0);
                    generateCaptions(row, "Firma", "Artikel", "Gesamtmenge", "Gesamtpreis");
                    leMap.forEach((key, value) -> value.forEach((innerKey, val) -> {
                        Row r2 = sheet.createRow(sheet.getLastRowNum()+1);
                        r2.createCell(0).setCellValue(key.getName());
                        r2.createCell(1).setCellValue(innerKey.getName());
                        r2.createCell(2).setCellValue(val);
                        r2.createCell(3).setCellValue(innerKey.getPricePerPiece() * val);
                    }));
                    finalwb.write(fileOut);
                }
                // ---------------------------------------------------
                // FILL DESIRED OUTPUT EXCEL
                // ---------------------------------------------------

            }

        } catch (IOException e) {
            logger.error("Fehler beim Lesen der Datei", e);
        }
        long end = System.nanoTime();
        logger.info("Total in Nanoseconds: {}", end - start);
    }

    private static void generateColumns(List<Order> orders, Sheet sheet) {
        int rowIndex = 1;
        for (Order order : orders) {
            Row row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(order.getCompany().getName());
            row.createCell(1).setCellValue(order.getDepartment().getName());
            row.createCell(2).setCellValue(order.getArticle().getName());
            row.createCell(3).setCellValue(order.getAmount());
            row.createCell(4).setCellValue(order.getArticle().getPricePerPiece());
            row.createCell(5).setCellValue(order.getAmount() * order.getArticle().getPricePerPiece());
            rowIndex++;
        }

    }

    private static void generateCaptions(Row captionRow, String... captions) {
        for (int i = 0; i < captions.length; i++) {
            Cell cell = captionRow.createCell(i);
            cell.setCellValue(captions[i]);
        }
    }
}
