package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Procedure;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.Procedure.IProcedureDrillPage;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProcedureDrillPage extends AbstractDrillPage implements IProcedureDrillPage {
    private ProcedureDrillPageDataGrid dataGrid;
    private WebElements webElements;

    public ProcedureDrillPage(WebDriver webDriver) {
        super(webDriver);
        webElements = new WebElements(webDriver);
        dataGrid = new ProcedureDrillPageDataGrid(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return  "(V046b) Paid by Specialty and Procedure".equals(webElements.pageTitle.getText());
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

    private static class WebElements {
        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        WebElement pageTitle;
    }
}
