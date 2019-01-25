package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.FundTypeDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.claims.Drill.DimensionDrill.IFundTypeDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 12/6/17.
 */
public class FundTypeDrillPage extends AbstractDrillPage implements IFundTypeDrillPage {
    private WebElements webElements;
    private FundTypeDrillPageDataGrid dataGrid;

    public FundTypeDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new FundTypeDrillPageDataGrid(webDriver);
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

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

       private static class WebElements{
        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}
