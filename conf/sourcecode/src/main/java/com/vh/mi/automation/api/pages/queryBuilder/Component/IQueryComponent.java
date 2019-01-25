package com.vh.mi.automation.api.pages.queryBuilder.Component;

import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.RefineLogic;

/**
 * Created by i81306 on 5/3/2017.
 */
public interface IQueryComponent {

    public RefineLogic goToRefineLogicClaimsSearch();
    public com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic goToRefineLogicStratifier();
    public void resetPage();
}
