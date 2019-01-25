package com.vh.mi.automation.test.pages.analytics.snfProfiler;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.pages.analytics.snfProfiler.ISNFProfilesSP100;
import com.vh.mi.automation.impl.pages.analytics.snfProfiler.SNFProfilesSP100;
import com.vh.mi.automation.test.comp.pagination.AbstractPaginationComponentTest;
import org.testng.annotations.BeforeClass;

/**
 * Created by i10359 on 11/16/17.
 */
public class SNFProfilesSP100PaginationTest extends AbstractPaginationComponentTest {
    private ISNFProfilesSP100 snfProfilesSP100;

    @BeforeClass
    public void setUp(){
        super.setUp();
    }

    @Override
    protected void setupPage() {
        snfProfilesSP100=navigationPanel.doNavigateTo(SNFProfilesSP100.class,defaultWaitTime);
    }

    @Override
    protected IPaginationComponent getPaginationComponent() {
      return snfProfilesSP100.getPaginationComponent();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return snfProfilesSP100.getDataGrid();
    }
}
