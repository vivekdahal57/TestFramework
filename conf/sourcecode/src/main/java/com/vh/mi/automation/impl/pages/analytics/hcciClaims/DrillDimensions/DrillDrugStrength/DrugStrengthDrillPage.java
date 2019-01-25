package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.DrillDrugStrength;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 12/12/17.
 */
public class DrugStrengthDrillPage extends AbstractDrillPage implements IDrillPage {
    private DrugStrengthDrillPageDataGrid dataGrid;
    private WebElements webElements;

    public DrugStrengthDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new DrugStrengthDrillPageDataGrid(webDriver);
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

    public static class WebElements{
        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}
