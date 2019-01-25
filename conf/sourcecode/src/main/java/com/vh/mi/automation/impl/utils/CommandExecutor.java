package com.vh.mi.automation.impl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author nimanandhar
 */
public class CommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    public static void main(String[] args) throws IOException {
        String command = System.getProperty("command");
        if (command == null) {
            logger.warn("No command specified using -Dcommand system property. Using default command createSuites");
            command = "createSuites";
        }
        switch (command) {
            case "createSuites":
                System.setProperty("script", "suites/testClasses.groovy");
                ExecuteGroovyScript.main(args);
                break;
            default:
                throw new RuntimeException("Unknown command " + command);
        }
    }

}
