package com.vh.mi.automation.impl.pages.analytics.demography.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 2/27/18.
 */
public class D09AgeGroupDrillPageDataGrid extends AbstractDataGrid {

    public D09AgeGroupDrillPageDataGrid(WebDriver driver){
        super(driver);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return D09AgeGroupDrillPageDataGridColumns.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch(drillOption){
            case "Individual":
                return DemographyIndividualDrillPage.class;
        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }
}
