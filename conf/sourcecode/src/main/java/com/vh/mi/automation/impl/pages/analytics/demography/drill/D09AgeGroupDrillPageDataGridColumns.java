package com.vh.mi.automation.impl.pages.analytics.demography.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 2/27/18.
 */
public enum D09AgeGroupDrillPageDataGridColumns implements IDataGridColumn {
    AGE_SUB_GROUPS("ageSubGrp" , "Age Sub Groups" , DataType.STRING),
    MALE_MEDICAL_PAID("MaleMedAmt" , "Male Medical Paid" , DataType.MONEY),
    MALE_RX_PAID("MaleRxAmt" , "Male Rx Paid" ,DataType.MONEY),
    MALE_PERCENT_OF_TOTAL_MM("MaleMMPOT" , "Percent Of Total MM" , DataType.PERCENTAGE),
    MALE_PERCENT_OF_TOTAL_PAID("MaleTotPOT" , "Percent Of Total Paid", DataType.PERCENTAGE ),
    FEMALE_MEDICAL_PAID("FemMedAmt" , "Female Medical Paid" , DataType.MONEY),
    FEMALE_RX_PAID("FemRxAmt" , "Female Rx Paid", DataType.MONEY),
    FEMALE_PERCENT_OF_TOTAL_MM("FemMMPOT" , "Percent Of Total MM" , DataType.PERCENTAGE),
    FEMALE_PERCENT_OF_TOTAL_PAID("FemTotPOT" , "Female Percent Of Total Paid", DataType.PERCENTAGE),
    TOTAL_MM("TotMM" , "Total MM" , DataType.INTEGER),
    TOTAL_MEDICAL_PAID("TotMedCost" , "Total Medical Paid" , DataType.MONEY),
    TOTAL_RX_PAID("TotRxCost" , "Total Rx Paid" , DataType.MONEY),
    TOTAL_MEDICALPLUS_RX_PAID("AllPaid" , "Total Medical Plus Rx Paid" , DataType.MONEY)
    ;



    private static Logger logger = LoggerFactory.getLogger(D09AgeGroupDrillPageDataGridColumns.class);
    private String id;
    private String label;
    private DataType dataType;

    private D09AgeGroupDrillPageDataGridColumns(String id, String label, DataType dataType){
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
        for(D09AgeGroupDrillPageDataGridColumns c : D09AgeGroupDrillPageDataGridColumns.values()){
            if(c.getId().equalsIgnoreCase(id))
                return c;
        }

        logger.warn("Mapping not available for label - " + id);

        return null;
    }
}
