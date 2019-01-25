package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.Specialty;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.claims.Drill.DimensionDrill.ISpecialtyDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 12/8/17.
 */
public class SpecialtyDrillPage extends AbstractDrillPage implements ISpecialtyDrillPage {
    private WebElements webElements;
    private SpecialtyDrillPageDataGrid dataGrid;

    public SpecialtyDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new SpecialtyDrillPageDataGrid(webDriver);
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


    private class WebElements{
        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver ,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}
