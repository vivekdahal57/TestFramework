package com.vh.mi.automation.impl.comp.adjustedNorm;

import com.vh.mi.automation.api.comp.adjustedNorm.AdjustedNormOptions;
import com.vh.mi.automation.api.comp.adjustedNorm.IAdjustedNorm;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.impl.comp.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pakshrestha
 */
public class AdjustedNorm extends AbstractWebComponent implements IAdjustedNorm {
    private final WebElements webElements;

    public AdjustedNorm(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public String getSelectedOption() {
        return webElements.adjustedNormTypeBody.getText();
    }

    @Override
    public List<String> getOptions() {
        showPopupMenu();

        List<WebElement> menuItemsElements = webElements.adjustedNormTypeMenuBody.findElements(By.tagName("a"));
        List<String> menuItemsTexts = new ArrayList<>();

        for (WebElement menuItemsElement : menuItemsElements) {
            menuItemsTexts.add(menuItemsElement.getText());
        }

        hidePopupMenu();

        return menuItemsTexts;
    }

    private void showPopupMenu() {
        Action hoverAction = new Actions(getDriver())
                .moveToElement(webElements.adjustedNormTypeHoverIcon)
                .build();
        hoverAction.perform();
        new WebDriverWait(getDriver(), TimeOuts.FIVE_SECONDS).until(ExpectedConditions.visibilityOf(webElements.adjustedNormTypeBody));
    }

    private void hidePopupMenu() {
        Action hoverAction = new Actions(getDriver())
                .moveToElement(webElements.adjustedNormTypeBody)
                .build();
        hoverAction.perform();
    }

    @Override
    public void selectOption(AdjustedNormOptions options) {
        if(getSelectedOption().equals(options.getDisplayText())){
            return;
        }
        showPopupMenu();
        WebElement linkElement = webElements.adjustedNormTypeMenuBody.findElement(By.linkText(options.getMenuText()));
      //  linkElement.click();
        SeleniumUtils.click(linkElement,getDriver());
        new LoadingPopup(getDriver()).waitTillDisappears();
    }

    @Override
    public boolean isFullyLoaded() {
        throw new NotImplementedException();
    }

    private static class WebElements {
        public WebElements(WebDriver driver) { PageFactory.initElements(driver, this); }

        @FindBy(id="d2Form:adjNormType_body")
        WebElement adjustedNormTypeBody;

        @FindBy(id="adjNormType_menu")
        WebElement adjustedNormTypeMenuBody;

        @FindBy(xpath=".//*[@id='d2Form:adjNormType_header']//*[@class='d2-tglPanel-mark_1']")
        WebElement adjustedNormTypeHoverIcon;
    }
}
