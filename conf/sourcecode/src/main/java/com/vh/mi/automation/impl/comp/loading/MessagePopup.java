package com.vh.mi.automation.impl.comp.loading;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.vh.mi.automation.api.annotations.logging.LogMethodExecutionTime;
import com.vh.mi.automation.api.comp.ILoadingComp;
import com.vh.mi.automation.api.constants.TimeOuts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author nimanandhar
 */
public class MessagePopup implements ILoadingComp {
    private final WebDriver driver;
    private final long timeOut;
    private final WebElements webElements;
    private String message;

    public MessagePopup(WebDriver driver) {
        this(driver, TimeOuts.THREE_MINUTES);
    }

    public MessagePopup(WebDriver driver, long timeOut) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.timeOut = timeOut;
        webElements = new WebElements(driver);

    }

    @LogMethodExecutionTime
    @Override
    public void waitTillDisappears() {
        if (isDisplayed()) {
            message = webElements.ani_message.getText();
            new WebDriverWait(driver, timeOut).until(new Function<WebDriver,Boolean>() {


                @Override
                public Boolean apply(WebDriver arg0) {
                    return !isDisplayed();
                }
            });
        }
    }

    public boolean isDisplayed() {
        return webElements.ani_message.isDisplayed();
    }

    public String getMessage() {
        return message;
    }


    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "ani_message")
        WebElement ani_message;
    }
}
