package com.vh.mi.automation.impl.pages.saml;

import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81247 on 11/15/2016.
 */
public class DemoerAssistedInputPage extends AbstractWebComponent{

    @FindBy(id = "manual-input-test")
    WebElement qaTab;

    public DemoerAssistedInputPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @Override
    public boolean isFullyLoaded() {
        return qaTab.isDisplayed();
    }

    public DemoerQAPage loadQAPage(){
        qaTab.click();
        return new DemoerQAPage(getDriver());
    }
}
