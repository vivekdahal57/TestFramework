package com.vh.mi.automation.api.pages.favorites.myJobs;


import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by msedhain on 02/16/2017.
 */
public enum MyJobsDataGridColumn implements IDataGridColumn {
    JOB_ID("JOBID", "Job ID", DataType.STRING),
    JOB_TITLE("JOBTITLE", "Job Title", DataType.STRING),
    JOB_REQDATE("JOBREQDATE", "Job Request Date", DataType.DATE),
    JOB_COMPDATE("JOBCOMPDATE", "Job Completion Date", DataType.DATE),
    JOB_SOURCE("JOBSOURCE", "Job Source", DataType.STRING),
    RESULT_FORMAT("RESULTFORMAT", "Result Format", DataType.STRING),
    JOB_STATUS("JOBSTATUS", "Job Status", DataType.STRING),
    READ_FLAG("READFLAG", "Read Flag", DataType.STRING),
    EXPIRATION("EXPIRATION", "Expiration", DataType.STRING);

    private static Logger logger = LoggerFactory.getLogger(MyJobsDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private MyJobsDataGridColumn(String id, String label, DataType dataType) {
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
        throw new RuntimeException("No MyJob Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (MyJobsDataGridColumn c : MyJobsDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }
}
