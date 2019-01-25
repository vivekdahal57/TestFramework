package com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.PlaceOfService;

import com.vh.mi.automation.api.pages.common.IQuerybuilderPage;
import com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.queryBuilder.IQueryBuilder;

/**
 * Created by i10359 on 12/18/17.
 */
public interface IPlaceOfService extends IQuerybuilderPage {
    public void includeTopNGroups(int topN);
}
