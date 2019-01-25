package com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.common.IDrillPage;

/**
 * Created by i10359 on 11/21/17.
 */
public interface I670QualityAndRiskDrillPage extends IDrillPage {
    public IDataGrid getDataGrid();
    public IDrillPage drillDownToPage(String page);

}
