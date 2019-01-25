package com.vh.mi.automation.impl.comp;

import com.google.common.base.Function;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.features.IHaveCohortSelection;
import com.vh.mi.automation.api.pages.analytics.myDashboard.IMyDashboard;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.fest.assertions.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author nimanandhar
 * @author i80448
 */
public class CohortSelection extends AbstractWebComponent implements IHaveCohortSelection {
    private final WebElements webElements;
    private Actions action;

    public CohortSelection(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.cohortBodyElm.isDisplayed();
    }

    private void displayMenu() {
        if (!webElements.cohortBodyElm.isDisplayed()) {
            SeleniumUtils.hoverOnElement(webElements.addCohortHover, getDriver());
        }
        Assertions.assertThat(webElements.cohortBodyElm.isDisplayed()).as("Cohort Menu should be displayed").isTrue();
    }

    public void doSelect() {
        displayMenu();//need to display menu so that getText works , try other alternative
        action = new Actions(getDriver());
        action.moveToElement(webElements.addCohortHover).build().perform();
        webElements.selectCohortElm.click();
    }


    private void waitUntilElementIsDisplayed(final WebElement webElement) {
        new WebDriverWait(getDriver(), TimeOuts.TEN_SECONDS)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class)
                .until(new Function<WebDriver, Boolean>() {
                    @Override
                    public Boolean apply(WebDriver input) {
                        return webElement.isDisplayed();
                    }
                });
    }

    @Override
    public String setCohort(String cohortId) {
        waitUntilElementIsDisplayed(webElements.cohortPopupIDSearch);
        webElements.cohortPopupIDSearch.clear();
        webElements.cohortPopupIDSearch.sendKeys(cohortId);
        webElements.cohortPopupIDSearch.sendKeys(Keys.RETURN);
        try {
            Thread.sleep(3000);
            if (getDriver().findElements(By.xpath("//*[@id=\"d2FormCohortSelect:_cohortSelectionSimpleGrid:0:j_id114\"]/table/tbody/tr/td")).size() > 0) {
                webElements.cohortIDRadioBtn.click();
                webElements.cohortApplyBtn.click();
                waitUntilElementIsDisplayed(webElements.cohortApplyBtn);
                Thread.sleep(3000);
            } else {
                webElements.cohortCloseBtn.click();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cohortId;
    }

    private static class WebElements {
        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "prd_cohort_menu")
        private WebElement cohortMenu = null;

        @FindBy(id = "d2Form:prd_cohort_body")
        private WebElement cohortBodyElm = null;

        @FindBy(xpath = "//*[@id=\"d2Form:prd_cohort_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement cohortMenuButtonElm = null;

        @FindAll({@FindBy(id = "d2Form:lnk_2"), @FindBy(linkText = "Select a Cohort")})
        private WebElement selectCohortElm = null;

        @FindBy(id = "d2FormCohortSelect:_cohortSelectionSimpleGrid:column_COHORTID_header_filterInputText")
        private WebElement cohortPopupIDSearch = null;

        @FindBy(xpath = "//*[@id=\"d2FormCohortSelect:_cohortSelectionSimpleGrid:0:j_id114\"]/table/tbody/tr/td")
        private WebElement cohortIDRadioBtn = null;

        @FindBy(id = "d2FormCohortSelect:_cohortSelectPanelApply")
        private WebElement cohortApplyBtn = null;

        @FindBy(id = "d2FormCohortSelect:_cohortSelectPanelApply_1")
        private WebElement cohortCloseBtn = null;

        @FindBy(xpath = " //*[@id=\"d2Form:prd_cohort_header\"]/table/tbody/tr/td/div/div[2]")
        private WebElement addCohortHover = null;

    }

}