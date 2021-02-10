package com.esentri.order.excel.reader;

import com.esentri.order.excel.reader.model.Order;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

final class OrderExcelReader {

    private final Logger logger = LoggerFactory.getLogger(OrderExcelReader.class);
    private final String filePath;

    private List<Order> orders;

    OrderExcelReader(String filePath) {
        this.filePath = filePath;
        this.orders = new ArrayList<>();
    }

    void readOrderAndCalculateOutput() {
        try (Workbook wb = WorkbookFactory.create(new File(filePath));) {
            orders = parseOrdersFromOrderExcel(wb);
            writeOrderExcel();
            writePivotMap();
        } catch (
            IOException e) {
            logger.error("Fehler beim Lesen der Datei", e);
        }
    }

    private void writePivotMap() throws IOException {
        new PivotExcel(orders).writePivotMap();
    }

    private void writeOrderExcel() throws IOException {
        new OrderOverview(orders).createOverview();
    }

    private List<Order> parseOrdersFromOrderExcel(Workbook wb) {
        final OrderExcelParser orderExcelParser = new OrderExcelParser(wb);
        return orderExcelParser.parseOrdersFromOrderExcel();
    }

}
