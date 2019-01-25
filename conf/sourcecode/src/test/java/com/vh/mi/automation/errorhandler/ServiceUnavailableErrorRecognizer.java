package com.vh.mi.automation.errorhandler;

import org.openqa.selenium.WebDriver;

/**
 * @author nimanandhar
 */
public class ServiceUnavailableErrorRecognizer {
    WebDriver webDriver;

    public ServiceUnavailableErrorRecognizer(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    boolean isError() {
        return webDriver.getTitle().equals("Error 503--Service Unavailable");
    }
}
