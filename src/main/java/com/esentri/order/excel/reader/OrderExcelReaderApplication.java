package com.esentri.order.excel.reader;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.esentri.clojure.order.excel.reader;

public final class OrderExcelReaderApplication {

    private static final Logger logger = LoggerFactory.getLogger(OrderExcelReaderApplication.class);

    public static void main(final String... args) {
        final long start = System.nanoTime();
        final OrderExcelReaderCommandLine orderExcelReaderCommandLine = new OrderExcelReaderCommandLine();
        orderExcelReaderCommandLine.parseArguments(args);

        if (!orderExcelReaderCommandLine.hasFilename()) {
            orderExcelReaderCommandLine.printHelp();
            return;
        }
        final String filePath = orderExcelReaderCommandLine.getFilenameCommandOption();
        final OrderExcelReader orderExcelReader = new OrderExcelReader(filePath);
        orderExcelReader.readOrderAndCalculateOutput();

        final long end = System.nanoTime();
        logger.info("Java Total in Nanoseconds: {}", end - start);

        logger.info("Clojure run");
        final long cljstart = System.nanoTime();
        reader.execute(filePath, "output-clojure.xlsx");
        final long cljstop = System.nanoTime();
        logger.info("Total: {}", cljstop - cljstart);
    }

}
