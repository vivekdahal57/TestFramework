package com.vh.mi.automation.impl.pages.queryBuilder.Component;

import com.vh.mi.automation.api.pages.queryBuilder.Component.IQueryBuilderToolBar;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.RefineLogic;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 5/3/2017.
 */
public class QueryBuilderToolBar extends AbstractWebComponent implements IQueryBuilderToolBar {

    private WebElements webElements;

    public QueryBuilderToolBar(WebDriver driver) {
        super(driver);
        webElements = new WebElements(getDriver());
    }

    public CriteriaComponent getCriteriaComponent(){
        return PageFactory.initElements(getDriver(), CriteriaComponent.class);
    }

    public <T> T goToCriteria(Class<T> criteriaClass) {
       return getCriteriaComponent().goToCriteria(criteriaClass);
    }


    public QueryComponent getQueryComponent(){
        return PageFactory.initElements(getDriver(), QueryComponent.class);
    }

    public RefineLogic goToRefineLogicClaimsSearch(){
        return getQueryComponent().goToRefineLogicClaimsSearch();
    }

    public com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic goToRefineLogicStratifier(){
        return getQueryComponent().goToRefineLogicStratifier();
    }

    public DiagnosisByComponent getDiagnosisByComponent(){

        return PageFactory.initElements(getDriver(), DiagnosisByComponent.class);
    }

    public ProcedureByComponent getProcedureByComponent(){
        return PageFactory.initElements(getDriver(), ProcedureByComponent.class);
    }

    public void reset(){
        SeleniumUtils.click(webElements.btnReset);
    }

    public void resetPage(){
        getQueryComponent().resetPage();
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }
    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(xpath = "//*[@id=\"d2Form:topToolBar\"]/tbody/tr/td[2]/table/tbody/tr[2]/td/a")
        private WebElement btnReset;
    }
}
