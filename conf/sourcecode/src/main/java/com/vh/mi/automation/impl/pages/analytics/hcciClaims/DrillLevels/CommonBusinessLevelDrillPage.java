package com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillLevels;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.analytics.claims.Drill.ICommonBusinessLevelDrillPage;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 11/30/17.
 */
public class CommonBusinessLevelDrillPage extends AbstractDrillPage implements ICommonBusinessLevelDrillPage {
    private CommonBusinessLevelDrillPageDataGrid dataGrid;
    private WebElements webElements;

    public CommonBusinessLevelDrillPage(WebDriver webdriver) {
        super(webdriver);
        dataGrid = new CommonBusinessLevelDrillPageDataGrid(webdriver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public IDrillPage drillDownToPage(String page) {
        IDrillPage drillPage = dataGrid.getRows().get(0).doDrillByOnSameWindow(page);
        drillPage.doWaitTillFullyLoaded(TimeOuts.THIRTY_SECONDS);
        return drillPage;
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
