package com.vh.mi.automation.api.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i82716 on 4/25/2017.
 */
public enum PopulationAnalyserDataGridColumn implements IDataGridColumn{
    COHORT("COHORTID","Cohort#",DataType.INTEGER),
    COHORT_DESCRIPTION("COHORTDESC","Cohort Description",DataType.STRING),
    BUSINESS_LEVELS("HIERARCHY","Business Levels",DataType.STRING),
    OWNER("OWNER","Owner",DataType.STRING),
    CREATED_DATE("CREATEDDATE","Created Date",DataType.DATE),
    TYPE("QUERYTYPE","Type",DataType.STRING),;

    private static Logger logger = LoggerFactory.getLogger(PopulationAnalyserDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private PopulationAnalyserDataGridColumn(String id, String label,DataType dataType){
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
        for (PopulationAnalyserDataGridColumn c : PopulationAnalyserDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }
        throw new RuntimeException("No PopulationAnalyser Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (PopulationAnalyserDataGridColumn c : PopulationAnalyserDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }

}
