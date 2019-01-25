package com.vh.mi.automation.impl.comp.dataGrid;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridHeaderParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

import static com.vh.mi.automation.impl.selenium.SeleniumUtils.elementExists;

/**
 * Created by nimanandhar on 12/3/2014.
 */
public class DefaultDataGridHeaderParser implements IDataGridHeaderParser {
    private static final String THEAD = "thead";
    private static final String COL_SPAN = "colspan";
    private static final String ROW_SPAN = "rowspan";
    private static final int START_ROW = 1;

    private RowSortedTable<Integer, Integer, String> headerTable = TreeBasedTable.create();


    @Override
    public Map<Integer, String> parse(WebElement headerElement) {
        Preconditions.checkArgument(headerElement.getTagName().equals(THEAD));

        headerTable.clear();
        List<WebElement> tableRowElements = headerElement.findElements(By.xpath("./tr"));
        int numberOfRows = tableRowElements.size();

        for (int rowNumber = START_ROW; rowNumber <= numberOfRows; rowNumber++) {
            WebElement tableRowElement = tableRowElements.get(rowNumber - 1);

            if (isRowEmpty(tableRowElement)) //some tr elements contains no data , they can be skipped
                continue;

            for (WebElement thElement : tableRowElement.findElements(By.tagName("th"))) {
                int rowSpan = getRowSpan(thElement);
                int colSpan = getColSpan(thElement);
                String id = thElement.getAttribute("id");

                int colNumber = getStartColNumber(rowNumber);
                for (int r = rowNumber; r < rowNumber + rowSpan; r++) {
                    for (int c = colNumber; c < colNumber + colSpan; c++) {
                        headerTable.put(r, c, id);
                    }
                }
            }
        }

        int lastRowIndex = headerTable.rowKeySet().last();
        return headerTable.row(lastRowIndex);
    }


    private int getColSpan(WebElement thElement) {
        String colSpan = thElement.getAttribute(COL_SPAN);
        return colSpan == null ? 1 : Integer.valueOf(colSpan);
    }

    private int getRowSpan(WebElement thElement) {
        String rowSpan = thElement.getAttribute(ROW_SPAN);
        return rowSpan == null ? 1 : Integer.valueOf(rowSpan);
    }

    private boolean isRowEmpty(WebElement tableRowElement) {
        return !elementExists(tableRowElement, ".//th");
    }

    private int getStartColNumber(int rowNumber) {
        Map<Integer, String> row = headerTable.row(rowNumber);
        for (int i = 1; i <= row.size(); i++) {
            if (row.get(i) == null) {
                return i;
            }
        }
        return row.size() + 1;
    }

}
