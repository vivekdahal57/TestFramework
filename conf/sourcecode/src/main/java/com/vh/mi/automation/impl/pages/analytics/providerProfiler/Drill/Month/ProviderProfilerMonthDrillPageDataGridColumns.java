package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Month;


import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 1/11/18.
 */
public enum ProviderProfilerMonthDrillPageDataGridColumns implements IDataGridColumn {
    SPECIALTY("SPECDESC" , "Specialty" , DataType.STRING),
    TIME_PERIOD("ANALYSISTYPE" , "Time Period" , DataType.STRING),
    TOTAL_PAID("PAIDAMT" , "Total Paid" , DataType.MONEY),
    UNIQUE_MEMBERS("MEMCOUNT" , "Unique Members" , DataType.INTEGER),
    MEMBERS_PER_K("MPT" , "Members per 1000" , DataType.DOUBLE),
    PAID_PER_MEMBER("PAIDPERMEMBER" , "Paid per Member" , DataType.MONEY),
    PMPM("PMPM" , "$PMPM" , DataType.MONEY)
   ;

    private static Logger logger = LoggerFactory.getLogger(ProviderProfilerMonthDrillPageDataGridColumns.class);
   private String id;
   private String label;
   private DataType dataType;

    private ProviderProfilerMonthDrillPageDataGridColumns(String id, String label, DataType dataType){
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
        for(ProviderProfilerMonthDrillPageDataGridColumns c : ProviderProfilerMonthDrillPageDataGridColumns.values()){
            if(c.getId().equalsIgnoreCase(id))
                return c;
        }
        throw new RuntimeException("No  Column found for id " + id);
    }

    public static IDataGridColumn fromLabel(String label) {
        for (ProviderProfilerMonthDrillPageDataGridColumns c : ProviderProfilerMonthDrillPageDataGridColumns.values()) {
            if (c.getLabel().equals(label)) return c;
        }

        logger.warn("Mapping not available for label - " + label);

        return null;
    }
}
