package com.vh.mi.automation.api.pages.analytics.demography.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.features.IHavePaginationComponent;
import com.vh.mi.automation.api.pages.common.IDrillPage;

/**
 * Created by i10359 on 2/27/18.
 */
public interface ID09AgeGroupDrillPage extends IDrillPage , IHavePaginationComponent {
    IDataGrid getDataGrid();
    public IDrillPage drillDownToPage(String page);
    public IDataGridCustomizer getDataGridCustomizer();
    public boolean isDataGridCustomizable();


}
