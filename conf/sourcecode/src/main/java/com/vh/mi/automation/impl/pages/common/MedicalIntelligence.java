package com.vh.mi.automation.impl.pages.common;

import com.vh.mi.automation.api.dto.Application;
import com.vh.mi.automation.api.pages.common.ILoginPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author i80448
 */
public class MedicalIntelligence extends AbstractWebComponent {

    public MedicalIntelligence(WebDriver driver) {
        super(driver);
    }

    public ILoginPage open(Application application) {
        String url = application.getUrl();
        checkArgument(!isNullOrEmpty(url));
        logger.info("Opening {}", url);
        getDriver().get(url);
        return PageFactory.initElements(getDriver(), LoginPage.class);
    }

    @Override
    public boolean isFullyLoaded() {
        return true;
    }

    public void quit() {
        getDriver().quit();
    }
}
