package com.esentri.order.excel.reader;

import com.esentri.order.excel.reader.model.Order;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

final class OrderExcelReader {

    private final Workbook outputWorkbook;
    private final Logger logger = LoggerFactory.getLogger(OrderExcelReader.class);
    private final String filePath;

    private List<Order> orders;

    OrderExcelReader(String filePath) {
        this.outputWorkbook = new XSSFWorkbook();
        this.filePath = filePath;
        this.orders = new ArrayList<>();
    }

    void readOrderAndCalculateOutput() {
        try (final Workbook inputWorkbook = WorkbookFactory.create(new File(filePath));) {
            orders = parseOrdersFromOrderExcel(inputWorkbook);
            writeOverviewSheet();
            writePivotSheet();
            writeFile();
        } catch (
            IOException e) {
            logger.error("Fehler beim Lesen der Datei", e);
        }
    }

    private void writeFile() {
        try {
            this.outputWorkbook.write(new FileOutputStream("order_overview_complete.xlsx"));
        } catch (IOException e) {
            logger.error("Fehler beim schreiben der Datei", e);
        }
    }

    private void writePivotSheet() {
        new PivotExcel(this.outputWorkbook, orders).writePivotMap();
    }

    private void writeOverviewSheet() {
        new OrderOverview(this.outputWorkbook, orders).createOverview();
    }

    private List<Order> parseOrdersFromOrderExcel(Workbook wb) {
        final OrderExcelParser orderExcelParser = new OrderExcelParser(wb);
        return orderExcelParser.parseOrdersFromOrderExcel();
    }

}
