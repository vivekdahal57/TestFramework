package com.vh.mi.automation.impl.comp.quintileByGroupSize;

import com.vh.mi.automation.api.comp.quintileByGroupSize.IQuintileByGroupSize;
import com.vh.mi.automation.api.comp.quintileByGroupSize.QuintileOptions;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author pakshrestha
 */
public class QuintileByGroupSize extends AbstractWebComponent implements IQuintileByGroupSize {
    private final WebElements webElements;

    public QuintileByGroupSize(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        throw new NotImplementedException();
    }

    @Override
    public String getSelectedOption() {
        return webElements.quintileGroupBody.getText();
    }

    @Override
    public List<String> getOptions() {
        showPopupMenu();

        List<WebElement> quintileOptions = webElements.quintileGroupMenuBody.findElements(By.tagName("a"));
        List<String> quintileOptionsTexts = new ArrayList<>();

        for (WebElement element : quintileOptions) {
            quintileOptionsTexts.add(element.getText());
        }

        hidePopupMenu();

        return quintileOptionsTexts;
    }


    private void showPopupMenu() {
        Action hoverAction = new Actions(getDriver())
                .moveToElement(webElements.quintileByGroupSizeHoverIcon)
                .build();
        hoverAction.perform();
    }


    private void hidePopupMenu() {
        Action hoverAction = new Actions(getDriver())
                .moveToElement(webElements.quintileGroupBody)
                .build();
        hoverAction.perform();
    }


    @Override
    public void selectOption(QuintileOptions option) {
        String selectedOption = getSelectedOption();
        if (selectedOption.equals(option.getDisplayText())) {
            return;
        }
        showPopupMenu();

        WebElement element = webElements.quintileGroupMenuBody.findElement(By.linkText(option.getMenuText()));
        SeleniumUtils.click(element,getDriver());
        new LoadingPopup(getDriver()).waitTillDisappears();
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "d2Form:quintileGrp_body")
        WebElement quintileGroupBody;

        @FindBy(xpath = ".//*[@id='d2Form:quintileGrp_header']//*[@class='d2-tglPanel-mark_1']")
        WebElement quintileByGroupSizeHoverIcon;

        @FindBy(id = "quintileGrp_menu")
        WebElement quintileGroupMenuBody;

    }
}
