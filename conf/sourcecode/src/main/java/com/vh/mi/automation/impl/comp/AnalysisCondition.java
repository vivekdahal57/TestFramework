package com.vh.mi.automation.impl.comp;

import com.vh.mi.automation.api.comp.IAnalysisCondition;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisCondition  extends AbstractWebComponent implements IAnalysisCondition {
    private WebElements webElements;
    private LoadingPopup loadingPopup;
    private static Logger logger = LoggerFactory.getLogger(AnalysisCondition.class);

    public AnalysisCondition(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        loadingPopup = new LoadingPopup(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return true;
    }

    @Override
    public void selectAnalysisCondition(IAnalysisCondition.Type type, IAnalysisCondition.Level level, String value) {
        String id = "d2Form:" +  level.getLevel() + type.getType();
        WebElement webElement = SeleniumUtils.findElementById(getDriver(),id);
        try {
            SeleniumUtils.selectByVisibleText(webElement, value);
        } catch (NoSuchElementException ex) {
            logger.info("Type: " + type);
            logger.info("Level:" + level);
            logger.info("Id:" + id);
            throw new NoSuchElementException("Error Occured While Selecting Element" + ex);
        }
    }

    @Override
    public void clickRun() {
        webElements.runBtn.click();
        loadingPopup.waitTillDisappears();
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "d2Form:runbutton")
         WebElement runBtn;

    }
}
