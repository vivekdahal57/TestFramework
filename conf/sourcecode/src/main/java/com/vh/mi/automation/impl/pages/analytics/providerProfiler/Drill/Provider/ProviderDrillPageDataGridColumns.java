package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Provider;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;

/**
 * Created by i10359 on 1/12/18.
 */
public enum ProviderDrillPageDataGridColumns implements IDataGridColumn {
    PROVIDER("PROVNAME" , "Provider" , DataType.STRING),
    PROVIDER_ID("PROVIDERID" , "Provider Id" , DataType.STRING),
    TOTAL_PAID("PAIDAMT" , "Total Paid" , DataType.MONEY),
    UNIQUE_MEMBERS("MEMCOUNT" , "unique Members" , DataType.INTEGER),
    MEMBERS_PER_K("MPT" , "Members Per k", DataType.DOUBLE),
    PAID_PER_MEMBER("PAIDPERMEMBER" , "Paid Per Member" , DataType.MONEY),
    PMPM("PMPM" ,"PMPM",DataType.MONEY)

  ;

    private String id;
    private String label;
    private DataType dataType;

    private ProviderDrillPageDataGridColumns(String id, String label , DataType dataType){
        this.id = id;
        this.label =  label;
        this.dataType =  dataType;
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

    public static IDataGridColumn  fromId(String id){
        for(ProviderDrillPageDataGridColumns c : ProviderDrillPageDataGridColumns.values()){
            if(c.getId().equalsIgnoreCase(id))
                return c;
        }
        throw new RuntimeException("No  Column found for id " + id);
    }
}
