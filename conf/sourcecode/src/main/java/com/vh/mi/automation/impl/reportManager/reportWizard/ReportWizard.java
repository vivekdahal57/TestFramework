package com.vh.mi.automation.impl.reportManager.reportWizard;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.reportManager.reportWizard.IReportWizard;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.ReportManagerLoadingPopup;
import com.vh.mi.automation.impl.reportManager.AbstractReportManagerPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by bibdahal on 1/28/2016.
 */
public class ReportWizard extends AbstractReportManagerPage implements IReportWizard {
    private final WebElements webElements;
    private final ReportManagerLoadingPopup reportManagerLoadingPopup;
    DataSelectionTab dataSelectionTab;
    ReportsTab reportsTab;
    GenerationTab generationTab;
    UserAccessTab userAccessTab;

    public ReportWizard(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        reportManagerLoadingPopup = new ReportManagerLoadingPopup(driver);
        dataSelectionTab = new DataSelectionTab(driver);
        reportsTab = new ReportsTab(driver);
        generationTab = new GenerationTab(driver);
        userAccessTab = new UserAccessTab(driver);
    }

    public void waitTillLoadingDisappears(){
        try{
            WaitUtils.waitUntilDisappear(getDriver(), webElements.loading, TimeOuts.TEN_SECONDS);
        }catch(ElementNotFoundException e){
            logger.info("No loading element found");
        }
    }

    @Override
    public boolean isFullyLoaded() {
        return getDisplayedTitle().equals("Report Wizard");
    }

    @Override
    public void goBack() {
        webElements.backButton.click();
    }

    @Override
    public List<String> getDisplayedTabRows() {
        List<String> displayedTabs = new ArrayList<>();
        for (WebElement webElement : webElements.tabRow.findElements(By.tagName("a"))) {
            displayedTabs.add(webElement.getText());
        }
        return displayedTabs;
    }

    @Override
    public DataSelectionTab getDataSelectionTab() {
        return dataSelectionTab;
    }

    @Override
    public GenerationTab getGenerationTab() {
        return generationTab;
    }

    @Override
    public ReportsTab getReportsTab() {
        return reportsTab;
    }

    @Override
    public UserAccessTab getUserAccessTab() {
        return userAccessTab;
    }


    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//a[text()='Back']")
        WebElement backButton;

        @FindBy (className = "tab-row")
        WebElement tabRow;

        @FindBy (id = "systemWorking")
        WebElement loading;

    }
}
