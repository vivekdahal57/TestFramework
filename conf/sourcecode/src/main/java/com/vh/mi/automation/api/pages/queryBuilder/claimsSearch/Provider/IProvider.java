package com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.Provider;

import com.vh.mi.automation.api.pages.common.IQuerybuilderPage;

/**
 * Created by i10359 on 12/18/17.
 */
public interface IProvider  extends IQuerybuilderPage{
    public void includeTopNGroups(int topN);
}
