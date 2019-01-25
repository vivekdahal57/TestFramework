package com.vh.mi.automation.impl.pages.queryBuilder.Component;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.Component.IQueryComponent;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.RefineLogic;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.queryManagement.QueryManagement;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 5/3/2017.
 */
public class QueryComponent extends AbstractWebComponent implements IQueryComponent{

    private WebElements webElements;

    public QueryComponent(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }



    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    @Override
    public RefineLogic goToRefineLogicClaimsSearch() {
        RefineLogic refineLogic = PageFactory.initElements(getDriver(), RefineLogic.class);
        goToRefineLogicPage();
        return refineLogic;
    }

    @Override
    public com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic goToRefineLogicStratifier() {
        com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic refineLogic = PageFactory.initElements(getDriver(), com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic.class);
        goToRefineLogicPage();
        return refineLogic;
    }

    public QueryManagement saveQueryWithDefaultOptionsWithName(String queryName){
        SeleniumUtils.hover(getDriver(), webElements.queryHoverElement);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.saveAsLink);
        SeleniumUtils.click(webElements.saveAsLink);
        WaitUtils.waitUntilDisplayed(getDriver(), webElements.queryNameTextBox, TimeOuts.FIVE_SECONDS);
        SeleniumUtils.sendKeysToInput(queryName, webElements.queryNameTextBox);
        SeleniumUtils.click(webElements.continueButton);
        return PageFactory.initElements(getDriver(), QueryManagement.class);
    }

    private void goToRefineLogicPage(){
        SeleniumUtils.hover(getDriver(), webElements.queryHoverElement);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.refineLogicLink);
        SeleniumUtils.click(webElements.refineLogicLink);
    }

    public void resetPage(){
        SeleniumUtils.click(webElements.resetButton);
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {

            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath="//div[@id='d2Form:preferenceFull']//div[text()='Query']//following::div[1]")
        private WebElement queryHoverElement;

        @FindBy(xpath="//div[@id='query_menu']//a[text()='Refine Logic']")
        private WebElement refineLogicLink;

        @FindBy(xpath="//*[@id='d2Form:link_CS_SAVE']")
        private WebElement saveAsLink;

        @FindBy(xpath="//input[@id='d2Form:queryName']")
        private  WebElement queryNameTextBox;

        @FindBy(xpath="//input[@id='d2Form:saveButton']")
        private WebElement continueButton;

        @FindBy(xpath="//*[@id='topToolBarDiv']//a[text()='Reset']")
        private WebElement resetButton;

    }
}
