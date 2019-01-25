package com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.drill.drillPopulationRiskDrivers;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.drill.IAllPopulation;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 1/29/18.
 */
public class AllPopulationDrillPage extends AbstractDrillPage implements IAllPopulation {
    private WebElements webElements;
    private IDataGrid dataGrid;

    public AllPopulationDrillPage(WebDriver webDriver){
        super(webDriver);
        webElements = new WebElements(getDriver());
        dataGrid = new AllPopulationDrillPageDataGrid(getDriver());
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
    public boolean isFullyLoaded() {
        return ("(PRD01) Population Risk Drivers".equalsIgnoreCase(webElements.pageTitle.getText()));
    }

    @Override
    public String getPageId() {
        return null;
    }

    public static class WebElements{
        private WebElements(WebDriver  webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}
