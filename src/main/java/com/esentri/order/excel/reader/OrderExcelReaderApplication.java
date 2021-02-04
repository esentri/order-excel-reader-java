package com.esentri.order.excel.reader;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderExcelReaderApplication {

    private static final Logger logger = LoggerFactory.getLogger(OrderExcelReaderApplication.class);
    private static final String FILENAME_CMD_OPTION = "filename";
    private static final String FILENAME_CMD_OPTION_SHORT = "f";

    public static void main(String... args) {
        long start = System.nanoTime();

        CommandLine commandLine = parseArguments(args);

        if (commandLine.hasOption(FILENAME_CMD_OPTION)) {
            final String filePath = commandLine.getOptionValue(FILENAME_CMD_OPTION);
            OrderExcelReader orderExcelReader = new OrderExcelReader(filePath);
            orderExcelReader.readOrderAndCalculateOutput();
        } else {
            printHelp();
        }

        long end = System.nanoTime();
        logger.info("Total in Nanoseconds: {}", end - start);
    }

    private static void printHelp() {
        Options options = getOptions();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("OrderExcelReader", options, true);
    }

    private static CommandLine parseArguments(String[] args) {

        Options options = getOptions();
        CommandLine cmd = null;
        CommandLineParser commandLineParser = new DefaultParser();

        try {
            cmd = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Fehler beim Einlesen der Parameter", e);
            printHelp();
            System.exit(1);
        }

        return cmd;
    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption(FILENAME_CMD_OPTION_SHORT, FILENAME_CMD_OPTION, true, "Path to excel");
        return options;
    }
}
