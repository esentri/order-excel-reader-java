package com.esentri.order.excel.reader;

import java.util.Arrays;

enum Department {

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

    String getName() {
        return this.name;
    }

    static Department fromString(String string) {
        return Arrays.stream(Department.values())
            .filter(department -> department.name.equalsIgnoreCase(string))
            .findFirst()
            .orElse(null);
    }

}
