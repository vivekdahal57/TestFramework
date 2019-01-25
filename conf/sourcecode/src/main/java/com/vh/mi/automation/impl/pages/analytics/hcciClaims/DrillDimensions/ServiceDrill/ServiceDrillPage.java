package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillDimensions.ServiceDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.claims.Drill.DimensionDrill.IServiceDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 12/8/17.
 */
public class ServiceDrillPage extends AbstractDrillPage implements IServiceDrillPage {
    private WebElements webElements;
    private ServiceDrillPageDataGrid dataGrid;

    public ServiceDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new ServiceDrillPageDataGrid(webDriver);
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
