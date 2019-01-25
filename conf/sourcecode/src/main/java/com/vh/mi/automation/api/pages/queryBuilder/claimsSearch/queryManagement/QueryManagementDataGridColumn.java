package com.vh.mi.automation.api.pages.queryBuilder.claimsSearch.queryManagement;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.constants.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by i81306 on 5/5/2017.
 */
    public enum QueryManagementDataGridColumn implements IDataGridColumn {
        QUERY("queryid","Query #", DataType.STRING),
        QUERY_NAME("queryName","Query Name",DataType.STRING),
        OWNER("owner","Owner",DataType.STRING),
        QUERY_CREATED_DATE("createdDate","Query Created Date",DataType.STRING),
        EXTRACT_CREATED_DATE("extractCreatedDate","Extract Created Date",DataType.DATE),
        EXTRACT_CYCLE_DATE("extractCycleDate","Extract Cycle Date",DataType.STRING),
        QUERY_STATUS("status","Query Status",DataType.STRING),
        NUMBER_OF_CLAIMS("claimCount","# of Claims",DataType.DATE),
        EXTRACT_EXPIRATION("extractExpDate","Extract Expiration",DataType.STRING);

        private static Logger logger = LoggerFactory.getLogger(com.vh.mi.automation.api.pages.queryBuilder.stratifier.PopulationAnalyserDataGridColumn.class);

        private String id;
        private String label;
        private DataType dataType;

        private QueryManagementDataGridColumn(String id, String label,DataType dataType){
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
            for (QueryManagementDataGridColumn c : QueryManagementDataGridColumn.values()) {
                if (c.getId().equals(id)) return c;
            }
            throw new RuntimeException("No QueryManagement Column found for id " + id);
        }

        public static IDataGridColumn fromLabel(String label) {
            for (QueryManagementDataGridColumn c : QueryManagementDataGridColumn.values()) {
                if (c.getLabel().equals(label)) return c;
            }

            logger.warn("Mapping not available for label {} ", label);
            return null;
        }

    }
