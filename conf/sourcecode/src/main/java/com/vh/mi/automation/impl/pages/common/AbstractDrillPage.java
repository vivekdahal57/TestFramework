package com.vh.mi.automation.impl.pages.common;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import com.vh.mi.automation.impl.utils.screenshot.ScreenShotTaker;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

/**
 * @author nimanandhar
 */
public abstract class AbstractDrillPage extends AbstractMIPage implements IDrillPage {
    private final WebElements webElements;
    private LoadingPopup loadingPopup;

    public AbstractDrillPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        loadingPopup = new LoadingPopup(getDriver());
    }


    @Override
    public boolean isImageFullyLoaded() {
        return webElements.graphImage.isDisplayed();
    }

    @Override
    public void closeCurrentWindowAndGoBackToParentWindow() {
        SeleniumUtils.closeChildWindowAndGoBackToMainWindow(getDriver(),true);
    }

    @Override
    public void doWaitTillPopUpDisappears() {
        loadingPopup.waitTillDisappears();
    }

    @Override
    public boolean isDrillPageValid() {
        return true;
    }

    @Override
    public IDataGrid getDataGrid() {
        return null;
    }

    @Override
    public void doClose() {
        Preconditions.checkState(getDriver().getWindowHandles().size() == 2);

        if (SeleniumUtils.isIE(getDriver())) {
            getDriver().close();
        } else {
            WebElement closeLinkElement = webElements.closeLinkElement;
            SeleniumUtils.click(closeLinkElement, getDriver());
        }

        try {
            WaitUtils.waitUntil(getDriver(), 10, new Function<WebDriver,Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    return driver.getWindowHandles().size() == 1;
                }
            });
        } catch (TimeoutException timeoutException) {
            Set<String> windowHandles = getDriver().getWindowHandles();
            logger.error("Expected number of windows to be 1 but was " + windowHandles.size());
            logger.info("Forcefully closing window");
            if (windowHandles.size() == 2) {
                ScreenShotTaker screenShotTaker = new ScreenShotTaker();
                screenShotTaker.takeScreenShot(getDriver(), "error_" + this.getClass().getSimpleName() + currentTime()+ "_active_window.png");
                SeleniumUtils.closeCurrentWindowAndSwitchToNewWindow(getDriver());
                screenShotTaker.takeScreenShot(getDriver(), "error_" + this.getClass().getSimpleName()+ currentTime()+ "_passive_window.png");
            }
        }

        Set<String> windowHandles = getDriver().getWindowHandles();
        Preconditions.checkState(windowHandles.size() == 1);
        String handle = windowHandles.iterator().next();
        getDriver().switchTo().window(handle);
    }

    private String currentTime(){
        return String.valueOf(System.currentTimeMillis());
    }



    private static class WebElements {

        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(linkText = "Close")
        private WebElement closeLinkElement;

        @FindBy(xpath = "//*[@id=\"d2Form:chart_image\"]")
        private WebElement graphImage;

    }


}
