package com.vh.mi.automation.impl.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.hcciClaims.HcciClaims01ServicesDataGridColumn;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import com.vh.mi.automation.impl.pages.analytics.hcciClaims.DrillLevels.CommonBusinessLevelDrillPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by i10359 on 2/20/18.
 */
public class HcciServicesDataGrid extends AbstractDataGrid {

    public HcciServicesDataGrid(WebDriver driver){
        super(driver);

    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return HcciClaims01ServicesDataGridColumn.fromId(id);

    }

    @Override
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        switch (drillOption) {
            case "Claims":
                return HcciServicesDrillPage.class;
            default:
                return CommonBusinessLevelDrillPage.class;
        }

    }

}
