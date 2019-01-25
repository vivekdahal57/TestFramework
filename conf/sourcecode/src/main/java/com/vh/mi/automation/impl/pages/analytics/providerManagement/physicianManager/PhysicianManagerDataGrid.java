package com.vh.mi.automation.impl.pages.analytics.providerManagement.physicianManager;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.providerManagement.PhysicianManager.PhysicianManagerDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.physicianManager.Drill.ProfilerDashBoardDrillPage;
import org.openqa.selenium.WebDriver;

public class PhysicianManagerDataGrid extends AbstractDataGrid {

    public PhysicianManagerDataGrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new PhysicianManagerColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return PhysicianManagerDataGridColumn.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Profiler Dashboard":
                return ProfilerDashBoardDrillPage.class;

        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }
}
