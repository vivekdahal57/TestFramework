package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.test.comp.pagination.AbstractPaginationComponentTest;
import org.testng.annotations.BeforeClass;

/**
 * @author nimanandhar
 */
public class Indv301PaginationTest extends AbstractPaginationComponentTest {
    private IIndv301 indv301Page;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @Override
    protected void setupPage() {
        indv301Page = navigationPanel.doNavigateTo(Indv301.class, context.getDefaultWaitTimeout());
        indv301Page.popupExists();
    }

    @Override
    protected IPaginationComponent getPaginationComponent() {
        return indv301Page.getPaginationComponent();
    }

    @Override
    protected IDataGrid getDataGrid() {
        return indv301Page.getDataGrid();
    }

}
