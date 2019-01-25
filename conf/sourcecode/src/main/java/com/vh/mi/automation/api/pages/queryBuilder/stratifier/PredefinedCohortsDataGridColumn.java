package com.vh.mi.automation.api.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10314 on 7/21/2017.
 */
public enum  PredefinedCohortsDataGridColumn implements IDataGridColumn {

    COHORT_DESCRIPTION("COHORTDESC","Cohort Description",DataType.STRING),
    QUERY_DETAILS("QUERYDETAILS","Query Details",DataType.STRING),
    CREATED_DATE("CREATEDDATE","Created Date",DataType.DATE),;

    private static Logger logger = LoggerFactory.getLogger(PopulationAnalyserDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private PredefinedCohortsDataGridColumn(String id, String label,DataType dataType){
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

    public static IDataGridColumn fromId(String id) {
        for (PredefinedCohortsDataGridColumn c : PredefinedCohortsDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }
        throw new RuntimeException("No PredefinedCohorts Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (PredefinedCohortsDataGridColumn c : PredefinedCohortsDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }

}
