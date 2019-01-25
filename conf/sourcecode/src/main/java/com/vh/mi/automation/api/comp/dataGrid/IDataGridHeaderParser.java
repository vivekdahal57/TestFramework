package com.vh.mi.automation.api.comp.dataGrid;

import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * Created by nimanandhar on 12/4/2014.
 */
public interface IDataGridHeaderParser {

    /**
     * Parses the thead element of a data grid element and returns a
     * map of index (starting at 1) and the <b>id attribute</b>
     * of the column at that position.
     * Note the id is the id attribute of the html element, and <b>not
     * the ID of the columns as used in MI</b>
     * <p>
     *<pre>
     * ---------------------------------------------------------------
     *           |              |               HeaderA
     * ---------------------------------------------------------------
     *           |              |      HeaderB       |       HeaderC
     * ---------------------------------------------------------------
     * Column1   |   Column2    |      Column3       |       Column4
     * ---------------------------------------------------------------
     * </pre>
     * </p>
     * <p>
     *     The method will not return all the headers,So in the above
     *     table assuming the id of columns are the same  as the names,
     *     this method should return the following map
     *     <pre>
     *         1 -> Column1
     *         2 -> Column2
     *         3 -> Column3
     *         4 -> Column4
     *     </pre>
     * </p>
     * @param headerElement thead Element of the dataGrid
     * @return A map of index and id attribute of the column at that index
     */
    Map<Integer, String> parse(WebElement headerElement);

}
