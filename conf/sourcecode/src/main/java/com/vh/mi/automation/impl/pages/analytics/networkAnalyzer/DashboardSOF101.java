package com.vh.mi.automation.impl.pages.analytics.networkAnalyzer;

import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.pages.analytics.networkAnalyzer.IDashboardSOF101;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by i80448 on 11/20/2014.
 */
public class DashboardSOF101 extends AbstractLandingPage implements IDashboardSOF101 {
    public static final String MODULE_ID = "400";
    private WebElements webElements;

    public DashboardSOF101(WebDriver driver) {
        super(driver, MODULE_ID);
        webElements = new WebElements(getDriver());
    }

    public boolean isGraphLoaded(){
        return webElements.allowedAmountValue.size() == 2;
    }

    private static class WebElements {
        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[text()='Allowed Amount($)']")
        private List<WebElement> allowedAmountValue;
    }

}
