package com.esentri.order.excel.reader.model;

public class Order {

    private final Company company;
    private final Department department;
    private final Article article;
    private final int amount;

    public Order(Company company, Department department, Article article, int amount) {
        this.company = company;
        this.department = department;
        this.article = article;
        this.amount = amount;
    }

    public Company getCompany() {
        return company;
    }

    public Department getDepartment() {
        return department;
    }

    public Article getArticle() {
        return article;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Order{" +
            "company=" + company +
            ", department=" + department +
            ", article=" + article +
            ", amount=" + amount +
            '}';
    }
}
