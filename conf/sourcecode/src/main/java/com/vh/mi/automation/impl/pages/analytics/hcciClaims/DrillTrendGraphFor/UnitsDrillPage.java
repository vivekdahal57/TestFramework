package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillTrendGraphFor;

import com.vh.mi.automation.api.pages.analytics.claims.Drill.IUnitsDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 12/28/17.
 */
public class UnitsDrillPage extends AbstractDrillPage implements IUnitsDrillPage {
    private WebElements webElements;

    public UnitsDrillPage(WebDriver webDriver){
        super(webDriver);
        webElements = new WebElements(getDriver());
    }


    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }


    private static class WebElements{
        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}
