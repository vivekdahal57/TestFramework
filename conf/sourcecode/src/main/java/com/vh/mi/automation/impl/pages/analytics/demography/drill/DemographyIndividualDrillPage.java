package com.vh.mi.automation.impl.pages.analytics.demography.drill;

import com.vh.mi.automation.api.pages.analytics.demography.IDemographyD05;
import com.vh.mi.automation.api.pages.analytics.demography.drill.IDemographyIndividualDrillPage;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.MemberTab.MedicalIntelligenceMemberSummary;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 3/2/18.
 */
public class DemographyIndividualDrillPage extends AbstractDrillPage implements IDemographyIndividualDrillPage{
    private WebElements webElements;

    public DemographyIndividualDrillPage(WebDriver driver){
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    public static class WebElements{
        private WebElements(WebDriver driver){
            PageFactory.initElements(driver,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }
}
