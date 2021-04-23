package com.esentri.order.excel.reader;

import com.esentri.order.excel.reader.model.Article;
import com.esentri.order.excel.reader.model.Company;
import com.esentri.order.excel.reader.model.Order;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

final class PivotExcel {

    private final Workbook workbook;
    private final List<Order> orders;

    PivotExcel(Workbook workbook, List<Order> orders) {
        this.workbook = workbook;
        this.orders = orders;
    }

    public void writePivotMap() {
        Map<Company, Map<Article, Integer>> pivotMap = createPivotMap(orders);
        writePivotOrderExcel(pivotMap);
    }

    private void writePivotOrderExcel(Map<Company, Map<Article, Integer>> leMap) {
        Sheet sheet = workbook.createSheet("Summe je Firma");
        Row row = sheet.createRow(0);
        CaptionUtil.generateCaptions(row, "Firma", "Artikel", "Gesamtmenge", "Gesamtpreis");
        leMap.forEach((key, value) -> value.forEach((innerKey, val) -> addRow(sheet, key, innerKey, val)));

    }

    private Map<Company, Map<Article, Integer>> createPivotMap(List<Order> orders) {
        Map<Company, Map<Article, Integer>> pivotMap = initializePivotMap();

        orders.forEach(order -> {
            final Map<Article, Integer> articleIntegerMap = pivotMap.get(order.getCompany());
            articleIntegerMap.put(order.getArticle(), articleIntegerMap.get(order.getArticle()) + order.getAmount());
        });
        return pivotMap;
    }

    private Map<Company, Map<Article, Integer>> initializePivotMap() {
        Map<Company, Map<Article, Integer>> leMap = new EnumMap<>(Company.class);

        Arrays.stream(Company.values()).forEach(company -> leMap.put(company, new EnumMap<>(Article.class)));

        leMap.forEach((key, value) -> Arrays.stream(Article.values()).forEach(article -> leMap.get(key).put(article, 0)));
        return leMap;
    }

    private void addRow(Sheet sheet, Company company, Article article, Integer amount) {
        Row r2 = sheet.createRow(sheet.getLastRowNum() + 1);
        r2.createCell(0).setCellValue(company.getName());
        r2.createCell(1).setCellValue(article.getName());
        r2.createCell(2).setCellValue(amount);
        r2.createCell(3).setCellValue(article.getPricePerPiece() * amount);
    }
}
