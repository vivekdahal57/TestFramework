package com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 3/14/18.
 */
public enum CD018DiseaseAnalysisByMembersDrillPageDataGridColumns  implements IDataGridColumn{
    INDIVIDUAL_ID("MemID" , "Individual Id" , DataType.STRING),
    RI("RI" , "RI" , DataType.INTEGER),
    CGI("DelRI" ,"CGI", DataType.INTEGER),
    ARI("ARI" , "ARI" , DataType.INTEGER),
    CURRENT("CURRENTSTATUS" , "Current", DataType.STRING),
    CM_STATUS("CMStatus" , "CM Status" , DataType.STRING),
    DM_STATUS("DM_Status" , "DM Status", DataType.STRING),
    NO_OF_DISEASES("ChronicCount" , "# Of Disease", DataType.INTEGER),
    NO_OF_ADMISSIONS("AdmitCount" , "# Of Admissions", DataType.INTEGER),
    ALOS("ALOS" , "ALOS", DataType.INTEGER),
    NO_OF_ER_VISITS("ERVisitCount" , "# Of ER Visits", DataType.INTEGER),
    ONSET_DATE("OnsetDate" , "Onset Date" , DataType.DATE),
    TOTAL_PAID("PaidAmt" , "Total Paid" , DataType.MONEY),
    ROLLING_YEAR_PAID("PaidAmt_CRY", "Rolling Year Paid" , DataType.MONEY),
    CONTRACT_YEAR_PAID("ContractYearPaid" , "Contract Year Paid", DataType.MONEY)
    ;
    private static Logger logger = LoggerFactory.getLogger(CD018DiseaseAnalysisByMembersDrillPageDataGridColumns.class);
    private String id;
    private String label;
    private DataType dataType;

    private CD018DiseaseAnalysisByMembersDrillPageDataGridColumns(String id, String label, DataType dataType){
       this.id = id;
       this.label = label;
       this.dataType = dataType;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public DataType getDataType() {
        return this.dataType;
    }

    @Override
    public String getLabel() {
        return this.label;
    }


    public static IDataGridColumn fromId(String id){
        for(CD018DiseaseAnalysisByMembersDrillPageDataGridColumns c : CD018DiseaseAnalysisByMembersDrillPageDataGridColumns.values()){
            if(c.getId().equals(id))
                return c;
        }
        logger.warn("Mapping not available for Id- "+id);
        return null;
    }
}
