package com.vh.mi.automation.impl.pages.analytics.snfProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.snfProfiler.Drill.SP120SNFAdmissionsDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 11/16/17.
 */
public class SNFProfilesSP100DataGrid extends AbstractDataGrid{

    public SNFProfilesSP100DataGrid(WebDriver driver){
        super(driver);
        columnIdExtractor = new SNFProfilesColumnExtractor();
        PageFactory.initElements(driver,this);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return null;
    }


    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch(drillOption){
            case "SNF Admissions":
                return SP120SNFAdmissionsDrillPage.class;
        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }
}
