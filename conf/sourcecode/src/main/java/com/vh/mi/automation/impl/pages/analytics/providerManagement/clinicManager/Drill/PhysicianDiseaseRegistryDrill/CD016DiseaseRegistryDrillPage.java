package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianDiseaseRegistryDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill.ICD016DiseaseRegistryDrillPage;

import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 11/20/17.
 */
public class CD016DiseaseRegistryDrillPage extends AbstractDrillPage implements ICD016DiseaseRegistryDrillPage {
    private CD016DiseaseRegistryDrillPageDataGrid dataGrid;
    private WebElements webElements;
    private static final Logger logger = LoggerFactory.getLogger(CD016DiseaseRegistryDrillPage.class);


    public CD016DiseaseRegistryDrillPage(WebDriver webDriver) {
        super(webDriver);
        dataGrid = new CD016DiseaseRegistryDrillPageDataGrid(webDriver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public boolean isDrillPageValid() {
        if (dataGrid.getData().size() > 0) {
            logger.info("size of the data in table is greater than zero");
            return true;
        } else {
            logger.info("size of the data in table is zero");
            return false;
        }
    }


    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.getText().equals("(CD016) Disease Registry");
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

        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;

    }

}
