package com.vh.mi.automation.api.pages.analytics.expenseDistribution;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nimanandhar on 9/9/2014.
 */
public enum ExpenseDistributionDataGridColumn implements IDataGridColumn {
    MEMBER_DISTRIBUTION("MemberDist", "Member Distribution", DataType.STRING);
    /*
    NO_OF_MEMBERS( DataType.INTEGER),
    TOTAL_COST_COHORT( DataType.MONEY),
    CUMULATIVE_MEMBERS( DataType.INTEGER),
    CUMULATIVE_TOTAL_COSTS( DataType.MONEY),
    AVG_COST_PER_MEMBER( DataType.MONEY),
    COST_DISTRIBUTION_ACTUAL( DataType.STRING),
    COST_DISTRIBUTION_NORM_TEST( DataType.STRING);
*/
    private static Logger logger = LoggerFactory.getLogger(ExpenseDistributionDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private ExpenseDistributionDataGridColumn(String id, String label, DataType dataType) {
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
        for (ExpenseDistributionDataGridColumn c : ExpenseDistributionDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }

        logger.warn("Mapping not available for ID - " + id);

        return null;
    }

    public static IDataGridColumn fromLabel(String label) {
        for (ExpenseDistributionDataGridColumn c : ExpenseDistributionDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }
}
