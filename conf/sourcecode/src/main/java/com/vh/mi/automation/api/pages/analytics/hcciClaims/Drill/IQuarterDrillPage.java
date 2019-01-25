package com.vh.mi.automation.api.pages.analytics.hcciClaims.Drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.common.IDrillPage;

/**
 * Created by i10359 on 12/27/17.
 */
public interface IQuarterDrillPage extends IDrillPage{
    public IDataGrid getDataGrid();
    public IDrillPage drillDownToPage(String page);
}
