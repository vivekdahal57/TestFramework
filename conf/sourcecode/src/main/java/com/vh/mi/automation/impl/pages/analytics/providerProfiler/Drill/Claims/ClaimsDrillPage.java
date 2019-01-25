package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Claims;

import com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.Claims.IClaimsDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ClaimsDrillPage extends AbstractDrillPage implements IClaimsDrillPage {

    private WebElements webElements;

    public ClaimsDrillPage(WebDriver webDriver) {
        super(webDriver);
        webElements = new WebElements(webDriver);
    }

    @Override
    public boolean isFullyLoaded() {
        return  "(965) Medical Claim Detail".equals(webElements.pageTitle.getText());
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    private static class WebElements {
        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        WebElement pageTitle;
    }
}
