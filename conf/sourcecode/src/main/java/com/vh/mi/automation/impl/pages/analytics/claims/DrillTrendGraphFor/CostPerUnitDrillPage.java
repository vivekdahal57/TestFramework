package com.vh.mi.automation.impl.pages.analytics.claims.DrillTrendGraphFor;

import com.vh.mi.automation.api.pages.analytics.claims.Drill.ICostPerUnitDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 12/28/17.
 */
public class CostPerUnitDrillPage extends AbstractDrillPage implements ICostPerUnitDrillPage{
    private WebElements webElements;

    public CostPerUnitDrillPage(WebDriver webDriver){
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
