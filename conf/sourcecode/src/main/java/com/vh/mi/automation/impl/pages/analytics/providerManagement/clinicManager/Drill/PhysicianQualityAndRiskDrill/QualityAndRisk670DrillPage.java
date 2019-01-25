package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianQualityAndRiskDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill.I670QualityAndRiskDrillPage;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 11/21/17.
 */
public class QualityAndRisk670DrillPage extends AbstractDrillPage implements I670QualityAndRiskDrillPage {
    private QualityAndRisk670DrillPageDataGrid dataGrid;
    private WebElements webElements;

    public QualityAndRisk670DrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new QualityAndRisk670DrillPageDataGrid(getDriver());
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
        return webElements.pageTitle.getText().equals("(670) Quality & Risk");
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

    @Override
    public IDrillPage drillDownToPage(String page) {
        IDrillPage drillPage = dataGrid.getRows().get(0).doDrillByOnSameWindow(page);
        drillPage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return drillPage;
    }



    private static class WebElements{

        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id = "pageTitle")
       private WebElement pageTitle;
    }


}
