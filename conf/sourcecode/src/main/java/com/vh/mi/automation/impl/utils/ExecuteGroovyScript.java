package com.vh.mi.automation.impl.utils;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Run arbitrary Groovy script file.
 * Usage mvn compile exec:java -Dexec.mainClass=com.vh.mi.automation.impl.utils.ExecuteGroovyScript -Dscript=path/to/scriptFile [-Dexec.args="argument1 argument2"]
 * All the arguments passed through exec.args are passed to the script
 * <p/>
 * By default if the script file is not found or any errors are exceptions are encoutered in script, it returns a status of 0
 * meaning success and the task(if run on bamboo) is considered a success. If you want to fail the task if an error occurs
 * set the System variable failTaskOnFailure to true
 * eg
 * mvn compile exec:java -Dexec.mainClass=com.vh.mi.automation.impl.utils.ExecuteGroovyScript -Dscript=path/to/scriptFile
 *
 * @author nimanandhar
 */
public class ExecuteGroovyScript {
    static Logger logger = LoggerFactory.getLogger(ExecuteGroovyScript.class);

    public static void main(String[] args) throws IOException {
        try {
            File groovyScriptFile = getGroovyScriptFile();
            if (groovyScriptFile == null) {
                if (!failOnError()) return;
                throw new Error("there was a problem while executing script. Perhaps the script file does not exist");
            }
            logger.info("");
            logger.info("Running groovy script file " + groovyScriptFile);
            logger.info("");

            Binding binding = new Binding();
            binding.setVariable("logger", logger);
            new GroovyShell(binding).run(groovyScriptFile, args);

        } catch (Exception exception) {
            logger.info(exception.getMessage());
            System.out.println(exception.getMessage());
            if (failOnError())
                throw new Error("There was a problem running script. " + exception.getMessage());
        }
    }

    private static File getGroovyScriptFile() {
        String script = System.getProperty("script");
        if (script == null) {
            logger.info("Please specify the script to execute using system property");
            return null;
        }

        File groovyScript = new File(script);
        if (!groovyScript.exists() || !groovyScript.isFile() || !groovyScript.canRead()) {
            logger.warn("There was a problem reading groovy script: " + script);
            return null;
        }
        return groovyScript;
    }

    private static boolean failOnError() {
        String failTaskOnFailure = System.getProperty("failTaskOnFailure");
        if (failTaskOnFailure != null && failTaskOnFailure.equals("true"))
            return true;
        return false;
    }
}
