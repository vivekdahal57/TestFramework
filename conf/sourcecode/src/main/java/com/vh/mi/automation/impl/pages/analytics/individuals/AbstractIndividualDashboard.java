package com.vh.mi.automation.impl.pages.analytics.individuals;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndividualDashboard;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nimanandhar
 */
public abstract class AbstractIndividualDashboard extends AbstractDrillPage implements IIndividualDashboard {
    private final WebElements webElements;

    public AbstractIndividualDashboard(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isNextButtonDisplayed() {
        try {
            webElements.nextButton.isDisplayed();
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    @Override
    public String getSelectedTab() {
        return webElements.selectedTab.getText();
    }

    @Override
    public List<String> getAvailableToolBarItems() {
        List<String> availableTabs = new ArrayList<>();
        for (WebElement tabElement : webElements.topToolBar.findElements(By.tagName("a"))) {
            availableTabs.add(tabElement.getText());
        }
        return availableTabs;
    }

    @Override
    public List<String> getAllTabs() {
        List<String> listOfTabs = new ArrayList<>();
        for (WebElement tabElement : webElements.tabs) {
            String tabText = tabElement.findElement(By.xpath("./table/tbody/tr/td")).getText();
            listOfTabs.add(tabText);
        }
        return listOfTabs;
    }

    @Override
    public boolean isTabAvailable(String tabName) {
        return getAllTabs().contains(tabName);
    }

    public ImmutableList<String> getDrillOptionsFor(String id) {
        String xpath = "//*[@id='"+ id +"']//a";
        List<WebElement> menus = getDriver().findElements(By.xpath(xpath));

        List<String> transform = Lists.transform(menus, new Function<WebElement, String>() {
            @Override
            public String apply(WebElement input) {
                return input.getAttribute("innerHTML").trim();
            }
        });

        return ImmutableList.copyOf(transform);
    }

    public void clickDrillOptionForComponent(String menuId, String linkName){
        String xpath = "//*[@id='"+ menuId +"']/..";
        WebElement hoverElement = SeleniumUtils.findElementByXPath(getDriver(), xpath);
        SeleniumUtils.hoverOnElement(hoverElement, getDriver());
        WebElement link = hoverElement.findElement(By.xpath(".//a[text()='" + linkName + "']"));
        WaitUtils.waitUntilEnabled(getDriver(), link);
        SeleniumUtils.click(link);
    }


    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//table[@id='d2Form:topExtraToolBar']//a[@class='d2-nextBtn']")
        WebElement nextButton;

        @FindBy(className = "rich-tab-header rich-tab-active d2-nowrap")
        WebElement selectedTab;

        @FindBy(id = "d2Form:topToolBar")
        WebElement topToolBar;

        @FindBy(xpath = "//td[@class='rich-tabhdr-side-cell']")
        List<WebElement> tabs;
    }
}
