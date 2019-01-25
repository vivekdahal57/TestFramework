package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i82298 on 11/16/2015.
 */
public class NurseDesktop301ND extends IndividualDashboardMemberSummary {

    WebElements webElements;

    public NurseDesktop301ND(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.isDisplayed() && webElements.memberIdInput.isDisplayed();
    }

    public NurseDesktop301ND enterMemberId(String memberId) {
        webElements.memberIdInput.sendKeys(memberId);
        return this;
    }

    private Actions showDrillMenu() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(webElements.drillMenuButton);
        return actions;
    }

    public boolean isTabPresent() {
        List<WebElement> listOfTabs = webElements.tabs;

        return listOfTabs.size() > 0 ? true : false;

    }

    public NurseDesktop301ND submit() {
        showDrillMenu().moveToElement(webElements.submitMemberIdLink).click().build().perform();
        doWaitTillTabsAreVisible(TimeOuts.THIRTY_SECONDS);
        return this;
    }

    private void doWaitTillTabsAreVisible(Integer timeOuts) {
        WaitUtils.waitUntil(getDriver(), timeOuts, new Function<WebDriver,Boolean>() {
            @Override
            public Boolean apply(WebDriver tab) {
                return webElements.tabs.size() > 0;
            }
        });
    }

    public List<String> getAvailableTabs() {

        List<String> listOfTabs = new ArrayList<>();
        for (WebElement tabElement : webElements.tabs) {
            String tabText = tabElement.findElement(By.xpath("./table/tbody/tr/td")).getText();
            listOfTabs.add(tabText);
        }
        return listOfTabs;
    }

    private static class WebElements {

        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//div[@id='pageTitle']/span")
        WebElement pageTitle;

        @FindBy(xpath = "//input[@id='d2Form:memId']")
        WebElement memberIdInput;

        @FindBy(xpath = "//table[@id='d2Form:drillMenu']//span[@class='d2-drillMenu-position']/span")
        WebElement drillMenuButton;

        @FindBy(xpath = "//td/a[@id='d2Form:tab:0:submitMemId']")
        WebElement submitMemberIdLink;

        @FindBy(xpath = "//td[@class='rich-tabhdr-side-cell']")
        List<WebElement> tabs;
    }


}
