package com.vh.mi.automation.impl.pages.analytics.providerManagement.physicianManager;

import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilerDashboard extends AbstractWebComponent {

    private WebElements webElements;
    private static final String NTH_CHECKBOX_PLACEHOLDER = "${nthCheckbox}";

    public ProfilerDashboard(WebDriver webDriver) {
        super(webDriver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }

    private void selectNthCheckbox(String nthCheckbox){
        String xpath = "(//*[@id='d2Form:simpleGrid:tb']//td/input[@type='checkbox'])[${nthCheckbox}]";
        WebElement gruopIncludeCheckbox = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(NTH_CHECKBOX_PLACEHOLDER, nthCheckbox));
        SeleniumUtils.click(gruopIncludeCheckbox);
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        WebElement pageTitle;

    }
}
