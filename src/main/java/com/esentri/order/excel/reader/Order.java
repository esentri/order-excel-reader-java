package com.esentri.order.excel.reader;

class Order {

    private Company company;
    private Department department;
    private Article article;
    private int amount;

    Order(Company company, Department department, Article article, int amount) {
        this.company = company;
        this.department = department;
        this.article = article;
        this.amount = amount;
    }

    Company getCompany() {
        return company;
    }

    Department getDepartment() {
        return department;
    }

    Article getArticle() {
        return article;
    }

    int getAmount() {
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
