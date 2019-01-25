package com.vh.mi.automation.api.pages.analytics.claims;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.common.IDrillPage;

/**
 * Created by i10359 on 10/27/17.
 */
public interface IClaims01DrillPage extends IDrillPage {
    public IDataGrid getDataGrid();
    public void generateCSV();
    public String getJobName();
    public void filterPaidAmounts();


}
