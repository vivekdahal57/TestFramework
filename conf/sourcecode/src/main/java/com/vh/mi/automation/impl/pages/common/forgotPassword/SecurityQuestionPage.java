package com.vh.mi.automation.impl.pages.common.forgotPassword;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.fest.assertions.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class SecurityQuestionPage extends AbstractWebComponent {
    private final WebElements webElements;

    public SecurityQuestionPage(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.title.isDisplayed();
    }

    private boolean isErrorMessageDisplayed() {
        return SeleniumUtils.elementExists(webElements.rootElement, "./div[@class='errorMessage']");
    }

    public String getErrorMessage() {
        String errorMessage = webElements.errorMessage.getText();
        return errorMessage.replaceAll("\n", "");
    }


    public SecurityQuestionPage enterDate(String month, String dayOfMonth) {
        Select selectMonth = new Select(webElements.DOBMonth);
        selectMonth.selectByVisibleText(month);
        Select selectDay = new Select(webElements.DOBDay);
        selectDay.selectByValue(dayOfMonth);

        return this;
    }

    public SecurityQuestionPage enterSSN(String ssn) {
        webElements.SSNInput.sendKeys(ssn);
        return this;
    }

    public SecurityQuestionPage enterAnswer(String answer) {
        webElements.answerInput.sendKeys(answer);
        return this;
    }

    private void submit() {
        webElements.submitRequestBtn.click();

    }

    public SecurityQuestionPage submitExpectingErrorMessage() {
        submit();
        doWaitTillFullyLoaded(TimeOuts.TEN_SECONDS);
        Assertions.assertThat(isErrorMessageDisplayed()).isTrue();
        return this;
    }

    private static class WebElements {
        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//div[@class='MainBody']")
        WebElement rootElement;

        @FindBy(xpath = "//div[@class='TopTitleBar']/span[@class='StepName']")
        WebElement title;


        @FindBy(xpath = "//input[@name='quesAns']")
        WebElement answerInput;

        @FindBy(xpath = "//input[@name='ssn']")
        WebElement SSNInput;

        @FindBy(xpath = "//div[@class='errorMessage']")
        WebElement errorMessage;

        @FindBy(xpath = "//select[@name='DOBMonth']")
        WebElement DOBMonth;

        @FindBy(xpath = "//select[@name='DOBDAY']")
        WebElement DOBDay;

        @FindBy(xpath = "//input[@value='Click to Submit your Reset Request']")
        WebElement submitRequestBtn;


    }
}
