package com.vh.mi.automation.impl.pages.queryBuilder.Component;

import com.vh.mi.automation.api.pages.common.IQuerybuilderPage;
import com.vh.mi.automation.api.pages.queryBuilder.Component.ISelectCriteriaComponent;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i20345 on 12/23/2016.
 */

public class CriteriaComponent extends AbstractWebComponent implements ISelectCriteriaComponent{
    private WebElements webElements;
    private String CRITERIA_NAME_PLACEHOLDER="${criteriaName}";

    public CriteriaComponent(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
    }


    @Override
    public <T> T goToCriteria(Class<T> criteriaClass) {
        T pageInstance = PageFactory.initElements(getDriver(), criteriaClass);
        IQuerybuilderPage queryBuilderPageInstance = (IQuerybuilderPage) pageInstance;
        goToCriteriaPage(queryBuilderPageInstance.getPageLink());
        return pageInstance;
    }

    private void goToCriteriaPage(String criteriaLinkName){
        SeleniumUtils.hover(getDriver(), webElements.criteriaHoverElement);
        WebElement linkToClick = getCriteriaLinkForName(criteriaLinkName);
        WaitUtils.waitUntilEnabled(getDriver(), linkToClick);
        SeleniumUtils.click(linkToClick);
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    private WebElement getCriteriaLinkForName(String selectCriteria){
        String xpath = "//div[@id='criteria_menu']//a[text()='${criteriaName}']";
        return SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(CRITERIA_NAME_PLACEHOLDER,selectCriteria));
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath="//div[@id='d2Form:preferenceFull']//div[text()='Criteria']//following::div[1]")
        private WebElement criteriaHoverElement;
    }
}