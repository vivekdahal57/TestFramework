package com.vh.mi.automation.api.pages.analytics.claims.Drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.common.IDrillPage;

/**
 * Created by i10359 on 11/30/17.
 */
public interface ICommonBusinessLevelDrillPage extends IDrillPage {
    public IDataGrid getDataGrid();
    public IDrillPage drillDownToPage(String page);
}
