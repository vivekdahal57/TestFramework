package com.vh.mi.automation.impl.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.Drill.HP120AdmissionDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i80448 on 11/20/2014.
 */
public class HospitalProfilesHP100DataGrid extends AbstractDataGrid {
    public HospitalProfilesHP100DataGrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new HospitalProfilesColumIdExtractor();
        PageFactory.initElements(driver, this);
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return HospitalProfilesHP100Columns.fromId(id);
    }


    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Admission":
                return HP120AdmissionDrillPage.class;
        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }


}
