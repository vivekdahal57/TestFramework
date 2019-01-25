package com.vh.mi.automation.impl.comp.dataGrid;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.comp.dataGrid.drill.IDataGridDrillMenu;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.dataGrid.drill.DataGridDrillMenu;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author nimanandhar
 */
public class DataGridRow implements IDataGridRow {

    private WebDriver driver;
    private WebElement compRootElm;
    private Function<String, IDrillPage> drillFunction;


    public DataGridRow(WebDriver driver, WebElement compRootElm, Function<String, IDrillPage> pageFactory) {
        checkArgument(driver != null);
        checkArgument(compRootElm != null);
        checkArgument(pageFactory != null);

        this.driver = driver;
        PageFactory.initElements(driver, this);

        this.compRootElm = compRootElm;
        this.drillFunction = pageFactory;
    }

    @Override
    public ImmutableList<String> getValues() {
        throw new AutomationException("Not Implemented");
    }

    @Override
    public String getValue(int colPosition) {
        return compRootElm.findElement(By.xpath("./td[position()='" + colPosition + "']")).getText();
    }

    @Override
    public boolean hasDrillOptions() {
        return SeleniumUtils.elementExists(compRootElm, ".//a[@class='d2-drillbtn']");
    }

    @Override
    public ImmutableList<String> getDrillOptions() {
        return doShowDrillMenu().getDrillOptions();
    }

    @Override
    public ImmutableList<String> getDrillOptionsBetween(String from, String to) {
        return doShowDrillMenu().getDrillOptionsBetween(from, to);
    }

    @Override
    public ImmutableList<String> getDrillOptionsAfter(String from) {
        return doShowDrillMenu().getDrillOptionAfter(from);
    }

    @Override
    public IDataGridDrillMenu doShowDrillMenu() {
        WebElement drillBtn = getDrillBtn();


        //fix for chrome todo check for firefox and ie
        WebElement logo = driver.findElement(By.className("primaryLogo"));
        new Actions(driver).moveToElement(logo).moveToElement(drillBtn).perform();
        return new DataGridDrillMenu(driver);
    }

    @Override
    public IDrillPage doDrillBy(String option) {
        Preconditions.checkState(driver.getWindowHandles().size() >= 1, "Before clicking drill there should be only one window, but found " + driver.getWindowHandles().size());

        doShowDrillMenu().doDrillBy(option);
        SeleniumUtils.waitUntilMoreThanOneWindowsIsOpen(driver);
        switchToNewWindow();
        //instantiate appropriate page object
        return drillFunction.apply(option);
    }


    @Override
    public IDrillPage doDrillByOnSameWindow(String option) {
        Preconditions.checkState(driver.getWindowHandles().size() == 1, "Before clicking drill there should be only one window, but found " + driver.getWindowHandles().size());
        doShowDrillMenu().doDrillBy(option);
        return drillFunction.apply(option);
    }

    @Override
    public void doDrillByPopup(String option) {
        Preconditions.checkState(driver.getWindowHandles().size() == 1, "Before clicking drill there should be only one window, but found " + driver.getWindowHandles().size());
        doShowDrillMenu().doDrillBy(option);
    }

    private void switchToNewWindow() {
        Set<String> windowHandles = driver.getWindowHandles();
        Preconditions.checkState(windowHandles.size() >= 2, "Expected 2 windows but found " + windowHandles.size());

        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(driver.getWindowHandle())) {
                driver.switchTo().window(windowHandle);
                return;
            }
        }
        throw new RuntimeException("There was a problem switching windows. CurrentWindowHandle= " + driver.getWindowHandle() + " Window Handles = " + windowHandles);
    }

    private WebElement getDrillBtn() {
        checkState(compRootElm != null);

        List<WebElement> as = compRootElm.findElements(By.tagName("a"));
        for (WebElement elm : as) {
            if (elm.getAttribute("class").equals("d2-drillbtn")) {
                return elm;
            }
        }

        // TODO: optimize the implementation to get drill by column without lopping through each <td> if possible
        throw new AutomationException("Drill by menu button not available");
    }
}