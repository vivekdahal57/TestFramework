package com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch;

import com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.queryBuilder.IQueryBuilder;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.pages.queryBuilder.Component.CriteriaComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by nimanandhar on 9/12/2014.
 */

public class QueryBuilder extends AbstractLandingPage{
    public static final String MODULE_ID = "205";

    public QueryBuilder(WebDriver driver) {
        super(driver, MODULE_ID);
    }

    public <T> T goToCriteria(Class<T> criteriaClass) {
        return PageFactory.initElements(getDriver(), CriteriaComponent.class).goToCriteria(criteriaClass);
    }
}
