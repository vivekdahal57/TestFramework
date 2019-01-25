package com.vh.mi.automation.impl.pages.analytics.individuals.drill.MemberTab;

import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 1/4/18.
 */
public class MedicalIntelligenceMemberSummary extends AbstractIndividualDashboard {
    private WebElements webElement;

    public MedicalIntelligenceMemberSummary(WebDriver driver){
        super(driver);
        webElement = new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElement.pageTitle.isDisplayed();
    }

    @Override
    public String getPageTitle() {
   String pageTitle = webElement.pageTitle.getText() ;
    return pageTitle;

    }

    @Override
    public String getPageId() {
        return null;
    }

    public static class WebElements{
        public WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@class = \"reportheading\"]")
         WebElement  pageTitle;
    }
}
