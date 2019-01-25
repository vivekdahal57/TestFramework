package com.vh.mi.automation.api.pages.queryBuilder.mvca.expert;

import com.vh.mi.automation.api.comp.IMemberList;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.features.*;
import com.vh.mi.automation.api.pages.common.IMIPage;

/**
 * Created by i80448 on 9/17/2014.
 */
public interface IMVCAExpert301E extends IMIPage, IAmLandingPage, IHaveBusinessLevel, IHaveAnalysisPeriod, IHaveReportingBy, IHaveDataGrid, IHaveMemberList {


    public IDataGrid getDataGrid();

    public boolean isDataGridCustomizable();

    public IDataGridCustomizer getDataGridCustomizer();

    public IMemberList getMemberList();

    public void saveQuery(String queryName);
    public void applyMinimumQueryCriteriaRequired(IMVCAExpert301E.Group group);
    public String getReportOperationSuccessfulMessage();
    public void createStaticMemberlistWithTopNMembers(String memberListName, int topN);

    public void deleteQueryWithName(String queryName);
    public String getExpectedQueryDeletionMessage();
    public void selectQueryWithName(String savedQueryName);
    public void createDymanicMemberList(int topN,String memmberListName);

    enum Group {
        DIAGNOSIS_GROUP("Diagnosis Group"),
        DXCG_ACC("DxCG ACC"),
        DXCG_RCC("DxCG RCC"),
        DXCG_CC("DxCG CC"),
        DXCG_DX("DxCG Dx"),
        PROCEDURE_GROUP("Procedure Group"),
        RX_CLASS("Rx Class"),
        DRUG("Drug"),
        DIAGNOSIS_CODE("Diagnosis Code"),
        PROCEDURE("Procedure");

        private String value;

        Group(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
