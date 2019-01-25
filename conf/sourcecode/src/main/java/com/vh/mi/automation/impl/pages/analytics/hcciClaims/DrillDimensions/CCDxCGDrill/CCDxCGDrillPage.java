package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.CCDxCGDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.claims.Drill.DimensionDrill.ICCDxCGDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 12/1/17.
 */
public class CCDxCGDrillPage extends AbstractDrillPage implements ICCDxCGDrillPage {
    private CCDxCGDrillPageDataGrid dataGrid;
    private WebElements webElements;

    public CCDxCGDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new CCDxCGDrillPageDataGrid(webDriver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }



    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    private class WebElements{
        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        private  WebElement pageTitle;
    }
}
