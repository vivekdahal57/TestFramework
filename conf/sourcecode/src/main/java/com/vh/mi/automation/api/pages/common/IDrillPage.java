package com.vh.mi.automation.api.pages.common;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.features.IAmTabbedPage;

/**
 * Represents a Page obtained after drilling down
 * from other pages,eg data grid
 *
 * @author nimanandhar
 */
public interface IDrillPage extends IMIPage, IAmTabbedPage {
    boolean isDrillPageValid();
    public IDataGrid getDataGrid();
    public boolean isImageFullyLoaded();
    public void closeCurrentWindowAndGoBackToParentWindow();
    public void doWaitTillPopUpDisappears();

}
