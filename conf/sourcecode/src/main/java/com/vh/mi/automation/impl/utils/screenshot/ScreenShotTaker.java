package com.vh.mi.automation.impl.utils.screenshot;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nimanandhar
 */
public class ScreenShotTaker {
    protected static final Logger logger = LoggerFactory.getLogger(ScreenShotTaker.class);
    private static final String PATH_SEPARATOR = File.separator;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dMMMyyyy");
    private final File screenShotDirectory;
    private static final Object lock = new Object();

    public ScreenShotTaker() {
        this(getDefaultScreenShotPath());
    }

    public ScreenShotTaker(File screenShotsPath) {
        this.screenShotDirectory = screenShotsPath;
    }

    public void takeScreenShot(WebDriver webDriver, String fileName) {
        if (webDriver == null) {
            logger.error("Cannot take screenshot. WebDriver is null");
        }

        synchronized (lock) {
            File screenShotTempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            File screenshotFile = getScreenshotFile(fileName);
            try {
                FileUtils.copyFile(screenShotTempFile, screenshotFile);
            } catch (IOException e) {
                logger.error("Error occured when copying {} to {}. Exception {}", screenShotTempFile.getAbsolutePath(), screenshotFile.getAbsolutePath(), e);
            }
        }
    }


    private File getScreenshotFile(String fileName) {
        fileName = removeInvalidCharacters(fileName);

        if (fileName.length() >= 200) {
            fileName = fileName.substring(0, 196) + ".png";
        }

        return new File(screenShotDirectory.getPath() + File.separator + fileName);
    }


    private String removeInvalidCharacters(String fileName) {
        return fileName.replaceAll("[\\s/?<>:*|\"'\\\\]+", "_");
    }


    /**
     * Convenience method that returns a path in the currentDirectory
     * where the user may store the screenShots
     */
    private static File getDefaultScreenShotPath() {
        File userDirectory = new File(System.getProperty("user.dir"));
        String screenShotPath = userDirectory.getPath() + PATH_SEPARATOR + "target" + PATH_SEPARATOR + "screenShots" + PATH_SEPARATOR + today() + PATH_SEPARATOR;
        return new File(screenShotPath);
    }

    private static String today() {
        return DATE_FORMAT.format(new Date());
    }

}
