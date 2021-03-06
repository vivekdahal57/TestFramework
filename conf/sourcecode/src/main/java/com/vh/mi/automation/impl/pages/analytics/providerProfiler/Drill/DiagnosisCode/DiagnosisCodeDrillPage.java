package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.DiagnosisCode;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.DiagnosisCode.IDiagnosisCodeDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DiagnosisCodeDrillPage extends AbstractDrillPage implements IDiagnosisCodeDrillPage{
    private WebElements webElements;
    private DiagnosisCodeDrillPageDataGrid dataGrid;

    public DiagnosisCodeDrillPage(WebDriver webDriver) {
        super(webDriver);
        webElements = new WebElements(webDriver);
        dataGrid = new DiagnosisCodeDrillPageDataGrid(getDriver());
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public boolean isFullyLoaded() {
        return  "(V046d) Paid by Specialty and Diagnosis".equals(webElements.pageTitle.getText());
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
