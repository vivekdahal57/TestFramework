package com.vh.mi.automation.impl.reportManager.reportWizard;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.reportManager.reportWizard.IUserAccessTab;
import com.vh.mi.automation.impl.reportManager.ReportAdministrator;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserAccessTab extends AbstractWebComponent implements IUserAccessTab {
    private  WebElements webElements;
    public UserAccessTab(WebDriver driver){
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    @Override
    public ReportAdministrator saveAndPublish(){
        SeleniumUtils.click(webElements.saveAndPublishButton);
        ReportAdministrator reportAdministrator = new ReportAdministrator(getDriver());
        reportAdministrator.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return reportAdministrator;
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(name="btnFinalSave")
        WebElement saveAndPublishButton;
    }
}
