package com.vh.mi.automation.impl.pages.analytics.hcciClaims;


import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciIClaims01DrillPage;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 10/27/17.
 */
public class HcciClaims01DrillPage extends AbstractDrillPage implements HcciIClaims01DrillPage {
    private static final String JOBNAME = "CSV_JOBNAME_" + Random.getRandomStringOfLength(3);
    private HcciClaims01DrillPageDataGrid dataGrid;
    private WebElements webElements;
    private LoadingPopup loadingPopup;

    public HcciClaims01DrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new HcciClaims01DrillPageDataGrid(getDriver());
        webElements = new WebElements(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
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
    public boolean isFullyLoaded(){
        return webElements.pageTitle.isDisplayed();
    }

    @Override
    public void generateCSV() {
        WaitUtils.waitForSeconds(TimeOuts.ONE_SECOND);

        SeleniumUtils.sendKeysToInput(JOBNAME, webElements.inputBox);
        SeleniumUtils.click(webElements.sendBtn, getDriver());
        loadingPopup.waitTillDisappears();
        SeleniumUtils.click(webElements.okBtn, getDriver());
        SeleniumUtils.closeChildWindowAndGoBackToMainWindow(getDriver(),true);
    }

    @Override
    public String getJobName() {
        return JOBNAME;
    }

    @Override
    public void filterPaidAmounts() {
        dataGrid.doFilter(HcciClaims01DrillPageDataGridColumns.PAID_AMOUNT, ">10000");
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;

        @FindBy(id="d2Form:sendToCsv")
        private WebElement sendBtn;

        @FindBy(id="d2Form:jobName")
        private WebElement inputBox;


        @FindBy(xpath="//*[@id=\"d2Form:csvJobProgressPanel_body\"]//input[@value='OK']")
        private WebElement okBtn;


    }
}
