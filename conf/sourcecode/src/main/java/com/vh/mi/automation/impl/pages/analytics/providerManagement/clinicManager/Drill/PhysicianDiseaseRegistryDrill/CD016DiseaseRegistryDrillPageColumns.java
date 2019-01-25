package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianDiseaseRegistryDrill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 11/23/17.
 */
public enum CD016DiseaseRegistryDrillPageColumns implements IDataGridColumn {
    DISEASES("CHRONICCATDESC" , "Diseases" ,DataType.STRING),
    NO_OF_MEMBERS_P1("MEMBER_P1", "# of Members P1", DataType.INTEGER),
    NO_OF_MEMBERS_P2("MEMBER_P2", "# of Members P2", DataType.INTEGER),
    MEMBERS_PER_K_P1("MEMBERSPERK_P1", "Members per 1000 P1", DataType.DOUBLE),
    MEMBERS_PER_K_P2("MEMBERSPERK_P2", "Members per 1000 P2", DataType.DOUBLE),
    TOTAL_PAID_P1("TotPaid_P1", "Total Paid P1", DataType.MONEY),
    TOTAL_PAID_P2("TotPaid_P2", "Total Paid p2", DataType.MONEY),
    PMPY_P1("PMPY_P1", "PMPY P1",DataType.MONEY),
    PMPY_P2("PMPY_P2","PMPY P2",DataType.MONEY),
    OFFICE_VISITS_PER_K_P1("OVPerK_P1" ,"Office Per Visit Per k P1",DataType.DOUBLE),
    OFFICE_VISITS_PER_K_P2("OVPerK_P2","Office Per Visit Per k P2",DataType.DOUBLE),
    ER_VISITS_PER_K_P1("ERVPerK_P1","ER Visits per k P1",DataType.DOUBLE),
    ER_VISITS_PER_K_P2("ERVPerK_P2", "ER Visits per k P2", DataType.DOUBLE),
    ADMISSIONS_PER_K_P1("AdmissionPerK_P1","Admissions per k P1",DataType.DOUBLE),
    ADMISSIONS_PER_K_P2("AdmissionPerK_P1","Admissions per k P2",DataType.DOUBLE);

    private static Logger logger = LoggerFactory.getLogger(CD016DiseaseRegistryDrillPageColumns.class);

    private  String id;
    private String label;
    private DataType dataType;

    private CD016DiseaseRegistryDrillPageColumns(String id, String label, DataType dataType){
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
        for(CD016DiseaseRegistryDrillPageColumns c : CD016DiseaseRegistryDrillPageColumns.values()){
            if(c.getId().equals(id))
                return c;
        }
        logger.warn("Mapping not available for ID - " + id);
        return null;
    }

    public static IDataGridColumn fromLabel(String label){
        for(CD016DiseaseRegistryDrillPageColumns c : CD016DiseaseRegistryDrillPageColumns.values()){
            if(c.getLabel().equals(label))
                return c;
        }
        logger.warn("Mapping not available for label - " + label);

        return null;
    }
}
