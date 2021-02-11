package com.esentri.order.excel.reader;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    }

}
