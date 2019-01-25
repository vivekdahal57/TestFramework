package com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.Drill;

import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.Drill.IHP120AdmissionClaimDetailDrillPage;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.Drill.IHP120AdmissionDrillPage;
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
 * Created by i10359 on 2/21/18.
 */
public class HP120AdmissionClaimDetailsDrillPage extends AbstractDrillPage implements IHP120AdmissionClaimDetailDrillPage {
    private WebElements webElements;
    private LoadingPopup loadingPopup;
    private static final String JOBNAME = "CSV_JOBNAME_" + Random.getRandomStringOfLength(3);



    public HP120AdmissionClaimDetailsDrillPage(WebDriver driver){
        super(driver);
        webElements = new WebElements(driver);
        loadingPopup = new LoadingPopup(getDriver());

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

    private static class WebElements{
        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;


        @FindBy(xpath="//*[@id=\"d2Form:csvJobProgressPanel_body\"]//input[@value='OK']")
        private WebElement okBtn;

        @FindBy(id="d2Form:jobName")
        private WebElement inputBox;

        @FindBy(id="d2Form:sendToCsv")
        private WebElement sendBtn;
    }
}
