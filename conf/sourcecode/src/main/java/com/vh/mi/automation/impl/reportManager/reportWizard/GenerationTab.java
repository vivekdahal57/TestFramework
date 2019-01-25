package com.vh.mi.automation.impl.reportManager.reportWizard;

import com.vh.mi.automation.api.reportManager.reportWizard.IGenerationTab;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GenerationTab extends AbstractWebComponent implements IGenerationTab{

    private  WebElements webElements;

    public GenerationTab(WebDriver driver){
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    @Override
    public void selectReportFormat(boolean isPDFChecked, boolean isRTFChecked){
        if(isPDFChecked){
            if(!webElements.rtfFormatCheckbox.isSelected()){
                webElements.rtfFormatCheckbox.click();
            }
        }
        if(isRTFChecked){
            if(!webElements.pdfFormatCheckbox.isSelected()){
                webElements.pdfFormatCheckbox.click();
            }
        }
    }

    @Override
    public void reportGenerationSaveAndContinue(){
        SeleniumUtils.click(webElements.saveAndContinueButton);
    }

    @Override
    public void selectTimePeriod(IGenerationTab.TimePeriod timePeriod){
        SeleniumUtils.selectByValue(webElements.timePeriodDropdown, timePeriod.getValue());
    }

    @Override
    public void selectMonthlyCost(IGenerationTab.MonthlyCost monthlyCost){
        SeleniumUtils.selectByValue(webElements.monthlyCostDropdown, monthlyCost.getValue());
    }

    @Override
    public void selectReportingBy(IGenerationTab.ReportingBy reportingBy){
        SeleniumUtils.selectByValue(webElements.reportingPeriodDropdown, reportingBy.getValue());
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy (xpath = "//form[@name='ReportGeneration']//input[@name='reportFormatRtf']")
        WebElement rtfFormatCheckbox;

        @FindBy (xpath = "//form[@name='ReportGeneration']//input[@name='reportFormatPdf']")
        WebElement pdfFormatCheckbox;

        @FindBy (name = "btnRepGenContinue")
        WebElement saveAndContinueButton;

        @FindBy (xpath = "//td[@id='time_Period']//select")
        WebElement timePeriodDropdown;

        @FindBy(xpath = "//td[@id='reporting_By']//select")
        WebElement reportingPeriodDropdown;

        @FindBy (xpath = "//div[@id='MonthlyCost']//select")
        WebElement monthlyCostDropdown;
    }
}
