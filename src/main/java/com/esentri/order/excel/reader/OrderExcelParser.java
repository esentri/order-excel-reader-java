package com.esentri.order.excel.reader;

import com.esentri.order.excel.reader.model.Article;
import com.esentri.order.excel.reader.model.Company;
import com.esentri.order.excel.reader.model.Department;
import com.esentri.order.excel.reader.model.Order;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

final class OrderExcelParser {

    private static final int COMPANY_COLUMN_INDEX = 0;
    private static final int DEPARTMENT_COLUMN_INDEX = 1;
    private static final int ARTICLE_COLUMN_INDEX = 2;
    private static final int AMOUNT_COLUMN_INDEX = 3;
    private final Workbook workbook;
    private List<Order> orders;

    OrderExcelParser(final Workbook workbook) {
        this.workbook = workbook;
        this.orders = new ArrayList<>();
    }

    public List<Order> parseOrdersFromOrderExcel() {
        final Sheet orderSheet = workbook.getSheetAt(0);
        orders = new ArrayList<>();
        for (Row row : orderSheet) {
            if (row.getRowNum() == orderSheet.getFirstRowNum()) {
                continue;
            }
            orders.add(parseOrderRow(row));
        }
        return orders;
    }

    private Order parseOrderRow(final Row row) {
        final Company company = getCompanyFromRow(row);
        final Department department = getDepartmentFromRow(row);
        final Article article = getArticleFromRow(row);
        final int amount = getAmountFromRow(row);
        return new Order(company, department, article, amount);
    }

    private int getAmountFromRow(final Row row) {
        return (int) row.getCell(AMOUNT_COLUMN_INDEX).getNumericCellValue();
    }

    private Article getArticleFromRow(final Row row) {
        return Article.fromString(row.getCell(ARTICLE_COLUMN_INDEX).getStringCellValue());
    }

    private Department getDepartmentFromRow(final Row row) {
        return Department.fromString(row.getCell(DEPARTMENT_COLUMN_INDEX).getStringCellValue());
    }

    private Company getCompanyFromRow(final Row row) {
        return Company.fromString(row.getCell(COMPANY_COLUMN_INDEX).getStringCellValue());
    }
}
