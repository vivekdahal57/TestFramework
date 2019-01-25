package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Provider;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.Provider.IProviderDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i82716 on 6/28/2017.
 */
public class ProviderDrillPage extends AbstractDrillPage implements IProviderDrillPage{

    private ProviderDrillPageDataGrid dataGrid;
    private WebElements webElements;

    public ProviderDrillPage(WebDriver webDriver){
        super(webDriver);
        webElements = new WebElements(webDriver);
        dataGrid = new ProviderDrillPageDataGrid(webDriver);
    }

    @Override
    public boolean isFullyLoaded() {
        return  "(V046a) Paid by Specialty and Provider".equals(webElements.pageTitle.getText());
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

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        WebElement pageTitle;

    }

}
