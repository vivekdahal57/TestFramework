package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardHealthAnalysisSummary;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10314 on 8/2/2017.
 */
public class IndividualDahsboardHealthAnalysisSummary extends AbstractIndividualDashboard implements IIndividualDashboardHealthAnalysisSummary{

    private LoadingPopup loadingPopup;
    public WebElements webElements;
    private Indv301 indv301;
    private static final String FIELD_NAME_PLACE_HOLDER = "${FieldName}";

    public IndividualDahsboardHealthAnalysisSummary(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
    }

    @Override
    public boolean isFullyLoaded(){
        return webElements.pageTitle.isDisplayed();
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (355) Health Analysis Summary";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }

    public void updateData(String fieldName, String updateText){
        String xpath = "//*[@id=\"d2Form:grid:tb\"]//td[.=\"${FieldName}\"]//following::input[1]";
        WebElement inputField = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(FIELD_NAME_PLACE_HOLDER, fieldName));
        WaitUtils.waitUntilDisplayed(getDriver(),inputField, TimeOuts.TWO_SECOND);
        inputField.clear();
        inputField.sendKeys(updateText);
    }

    public String getUpdatedDataSavedSuccessfullyText(){
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnSave);
        SeleniumUtils.click(webElements.btnSave);
        loadingPopup.waitTillDisappears();
        WaitUtils.waitUntilDisplayed(getDriver(),webElements.recordsModifiedText, TimeOuts.FIVE_SECONDS);
        return webElements.recordsModifiedText.getText();
    }

    public boolean updatedDataSeenInHASPage(String fieldName, String updateText){
        String xpath = "//*[@id=\"d2Form:grid:tb\"]//td[.=\"${FieldName}\"]//following::input[1]";
        WebElement updatedValue = SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(FIELD_NAME_PLACE_HOLDER,fieldName));
        WaitUtils.waitUntilDisplayed(getDriver(),updatedValue, TimeOuts.TWO_SECOND);
        String updatedVale = updatedValue.getAttribute("value");

        if (updatedVale.equalsIgnoreCase(updateText)){
            return true;
        }else {
            return false;
        }
    }

    public String checkLastSavedByUser(){
        String lastSavedBy = webElements.lastSavedBy.getText();
        String newLastSavedBy = lastSavedBy.split(",")[0];
        newLastSavedBy = newLastSavedBy.replaceAll("[_]","").replaceAll("\\s","").toLowerCase();
        return newLastSavedBy;
    }

    public void closeHASPageAndGoBackToIndividualPage(){
        SeleniumUtils.closeChildWindowAndGoBackToMainWindow(getDriver(),true);
        loadingPopup.waitTillDisappears();
    }

    public static class WebElements{
        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"pageTitle\"]/span")
        private WebElement pageTitle;

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]/tbody/tr//a[.=\"Save\"]")
        private WebElement btnSave;

        @FindBy(xpath = "//*[@id=\"ani_message\"]")
        private WebElement recordsModifiedText;

        @FindBy(xpath = "//*[@id=\"d2Form:grid:tb\"]/tr[1]/td[1]//td[.=\"Last Saved by User\"]//following::td[1]")
        private WebElement lastSavedBy;
    }
}
