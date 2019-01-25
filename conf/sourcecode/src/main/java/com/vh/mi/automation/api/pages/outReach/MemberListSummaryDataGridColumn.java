package com.vh.mi.automation.api.pages.outReach;


import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by msedhain on 02/16/2017.
 */
public enum MemberListSummaryDataGridColumn implements IDataGridColumn {
    NAME("MemberList", "Name", DataType.STRING),
    CREATED_BY("MemName", "Created By", DataType.STRING),
    ASSOCIATED_WITH_CONDITION("HASCONDITION", "Associated With Condition", DataType.STRING),
    CONDITION("CONDITION", "Condition", DataType.STRING),
    LIST_SCOPE("ScopeControl", "List Scope", DataType.STRING),
    DATE_LASE_MODIFIED("Modified_Date", "Date Last Modified", DataType.DATE);

    private static Logger logger = LoggerFactory.getLogger(MemberListSummaryDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private MemberListSummaryDataGridColumn(String id, String label, DataType dataType) {
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
        for (MemberListSummaryDataGridColumn c : MemberListSummaryDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }
        throw new RuntimeException("No memberListSummary Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (MemberListSummaryDataGridColumn c : MemberListSummaryDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }
}
