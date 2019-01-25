package com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.Provider;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i82716 on 6/29/2017.
 */
public enum ProviderDrillPageDataGridColumn implements IDataGridColumn {
    PROVIDER("PROVNAME","Provider",DataType.STRING),
    PROVIDERID("providerId","Provider Id",DataType.INTEGER),
    TOTAL_PAID("PAIDAMT","Total Paid",DataType.MONEY),
    UNIQUE_MEMBERS("MEMCOUNT","Unique Members",DataType.INTEGER),
    MEMBERSPER1000("MPT","Members per 1000",DataType.DOUBLE),
    PAIDPERMEMBER("PAIDPERMEMBER","Paid per Member",DataType.MONEY),
    PMPM("PMPM","$PMPM",DataType.MONEY);

    private static Logger logger = LoggerFactory.getLogger(ProviderDrillPageDataGridColumn.class);

    private String id;
    private String label;
    private DataType dataType;

    private ProviderDrillPageDataGridColumn(String id, String label, DataType dataType) {
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
        for (ProviderDrillPageDataGridColumn c : ProviderDrillPageDataGridColumn.values()) {
            if (c.getId().equals(id)) return c;
        }
        throw new RuntimeException("No ProviderProfiler Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (ProviderDrillPageDataGridColumn c : ProviderDrillPageDataGridColumn.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label {} ", label);
        return null;
    }
}
