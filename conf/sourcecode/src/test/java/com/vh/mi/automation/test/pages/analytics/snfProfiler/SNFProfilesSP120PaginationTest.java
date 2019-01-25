package com.vh.mi.automation.test.pages.analytics.snfProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.pages.analytics.snfProfiler.Drill.ISP120SNFAdmissionsDrillPage;
import com.vh.mi.automation.api.pages.analytics.snfProfiler.ISNFProfilesSP100;
import com.vh.mi.automation.impl.pages.analytics.snfProfiler.SNFProfilesSP100;
import com.vh.mi.automation.test.comp.pagination.AbstractPaginationComponentTest;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;

/**
 * Created by i10359 on 11/16/17.
 */
public class SNFProfilesSP120PaginationTest extends AbstractPaginationComponentTest {

    private ISNFProfilesSP100 snfProfilesSP100;
    private ISP120SNFAdmissionsDrillPage snfAdmissionsDrillPage;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }


    @Override
    protected void setupPage() {
        snfProfilesSP100=navigationPanel.doNavigateTo(SNFProfilesSP100.class,defaultWaitTime);
        Assertions.assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo("(SP100) SNF Profiles");
       snfAdmissionsDrillPage=(ISP120SNFAdmissionsDrillPage)snfProfilesSP100.drillDownToPage("SNF Admissions");

    }

    @Override
    protected IPaginationComponent getPaginationComponent() {
        return snfAdmissionsDrillPage.getPaginationComponent();

    }

    @Override
    protected IDataGrid getDataGrid() {
        return snfAdmissionsDrillPage.getDataGrid();

    }
}
