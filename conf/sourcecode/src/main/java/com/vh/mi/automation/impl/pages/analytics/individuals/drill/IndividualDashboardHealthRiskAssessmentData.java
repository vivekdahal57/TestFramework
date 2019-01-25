package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardHealthRiskAssessmentData;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by i10314 on 8/24/2017.
 */
public class IndividualDashboardHealthRiskAssessmentData extends AbstractIndividualDashboard implements IIndividualDashboardHealthRiskAssessmentData {
    private WebElements webElements;
    private LoadingPopup loadingPopup;
    private static final String FIELD_NAME_PLACE_HOLDER = "${Field Name}";

    public IndividualDashboardHealthRiskAssessmentData(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
        loadingPopup = new LoadingPopup(getDriver());
    }

    @Override
    public String getPageTitle() {
        return "Individual Dashboard: (HRA1) Health Risk Assessment Data";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }

    public void addOrEditAndSaveTheseFields(List<Pair<String, String>> fields) {
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnAddEdit);
        SeleniumUtils.click(webElements.btnAddEdit);
        loadingPopup.waitTillDisappears();

        String xpath = "//td[@class=\"rich-table-cell d2-twocol\"][.=\"${Field Name}\"]//following::input[1]";
        for (Pair<String, String> field : fields) {
            WebElement fieldInput = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(FIELD_NAME_PLACE_HOLDER, field.getKey()));
            WaitUtils.waitUntilEnabled(getDriver(),fieldInput);
            fieldInput.clear();
            SeleniumUtils.sendKeysToInput(field.getValue(), fieldInput);
        }

        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnSave);
        SeleniumUtils.click(webElements.btnSave);
        loadingPopup.waitTillDisappears();
    }

    public boolean savedDataSeenInHRAPage(List<Pair<String, String>> fields){
        String xpath = "//td[@class=\"rich-table-cell d2-twocol\"][.=\"${Field Name}\"]//following::span[1]";
        for(Pair<String, String> field : fields){
            String fieldValue = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(FIELD_NAME_PLACE_HOLDER,field.getKey())).getText();
            if (!fieldValue.equals(field.getValue())){
                return false;
            }
        }
        return true;
    }

    public String checkLastSavedByUser(){
        String lastSavedBy = webElements.lastSavedBy.getText();
        String newLastSavedBy = lastSavedBy.split(",")[0];
        newLastSavedBy = newLastSavedBy.replaceAll("[_]","").replaceAll("\\s","").toLowerCase();
        return newLastSavedBy;
    }

    public static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]//a[.=\"Add-Edit\"]")
        private WebElement btnAddEdit;

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]//a[.=\"Save\"]")
        private WebElement btnSave;

        @FindBy(xpath = "//*[@id=\"d2Form:lastSavedBy\"]/tbody/tr/td[2]/span")
        private WebElement lastSavedBy;

    }
}
