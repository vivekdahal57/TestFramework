package com.vh.mi.automation.impl.pages.analytics.claims.DrillDimensions.ProcedureDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.claims.Drill.DimensionDrill.IProcedureDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 12/8/17.
 */
public class ProcedureDrillPage extends AbstractDrillPage implements IProcedureDrillPage {
    private ProcedureDrillPageDataGrid dataGrid;
    private WebElements webElements;


    public ProcedureDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new  ProcedureDrillPageDataGrid(webDriver);
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
