package com.vh.mi.automation.test.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.Drill.IHP120AdmissionDrillPage;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.IHospitalProfilesHP100;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100;
import com.vh.mi.automation.test.comp.pagination.AbstractPaginationComponentTest;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;

import static com.vh.mi.automation.api.constants.MILandingPages.HOSPITAL_PROFILER;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 11/14/17.
 */
public class HospitalProfilesHP120PaginationTest extends AbstractPaginationComponentTest {
    private IHospitalProfilesHP100 hp100;

    private IHP120AdmissionDrillPage hp120admissionDrillPage;


    @BeforeClass
    public void setup() {
        super.setUp();
    }


    @Override
    protected void setupPage() {
        hp100 = navigationPanel.doNavigateTo(HospitalProfilesHP100.class, defaultWaitTime);
        Assertions.assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(HOSPITAL_PROFILER.getPageTitle());
        hp120admissionDrillPage = (IHP120AdmissionDrillPage) hp100.drillDownToPage("Admission");

    }

    @Override
    protected IPaginationComponent getPaginationComponent() {
        return hp120admissionDrillPage.getPaginationComponent();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return hp120admissionDrillPage.getDataGrid();
    }
}
