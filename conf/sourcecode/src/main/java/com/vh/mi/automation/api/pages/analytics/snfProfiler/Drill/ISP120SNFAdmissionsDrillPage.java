package com.vh.mi.automation.api.pages.analytics.snfProfiler.Drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.features.IHavePaginationComponent;
import com.vh.mi.automation.api.pages.common.IDrillPage;

/**
 * Created by i10359 on 11/16/17.
 */
public interface ISP120SNFAdmissionsDrillPage extends IDrillPage,IHavePaginationComponent{
    public IDataGrid getDataGrid();
}
