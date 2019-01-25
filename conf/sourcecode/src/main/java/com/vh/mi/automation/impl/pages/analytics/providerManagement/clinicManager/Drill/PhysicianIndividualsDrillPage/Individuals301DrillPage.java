package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianIndividualsDrillPage;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill.IIndividual301DrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 11/27/17.
 */
public class Individuals301DrillPage extends AbstractDrillPage implements IIndividual301DrillPage{
    private Individuals301DrillPageDataGrid dataGrid;
    private WebElements webElements;


    public Individuals301DrillPage(WebDriver webDriver){
      super(webDriver);
      dataGrid = new Individuals301DrillPageDataGrid(webDriver);
      webElements = new WebElements(getDriver());
    }


    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.getText().equals("(301) Individuals");
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

        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;

    }
}
