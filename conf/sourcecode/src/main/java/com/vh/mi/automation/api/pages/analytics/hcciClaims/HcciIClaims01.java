
package com.vh.mi.automation.api.pages.analytics.hcciClaims;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.api.features.IHaveAnalysisPeriod;
import com.vh.mi.automation.api.features.IHaveBusinessLevel;
import com.vh.mi.automation.api.features.IHaveReportingBy;
import com.vh.mi.automation.api.pages.common.IDrillPage;

import java.io.IOException;

/**
 * @author i80448
 */
public interface HcciIClaims01 extends IHaveAnalysisPeriod, IHaveBusinessLevel, IHaveReportingBy, IAmLandingPage {
    public IDataGrid getDataGrid();

    public boolean isDataGridCustomizable();

    public IDataGridCustomizer getDataGridCustomizer();

    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context)throws IOException;

    public IDrillPage drillDownToPage(String page);

    public HcciIClaims01DrillPage drillDownToHcciClaims();

    public HcciIClaims01DrillPage drillDownToClaimsFromPharmacy();

}
