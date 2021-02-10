package com.esentri.order.excel.reader;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class OrderExcelReaderCommandLine {

    private static final Logger logger = LoggerFactory.getLogger(OrderExcelReaderCommandLine.class);
    private static final String FILENAME_CMD_OPTION = "filename";
    private static final String FILENAME_CMD_OPTION_SHORT = "f";

    private final CommandLineParser commandLineParser;
    private final Options options;
    private CommandLine commandLine;

    OrderExcelReaderCommandLine() {
        this.commandLineParser = new DefaultParser();
        this.options = getOptions();
    }

    public void parseArguments(final String[] args) {
        commandLine = null;
        createCommandLine(args);
    }

    public String getFilenameCommandOption() {
        return commandLine.getOptionValue(FILENAME_CMD_OPTION);
    }

    public boolean hasFilename() {
        return commandLine.hasOption(FILENAME_CMD_OPTION);
    }

    public void printHelp() {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("OrderExcelReader", options, true);
    }

    private void createCommandLine(final String[] args) {
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Fehler beim Einlesen der Parameter", e);
            printHelp();
            System.exit(1);
        }
    }

    private Options getOptions() {
        Options options = new Options().addOption(FILENAME_CMD_OPTION_SHORT, FILENAME_CMD_OPTION, true, "Path to excel");
        return options;
    }
}
