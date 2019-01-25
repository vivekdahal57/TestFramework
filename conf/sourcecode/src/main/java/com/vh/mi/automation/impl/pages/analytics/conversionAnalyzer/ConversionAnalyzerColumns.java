package com.vh.mi.automation.impl.pages.analytics.conversionAnalyzer;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nimanandhar on 10/29/2014.
 */
public enum ConversionAnalyzerColumns implements IDataGridColumn {
    CURBRAND("CURBRAND", "Current Drug", DataType.STRING),
    ALTBRAND("ALTBRAND", "Alternative Drug", DataType.STRING),
    TOTALLOWEDAMT("TOTALLOWEDAMT", "Total Allowed", DataType.MONEY),
    TOTPAIDAMT("TOTPAIDAMT", "Total Paid", DataType.MONEY),
    POTENTIALSAVINGAMT("POTENTIALSAVINGAMT", "Potential Savings*", DataType.MONEY),;


    private static Logger logger = LoggerFactory.getLogger(ConversionAnalyzerColumns.class);

    private String id;
    private String label;
    private DataType dataType;

    private ConversionAnalyzerColumns(String id, String label, DataType dataType) {
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
        for (ConversionAnalyzerColumns c : ConversionAnalyzerColumns.values()) {
            if (c.getId().equals(id)) return c;
        }
        throw new RuntimeException("No ConversionAnalyzerColumn found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (ConversionAnalyzerColumns c : ConversionAnalyzerColumns.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }
}