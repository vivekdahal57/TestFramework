package com.vh.mi.automation.impl.reportManager.reportWizard;

import com.vh.mi.automation.api.reportManager.reportWizard.IReportsTab;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReportsTab extends AbstractWebComponent implements IReportsTab {

    private final WebElements webElements;

    public ReportsTab(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    @Override
    public void selectAllReportChapter(){
        SeleniumUtils.click(webElements.checkAllReportsButton);
    }

    @Override
    public void reportChapterSaveAndContinue() {
        SeleniumUtils.click(webElements.saveAndContinueButton);
    }

    @Override
    public void selectReportSet(IReportsTab.ReportSet reportSet) {
        SeleniumUtils.selectByValue(webElements.reportSetDropdown,reportSet.getValue());
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//form[@name='ChapterSelectionForm']//img[@id='imgCheck']")
        WebElement checkAllReportsButton;

        @FindBy(name = "btnChapContinue")
        WebElement saveAndContinueButton;

        @FindBy (xpath = "//td[@id='rptPkgSelection']//select")
        WebElement reportSetDropdown;
    }
}
