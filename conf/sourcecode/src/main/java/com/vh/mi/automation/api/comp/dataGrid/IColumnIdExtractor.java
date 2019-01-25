package com.vh.mi.automation.api.comp.dataGrid;

/**
 * Extracts ID of Column from the id attribute
 * of dataGrid WebElements
 * Created by nimanandhar on 12/4/2014.
 */
public interface IColumnIdExtractor {

    /**
     * Parses the id attribute of column header and returns
     * the ID as used in MI xhtml pages. For eg
     * If the id attribute is d2Form:simpleGrid:column_MaleMedCost_header
     * it should parse this and return only MaleMedCost
     * @param idAttribute the attribute of column html header element
     * @return the ID of the column
     */
    String extractColumnIdFromHeader(String idAttribute);


    /**
     * Parses the id attribute of column sort link and
     * returns the ID of the column
     * @param idAttribute the id attribute of sort Link for column
     * @return the ID of the column
     */
    String extractColumnIdFromSortLink(String idAttribute);
}
