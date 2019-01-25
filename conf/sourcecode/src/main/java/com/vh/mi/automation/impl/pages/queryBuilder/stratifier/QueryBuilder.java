package com.vh.mi.automation.impl.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.pages.queryBuilder.stratifier.IQueryBuilder;
import com.vh.mi.automation.impl.comp.AnalysisPeriod;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.CriteriaComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i20345 on 12/23/2016.
 */
public class QueryBuilder extends AbstractLandingPage implements IQueryBuilder {
    public static final String MODULE_ID = "175";
    private WebElements webElements;
    AnalysisPeriod period;
    LoadingPopup loading;
    CriteriaComponent criteriaComponent;

    public QueryBuilder(WebDriver driver) {
        super(driver, MODULE_ID);
        webElements = new WebElements(driver);
        period = new AnalysisPeriod(getDriver());
        loading=new LoadingPopup(getDriver());
        criteriaComponent = new CriteriaComponent(getDriver());
    }


    public <T> T selectCriteriaComponent(Class<T> criteriaClass) {
        return criteriaComponent.goToCriteria(criteriaClass);
    }

    public static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "d2Form:demographyGrid:0:age:0:ageLower")
        private WebElement txtAgeLower;


    }

}
