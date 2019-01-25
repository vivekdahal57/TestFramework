package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DrillDrug;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.claims.Drill.DimensionDrill.IDrugDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 12/12/17.
 */
public class DrugDrillPage extends AbstractDrillPage implements IDrugDrillPage {
    private DrugDrillPageDataGrid dataGrid;
    private WebElements webElements;


    public DrugDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new DrugDrillPageDataGrid(webDriver);
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

    public static class WebElements{
        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}
