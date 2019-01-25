package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.ACCDxCGDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.claims.Drill.DimensionDrill.IACCDxCGDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 11/30/17.
 */
public class ACCDxCGDrillPage extends AbstractDrillPage implements IACCDxCGDrillPage {
    private ACCDxCGDrillPageDataGrid dataGrid;
    private WebElements webElements;

    public ACCDxCGDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new ACCDxCGDrillPageDataGrid(webDriver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText() ;
    }

    @Override
    public String getPageId() {
        return null;
    }

    private class WebElements{
        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver ,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}
