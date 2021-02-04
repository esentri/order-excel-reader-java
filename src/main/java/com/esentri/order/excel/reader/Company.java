package com.esentri.order.excel.reader;

import java.util.Arrays;

enum Company {

    TECICON("Tecicon"),
    DONTON("Donton"),
    TUMTUM("TumTum"),
    YAM("yam"),
    LANGINC("Lang Inc."),
    BERND("Bernd");

    private final String name;

    Company(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    static Company fromString(String string) {
        return Arrays.stream(Company.values())
            .filter(company -> company.name.equalsIgnoreCase(string))
            .findFirst()
            .orElse(null);
    }
}
