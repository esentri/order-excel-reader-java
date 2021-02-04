package com.esentri.order.excel.reader.model;

import java.util.Arrays;

public enum Department {

    IT("IT Abteilung"),
    SALES("Vertrieb"),
    MARKETING("Marketing"),
    HR("Personal"),
    PRODUCTMANAGEMENT("Produktmanagement"),
    CONTROLLING("Controlling");

    private final String name;

    Department(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Department fromString(String string) {
        return Arrays.stream(Department.values())
            .filter(department -> department.name.equalsIgnoreCase(string))
            .findFirst()
            .orElse(null);
    }

}
