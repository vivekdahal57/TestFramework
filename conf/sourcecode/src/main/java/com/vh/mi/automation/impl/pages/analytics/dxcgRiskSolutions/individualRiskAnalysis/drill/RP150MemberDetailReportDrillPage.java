package com.vh.mi.automation.impl.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.drill;

import com.vh.mi.automation.api.pages.analytics.dxcgRiskSolutions.individualRiskAnalysis.drill.IRP150MemberDetailReportDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RP150MemberDetailReportDrillPage extends AbstractDrillPage implements IRP150MemberDetailReportDrillPage {
    private WebElements webElements;

    public RP150MemberDetailReportDrillPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        System.out.println(webElements.pageTitle.getText());
        return "Individual Dashboard: (RP150) Member Detail Report".equalsIgnoreCase(webElements.pageTitle.getText());
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
        WebElement pageTitle;
    }
}
