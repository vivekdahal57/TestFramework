package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Provider;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.Provider.ProviderDrillPageDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;

/**
 * Created by i82716 on 6/29/2017.
 */
public class ProviderDrillPageDataGrid extends AbstractDataGrid {

    public ProviderDrillPageDataGrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new ProviderDrillPageColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {

        return ProviderDrillPageDataGridColumns.fromId(id);
    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Individual":
                return IndividualDrillPage.class;

        }
        throw new AutomationException("Unrecognized drill Menu " + drillOption);
    }

}
