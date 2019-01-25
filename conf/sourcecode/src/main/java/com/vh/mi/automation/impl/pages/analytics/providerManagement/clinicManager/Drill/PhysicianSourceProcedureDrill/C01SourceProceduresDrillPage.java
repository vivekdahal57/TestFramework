package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianSourceProcedureDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill.IC01SourceProceduresDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 11/22/17.
 */
public class C01SourceProceduresDrillPage extends AbstractDrillPage implements IC01SourceProceduresDrillPage {
    private C01SourceProceduresDrillPageDataGrid dataGrid;
    private WebElements webElements;

    public C01SourceProceduresDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new C01SourceProceduresDrillPageDataGrid(getDriver());
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
        return webElements.pageTitle.getText().equals("(C01) Claims");
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
        return null;
    }

    private static class WebElements{

        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;

    }
}
