package com.vh.mi.automation.impl.reportManager;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.features.IAmWebComponent;
import com.vh.mi.automation.api.reportManager.IReportManagerPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nimanandhar
 */
public abstract class AbstractReportManagerPage extends AbstractWebComponent implements IReportManagerPage {
    private final WebElements webElements;


    public AbstractReportManagerPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public void doSwitchToMainFrame() {
        new WebDriverWait(getDriver(), TimeOuts.SIXTY_SECONDS).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));
    }


    @Override
    public String getDisplayedTitle() {
        getDriver().switchTo().defaultContent().switchTo().frame("mainFrame");
        return webElements.applicationName.getText();
    }

    public <T> T logoutExpectingPage(Class<T> expectedClass, Integer waitTime) {
        getDriver().switchTo().defaultContent().switchTo().frame("mainFrame");
        Actions actions = new Actions(getDriver());
        actions.moveToElement(webElements.logout).click().perform();
        T pageInstance = PageFactory.initElements(getDriver(), expectedClass);
        waitTillDocumentReady(waitTime);
        return pageInstance;
    }

    @Override
    public List<String> getDisplayedTabs() {
        List<String> displayedTabs = new ArrayList<>();
        for (WebElement webElement : webElements.globalNavigation.findElements(By.tagName("a"))) {
            displayedTabs.add(webElement.getText());
        }
        return displayedTabs;
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "applicationName")
        WebElement applicationName;

        @FindBy(id = "GlobalNavigation1")
        WebElement globalNavigation;

        @FindBy(linkText = "Logout")
        WebElement logout;
    }


}
