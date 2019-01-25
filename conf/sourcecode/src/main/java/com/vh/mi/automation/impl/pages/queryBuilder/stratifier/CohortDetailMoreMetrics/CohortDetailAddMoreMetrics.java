package com.vh.mi.automation.impl.pages.queryBuilder.stratifier.CohortDetailMoreMetrics;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.CohortDetailAddMoreMetrics.CohortDetailAddMoreMetricsDataGridColumn;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.CohortDetailAddMoreMetrics.ICohortDetailAddMoreMetrics;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by i10314 on 7/13/2017.
 */
public class CohortDetailAddMoreMetrics extends QueryBuilderToolBar implements ICohortDetailAddMoreMetrics {
    private final IDataGrid dataGrid;
    WebElements webElements;

    public CohortDetailAddMoreMetrics(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        dataGrid = new CohortDetailAddMoreMetricsDataGrid(driver);
    }
    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed();
    }

    public void addMetricesWithTheseIDs(String IDlist){
        List<String> idList = Arrays.asList(IDlist.split(","));
        for(String IDs: idList){
            dataGrid.doFilter(CohortDetailAddMoreMetricsDataGridColumn.ISSUE_NAME,IDs);
            WaitUtils.waitUntilEnabled(getDriver(),webElements.chkBoxApplyMetrics);
            SeleniumUtils.click(webElements.chkBoxApplyMetrics);
            dataGrid.doClearRecentFilteredValue();
        }
        SeleniumUtils.click(webElements.btnApplyMetrics);
        SeleniumUtils.switchToNewWindow(getDriver());
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"d2Form:pageTitle\"]")
        private WebElement pageTitle;

        @FindBy(xpath = "//*[@id=\"d2Form:metricsGrid:tb\"]/tr/td[1]/input[1]")
        private WebElement chkBoxApplyMetrics;

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar_1\"]/tbody/tr/td[2]/table/tbody/tr[2]/td")
        private WebElement btnApplyMetrics;
    }

}
