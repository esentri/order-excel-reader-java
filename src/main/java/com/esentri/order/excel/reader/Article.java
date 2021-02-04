package com.esentri.order.excel.reader;

import java.util.Arrays;

enum Article {

    TONER("Toner", 134.79),
    PAPER_CLIP("Büroklammern", 1.24),
    PEN("Stift", 3.92),
    PAPER("Druckerpapier", 4.53),
    MARKERS("Whiteboard Marker", 7.67),
    FOLDER("Ordner", 1.99);

    private final String name;
    private final double pricePerPiece;

    Article(String name, double pricePerPiece) {
        this.name = name;
        this.pricePerPiece = pricePerPiece;
    }

    String getName() {
        return this.name;
    }

    double getPricePerPiece() {
        return this.pricePerPiece;
    }

    static Article fromString(String string) {
        return Arrays.stream(Article.values())
            .filter(article -> article.name.equalsIgnoreCase(string))
            .findFirst()
            .orElse(null);
    }
}
