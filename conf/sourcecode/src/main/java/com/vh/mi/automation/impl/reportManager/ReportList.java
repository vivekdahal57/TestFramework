package com.vh.mi.automation.impl.reportManager;

import com.vh.mi.automation.api.reportManager.IReportList;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 5/10/2017.
 */
public class ReportList extends ReportAdministrator.AbstractReportManagerPage implements IReportList{
    private final WebElements webElements;


    public ReportList(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public void goBack() {
        SeleniumUtils.click(webElements.backButton);
    }

    @Override
    public void goToReportWizard() {
        SeleniumUtils.click(webElements.reportWizardButton);
    }

    @Override
    public boolean isFullyLoaded() {
        return getDisplayedTitle().equals("Report List");
    }



    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//a[text()='Back']")
        WebElement backButton;

        @FindBy(xpath = "//a[text()='Report Wizard']")
        WebElement reportWizardButton;

    }
}
