package com.vh.mi.automation.impl.pages.outReach;


import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.outReach.IMemberListSummary;
import com.vh.mi.automation.api.pages.outReach.MemberListSummaryDataGridColumn;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by i82716 on 5/16/2017.
 */
public class MemberListSummary extends AbstractLandingPage implements IMemberListSummary {
    private static final String MODULE_ID = "61";
    private static final String LIST_NAME_HOLDER = "${List Name}";
    private final WebElements webElements;

    MemberListSummaryDataGrid dataGrid;
    LoadingPopup loadingPopup;


    public MemberListSummary(WebDriver driver) {
        super(driver, MODULE_ID);
        webElements = new WebElements(driver);
        dataGrid = new MemberListSummaryDataGrid(driver);
        loadingPopup = new LoadingPopup(driver);
    }

    public boolean isMemberListPresent(String memberListName) {
        dataGrid.doFilter(MemberListSummaryDataGridColumn.NAME, memberListName);
        waitForAjaxCallToFinish();
        if (dataGrid.getData().rowMap().size() == 1) {
            return true;
        }
        return false;
    }

    public Indv301 goToIndv301PageFor(String listName) {
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]//td[.=\"${List Name} (Dynamic)\"]//following::td[1]/a[1]";
        WebElement hoverList = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(LIST_NAME_HOLDER, listName));
        WaitUtils.waitUntilEnabled(getDriver(), hoverList);
        SeleniumUtils.hover(getDriver(), hoverList);

        WaitUtils.waitUntilDisplayed(getDriver(),webElements.viewListLink, TimeOuts.TWO_SECOND);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.viewListLink);
        SeleniumUtils.click(webElements.viewListLink);
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(getDriver());
        SeleniumUtils.switchToNewWindow(getDriver());
        Indv301 indv301 = new Indv301(getDriver());

        indv301.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return indv301;
    }

    private void waitForAjaxCallToFinish() {
        WaitUtils.waitForMilliSeconds(100);
        loadingPopup.waitTillDisappears();
        checkState(!loadingPopup.isDisplayed(), "The popup should have disappeared");
    }


    private static class WebElements {
        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a[.=\"View List\"]")
        private WebElement viewListLink;
    }
}
