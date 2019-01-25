package com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.Drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 11/13/17.
 */
public class HP120AdmissionDrillPageDataGrid extends AbstractDataGrid {


    public HP120AdmissionDrillPageDataGrid(WebDriver driver){
        super(driver);
        columnIdExtractor = new HP120AdmissionDrillPageColumnIdExtractor();

    }


    @Override
    protected IDataGridColumn getColumn(String id) {
        return HP120AdmissionsDrillPageColumns.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
      switch(drillOption){
          case "Claim Details":
              return  HP120AdmissionClaimDetailsDrillPage.class;
      }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }
}
