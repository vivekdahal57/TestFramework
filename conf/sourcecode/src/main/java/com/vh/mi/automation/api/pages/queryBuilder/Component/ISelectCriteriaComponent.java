package com.vh.mi.automation.api.pages.queryBuilder.Component;

/**
 * Created by i20345 on 1/6/2017.
 */
public interface ISelectCriteriaComponent {

    public <T> T goToCriteria(Class<T> criteriaClass);

}
