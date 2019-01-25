package com.vh.mi.automation.impl.pages.admin.pages.Security;

import com.vh.mi.automation.api.pages.admin.pages.security.ISecurityFramework;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 8/22/2017.
 */
public class SecurityFrameworkPage extends AbstractWebComponent implements ISecurityFramework {

    private final WebElements webElements;

    public SecurityFrameworkPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.titleBar.getText().contains("Module Security FrameWork");
    }

    @Override
    public void selectScheme(String schemeName) {
        SeleniumUtils.selectByVisibleText(webElements.selectScheme, schemeName);
        SeleniumUtils.click(webElements.nextButton);
    }

    @Override
    public void applyMinimumSFW() {
        if(SeleniumUtils.isCheckboxSelected(webElements.allCheckbox)){
            SeleniumUtils.click(webElements.allCheckbox);
        }
        SeleniumUtils.click(webElements.nextButton);

        SeleniumUtils.click(webElements.allowLowerLevelsCheckbox);
        SeleniumUtils.click(webElements.firstLvlCheckbox);
        SeleniumUtils.click(webElements.finishButton);

    }

    @Override
    public void allowAllSFW() {
        if(!SeleniumUtils.isCheckboxSelected(webElements.allCheckbox)){
            SeleniumUtils.click(webElements.allCheckbox);
        }
        SeleniumUtils.click(webElements.nextButton);
    }

    private static class WebElements {
        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(className = "Titlebar")
        WebElement titleBar;

        @FindBy(name = "schemeID")
        WebElement selectScheme;

        @FindBy(className = "MainMenuButton")
        WebElement nextButton;

        @FindBy(name = "allowAllAccess")
        WebElement allCheckbox;

        @FindBy(id = "planTypeSelectionAll")
        WebElement allowLowerLevelsCheckbox;

        @FindBy(xpath = "(//input[@type='checkbox'])[1]")
        WebElement firstLvlCheckbox;

        @FindBy(xpath = "//TD[@height='30'][contains(text(),'Finish')]")
        WebElement finishButton;





    }
}
