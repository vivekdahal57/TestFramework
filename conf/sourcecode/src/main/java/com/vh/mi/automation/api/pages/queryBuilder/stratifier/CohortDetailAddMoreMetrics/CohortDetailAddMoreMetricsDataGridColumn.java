package com.vh.mi.automation.api.pages.queryBuilder.stratifier.CohortDetailAddMoreMetrics;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import com.vh.mi.automation.api.pages.favorites.myJobs.MyJobsDataGridColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10314 on 7/13/2017.
 */
public enum  CohortDetailAddMoreMetricsDataGridColumn implements IDataGridColumn {
    ISSUE_NAME("ISSUENAME", "Issue Name", DataType.STRING),
    QUESTION("QUESTION", "Question", DataType.STRING),
    PANEL_WITH_ISSUE("PANELWITHISSUE", "#Panel With Issue", DataType.DATE),
    PANEL_MEETING_CRITERIA("PANELMEETINGCRITERIA", "Members with Gap/Risk", DataType.DATE),
    PANEL_NOT_MEETING_CRITERIA("PANELNOTMEETINGCRITERIA", "Members without Gap/Risk", DataType.STRING);

    private static Logger logger = LoggerFactory.getLogger(CohortDetailAddMoreMetricsDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private CohortDetailAddMoreMetricsDataGridColumn(String id, String label, DataType dataType) {
        this.id = id;
        this.label = label;
        this.dataType = dataType;
    }

    @Override
    public DataType getDataType() {
        return this.dataType;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public static IDataGridColumn fromId(String id) {
        for (MyJobsDataGridColumn c : MyJobsDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }
        throw new RuntimeException("No More Metrics Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (MyJobsDataGridColumn c : MyJobsDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }
}
