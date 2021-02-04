package com.esentri.order.excel.reader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public final class CaptionUtil {

    public static void generateCaptions(Row captionRow, String... captions) {
        for (int i = 0; i < captions.length; i++) {
            Cell cell = captionRow.createCell(i);
            cell.setCellValue(captions[i]);
        }
    }

}
