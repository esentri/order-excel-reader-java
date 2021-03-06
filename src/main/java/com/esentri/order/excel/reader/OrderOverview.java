package com.esentri.order.excel.reader;

import com.esentri.order.excel.reader.model.Order;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

final class OrderOverview {

    private final List<Order> orders;

    OrderOverview(List<Order> orders) {
        this.orders = orders;
    }

    void createOverview() throws IOException {
        try (
            final Workbook orderOverviewSummary = new XSSFWorkbook();
            final FileOutputStream fileOut = new FileOutputStream("order_overview_complete.xlsx")
        ) {
            Sheet sheet = orderOverviewSummary.createSheet("Bestellungen");
            Row row = sheet.createRow(0);
            CaptionUtil.generateCaptions(row, "Firma", "Abteilung", "Artikel", "Menge", "Einzelpreis", "Gesamtpreis");
            generateColumns(orders, sheet);
            orderOverviewSummary.write(fileOut);
        }
    }

    private void generateColumns(List<Order> orders, Sheet sheet) {
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
}
