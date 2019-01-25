package com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk.drill;

import com.vh.mi.automation.api.pages.analytics.qualityMeasures.qualityAndRisk.drill.IQRMDetail690;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class QRMDetail690 extends AbstractDrillPage implements IQRMDetail690 {
    private WebElements webElements;

    public QRMDetail690(WebDriver webDriver){
        super(webDriver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded(){
        return "(690) QRM Detail".equalsIgnoreCase(webElements.pageTitle.getText());
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    @Override
    public boolean isDrillPageValid() {
        logger.info("Drill Page is in valid state if and only if  all the tables of 'QRM Detail' are present in the page");
        logger.info("Checking if all tables of 'QRM Detail' exists or not");
        List<String> tableIds = getQRMDetailTableXPaths();
        for(String xpath: tableIds) {
            if (SeleniumUtils.findElementsByXPath(getDriver(),xpath).size() != 1 ) {
                logger.info("Table with Xpath '" + xpath + "' Does Not Exists In The Page.Thus Drill page is not valid");
                return false;
            }
        }
        return true;
    }

    private List<String> getQRMDetailTableXPaths() {
        List<String> tableXPaths = new ArrayList<>();
        tableXPaths.add("//div[@id='middleToolbarDiv']//div[text()='Show Gap/Risk Trending']");
        tableXPaths.add("//div[@id='middleToolBarNdataGrid']//table[@id='d2Form:simpleGrid']");
        return tableXPaths;
    }


    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
         WebElement pageTitle;
    }

}
