package com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.General;

import com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.General.IClaimsLevel;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.QueryBuilderToolBar;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/**
 * Created by i81306 on 5/3/2017.
 */
public class ClaimsLevel extends QueryBuilderToolBar implements IClaimsLevel{


    WebElements webElements;

    public ClaimsLevel(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
    }

    public void selectClaimType(String claimType){
        if("All".equalsIgnoreCase(claimType)){
            SeleniumUtils.click(webElements.allClaimsCheckBox);
        }else if("Medical".equalsIgnoreCase(claimType)){
            SeleniumUtils.click(webElements.medicalClaimsCheckBox);
        }else if("Rx".equalsIgnoreCase(claimType)){
            SeleniumUtils.click(webElements.rxClaimsCheckBox);
        }
    }

    public void selectMemberListFromMVCA(String memberListName){
        SeleniumUtils.selectByVisibleText(webElements.memberListSelectElement, memberListName);
    }

    @Override
    public String getPageLink() {
        return "General";
    }


    private static class WebElements {
        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath="(//span[text()='Claim Type']//following::label[text()='All Claims']//preceding::input[@type='checkbox'])[1]")
        private WebElement allClaimsCheckBox;

        @FindBy(xpath="(//span[text()='Claim Type']//following::label[text()='Medical']//preceding::input[@type='checkbox'])[1]")
        private WebElement medicalClaimsCheckBox;

        @FindBy(xpath="(//span[text()='Claim Type']//following::label[text()='Rx']//preceding::input[@type='checkbox'])[1]")
        private WebElement rxClaimsCheckBox;

        @FindBy(xpath="//*[@id=\"d2Form:GroupGrid:tb\"]//label[text()='Member list from MVCA']//following::select[1]")
        private WebElement memberListSelectElement;


    }
}
