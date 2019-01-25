package com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i10359 on 3/13/18.
 */
public enum ComorbidityCD108DrillPageDataGridColumns implements IDataGridColumn {
        COMORBIDITY("COMORBIDDISEASE" , "Comorbidity", DataType.STRING ),
        NO_OF_MEMBERS_TOTAL("MEMCOUNTTOTAL" , "# of Members Total", DataType.INTEGER),
        NO_OF_MEMBERS_CURRENT("MEMCOUNTCURRENT" , "# of Members  Current" , DataType.INTEGER),
        MEMBERS_PER_K("MEMBERPER1000" , "Members per 1000" , DataType.DOUBLE),
        OFFICE_VISIT_PER_K("OFFICEVISITPER1000" , "Office Visit per 1000", DataType.DOUBLE),
        ER_VISIT_PER_k("ERVISITPER1000" , "ER Visit  per 1000", DataType.DOUBLE),
        ADMISSIONS_PER_K("ADMITPER1000" , "Admissions Per 1000" , DataType.DOUBLE),
        SNF_ADMISSIONS_PER_K("SNFADMPER1000", "SNF Admissions per 1000", DataType.DOUBLE),
        PMPY("PMPY" , "PMPY" , DataType.MONEY)
    ;
    private static Logger logger = LoggerFactory.getLogger(ComorbidityCD108DrillPageDataGridColumns.class);

        private String id;
        private String label;
        private DataType dataType;

        ComorbidityCD108DrillPageDataGridColumns(String id, String label, DataType dataType){
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
            for(ComorbidityCD108DrillPageDataGridColumns c : ComorbidityCD108DrillPageDataGridColumns.values()){
                if(c.getId().equals(id))
                    return c;
            }
            logger.warn("Mapping not available for Id- "+id);
            return null;
    }
}
