package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillTrendBy.Quarter;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.analytics.claims.Drill.IQuarterDrillPage;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 12/27/17.
 */
public class QuarterDrillPage extends AbstractDrillPage implements IQuarterDrillPage {
    private QuarterDrillPageDataGrid dataGrid;
    private WebElements webElements;


    public QuarterDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new QuarterDrillPageDataGrid(webDriver);
        webElements = new WebElements(getDriver());

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

    @Override
    public IDrillPage drillDownToPage(String page) {
        IDrillPage drillPage = dataGrid.getRows().get(0).doDrillByOnSameWindow(page);
        drillPage.doWaitTillFullyLoaded(TimeOuts.THIRTY_SECONDS);
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
