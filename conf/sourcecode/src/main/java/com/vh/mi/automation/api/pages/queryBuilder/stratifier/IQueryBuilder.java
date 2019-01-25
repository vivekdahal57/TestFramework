package com.vh.mi.automation.api.pages.queryBuilder.stratifier;

/**
 * Created by i20345 on 12/23/2016.
 */
public interface IQueryBuilder {

    public <T> T selectCriteriaComponent(Class<T> criteriaClass);

}
