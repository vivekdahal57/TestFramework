package com.vh.mi.automation.impl.pages.queryBuilder;//package com.vh.mi.automation.impl.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.IIndividual301;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardOutreachHistory;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by i20345 on 12/26/2016.
 */
public class Individual301 extends AbstractWebComponent implements IIndividual301{
    Actions action;
    private WebElements webElements;
    SeleniumUtils seleniumUtils;
    private final LoadingPopup loadingPopup;

    public Individual301(WebDriver driver) {
        super(driver);
        webElements=new WebElements(getDriver());
        loadingPopup = new LoadingPopup(driver);
        action=new Actions(getDriver());
        seleniumUtils=new SeleniumUtils();
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.individualColumn.isDisplayed();
    }

    public String getIndividualCount() {
        return webElements.individualCount.get(webElements.individualCount.size() -1).getText();
    }

    public void closeCurrentWindowAndSwitchToMainWindow(){
        seleniumUtils.closeChildWindowAndGoBackToMainWindow(getDriver(),true);
    }

    public IndividualDashboardOutreachHistory goToOutreachHistoryPage(){
        WaitUtils.waitUntilEnabled(getDriver(),webElements.hoverBtn);
        SeleniumUtils.hover(getDriver(),webElements.hoverBtn);

        WaitUtils.waitUntilEnabled(getDriver(),webElements.goToOutreachHistoryLink);
        SeleniumUtils.click(webElements.goToOutreachHistoryLink);
        waitTillDocumentReady(TimeOuts.SIXTY_SECONDS);

        IndividualDashboardOutreachHistory individualDashboardOutreachHistory = new IndividualDashboardOutreachHistory(getDriver());

        individualDashboardOutreachHistory.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);

        return individualDashboardOutreachHistory;

    }



    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"d2Form:paginationPanel\"]//span")
        private List<WebElement> individualCount;

        @FindBy(xpath = "//*[@id=\"pageTitle\"]")
        private WebElement individualColumn;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr[1]/td[3]/a[1]")
        private WebElement hoverBtn;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a[.=\"Outreach History\"]")
        private WebElement goToOutreachHistoryLink;
    }
}
