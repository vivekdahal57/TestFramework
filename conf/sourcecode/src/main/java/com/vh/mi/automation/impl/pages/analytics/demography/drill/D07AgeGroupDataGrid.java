package com.vh.mi.automation.impl.pages.analytics.demography.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.demography.drill.D07AgeGroupDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

public class D07AgeGroupDataGrid extends AbstractDataGrid {

    public D07AgeGroupDataGrid(WebDriver driver) {
        super(driver);
    }


    @Override
    protected IDataGridColumn getColumn(String id) {
        return D07AgeGroupDataGridColumn.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
      switch(drillOption){
          case "Age Sub-group":
              return D09AgeGroupDrillPage.class;
      }

        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }
}
