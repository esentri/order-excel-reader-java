package com.esentri.order.excel.reader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

final class CaptionUtil {

    private CaptionUtil() {
        // Util-Class shall not be instantiated
    }

    public static void generateCaptions(final Row captionRow, final String... captions) {
        for (int i = 0; i < captions.length; i++) {
            final Cell cell = captionRow.createCell(i);
            cell.setCellValue(captions[i]);
        }
    }

}
