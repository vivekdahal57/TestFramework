package com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.drill.IComorbidityCD108DrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ComorbidityCD108DrillPage extends AbstractDrillPage implements IComorbidityCD108DrillPage {

    private WebElements webElements;
    private ComorbidityCD108DrillPageDataGrid dataGrid;

    public ComorbidityCD108DrillPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        dataGrid =  new ComorbidityCD108DrillPageDataGrid(driver);
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public boolean isFullyLoaded() {
        return "(CD108) Comorbidity Analysis by Diseases".equalsIgnoreCase(webElements.pageTitle.getText());
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
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        WebElement pageTitle;
    }
}
