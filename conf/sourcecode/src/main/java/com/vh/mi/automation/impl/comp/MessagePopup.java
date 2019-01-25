package com.vh.mi.automation.impl.comp;

import com.google.common.base.Function;
import com.vh.mi.automation.api.annotations.logging.LogMethodExecutionTime;
import com.vh.mi.automation.api.comp.ILoadingComp;
import com.vh.mi.automation.api.constants.TimeOuts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Deprecated in favor of {@link com.vh.mi.automation.impl.comp.loading.MessagePopup}
 *
 * @author nimanandhar
 */
@Deprecated
public class MessagePopup implements ILoadingComp {
    private String message;
    private WebDriver driver;
    private long timeOut;


    public MessagePopup(WebDriver driver) {
        this(driver, TimeOuts.THREE_MINUTES);
    }

    public MessagePopup(WebDriver driver, long timeOut) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.timeOut = timeOut;
    }

    @LogMethodExecutionTime
    @Override
    public void waitTillDisappears() {
        if (isDisplayed()) {
            message = ani_message.getText();
            new WebDriverWait(driver, timeOut).until(new Function<WebDriver,Boolean>() {

                @Override
                public Boolean apply(WebDriver arg0) {
                    return !isDisplayed();
                }
            });
        }

    }

    public boolean isDisplayed() {
        return ani_message.isDisplayed();
    }

    public String getMessage() {
        return message;
    }

    public void reset() {
        message = null;
    }


    @FindBy(id = "ani_message")
    WebElement ani_message;

}
