package com.vh.mi.automation.api.pages.analytics.demography.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.common.IDrillPage;

/**
 * Created by i10359 on 3/2/18.
 */
public interface IDemographyIndividualDrillPage extends IDrillPage{
    IDataGrid getDataGrid();
}
