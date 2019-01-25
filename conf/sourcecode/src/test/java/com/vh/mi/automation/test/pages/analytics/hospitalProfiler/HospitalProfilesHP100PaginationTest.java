package com.vh.mi.automation.test.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.IHospitalProfilesHP100;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100;
import com.vh.mi.automation.test.comp.pagination.AbstractPaginationComponentTest;
import org.testng.annotations.BeforeClass;

/**
 * Created by i10359 on 11/13/17.
 */
public class HospitalProfilesHP100PaginationTest extends AbstractPaginationComponentTest {
    private IHospitalProfilesHP100 hospitalProfilesHP100;

    @BeforeClass
    public void setup(){
        super.setUp();
    }

    @Override
    protected void setupPage() {
        hospitalProfilesHP100=navigationPanel.doNavigateTo(HospitalProfilesHP100.class,defaultWaitTime);
    }

    @Override
    protected IPaginationComponent getPaginationComponent() {
        return hospitalProfilesHP100.getPaginationComponent();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return hospitalProfilesHP100.getDataGrid();
    }
}
