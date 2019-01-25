package com.vh.mi.automation.impl.pages.saml;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.pages.saml.IEULAPage;
import com.vh.mi.automation.impl.pages.common.AbstractMIPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Represents the End User License Agreement(EULA) Page
 *
 * @author nimandhar
 */
public class EULAPage extends AbstractMIPage implements IEULAPage {
    private final WebElements webElements;

    public EULAPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public <T extends AbstractWebComponent> T agree(Class<T> pageObjectClass, int waitTimeSeconds) {
        WebElement agreeButton = webElements.agreeButton;
        SeleniumUtils.click(agreeButton,getDriver());
        waitTillDocumentReady(waitTimeSeconds);
        T pageObject = PageFactory.initElements(getDriver(), pageObjectClass);
        return pageObject;
    }


    @Override
    public String getPageTitle() {
        return "End User License Agreement (EULA)";
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }

    private static final class WebElements {
        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "aura-yes")
        WebElement agreeButton;
    }
}
