package com.esentri.order.excel.reader;

import com.esentri.order.excel.reader.model.Article;
import com.esentri.order.excel.reader.model.Company;
import com.esentri.order.excel.reader.model.Department;
import com.esentri.order.excel.reader.model.Order;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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

    OrderExcelReader(String filePath) {
        this.filePath = filePath;
    }

    void readOrderAndCalculateOutput() {
        try (Workbook wb = WorkbookFactory.create(new File(filePath));) {
            List<Order> orders = parseOrdersFromOrderExcel(wb);
            writeOrderExcel(orders);
            writePivotMap(orders);
        } catch (
            IOException e) {
            logger.error("Fehler beim Lesen der Datei", e);
        }
    }

    private void writePivotMap(List<Order> orders) throws IOException {
        new PivotExcel(orders).writePivotMap();
    }

    private void writeOrderExcel(List<Order> orders) throws IOException {
        new OrderOverviewSummary(orders).createOverviewSummary();
    }

    private List<Order> parseOrdersFromOrderExcel(Workbook wb) {
        final Sheet orderSheet = wb.getSheetAt(0);

        List<Order> orders = new ArrayList<>();
        for (Row row : orderSheet) {
            if (row.getRowNum() == orderSheet.getFirstRowNum()) {
                continue;
            }
            orders.add(readOrderFromRow(row));
        }
        return orders;
    }

    private Order readOrderFromRow(Row row) {
        Company company = Company.fromString(row.getCell(0).getStringCellValue());
        Department department = Department.fromString(row.getCell(1).getStringCellValue());
        Article article = Article.fromString(row.getCell(2).getStringCellValue());
        int amount = (int) row.getCell(3).getNumericCellValue();
        return new Order(company, department, article, amount);
    }


}
