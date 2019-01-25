package com.vh.mi.automation.impl.reportManager;

import com.vh.mi.automation.api.reportManager.IRMLoginPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by i81306 on 8/9/2017.
 */
public class ReportManager extends AbstractWebComponent {

    public ReportManager(WebDriver driver) {
        super(driver);
    }

    public IRMLoginPage open(String adminURL) {
        checkArgument(!isNullOrEmpty(adminURL));
        logger.info("Opening {}", adminURL);
        getDriver().get(adminURL);
        return PageFactory.initElements(getDriver(), RMLoginPage.class);
    }

    @Override
    public boolean isFullyLoaded() {
        return true;
    }

    public void quit() {
        getDriver().quit();
    }
}
