package com.vh.mi.automation.test.comp.dataGrid;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;
import com.vh.mi.automation.impl.comp.dataGrid.gridValue.GridValueFactory;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by nimanandhar on 9/9/2014.
 */

public abstract class AbstractDataGridTest extends BaseTest {
    protected IDataGrid dataGrid;
    final String[] METACHARACTERS = {"*"};


    abstract protected IDataGrid getDataGrid();

    public void setUp() {
        super.setUp();
        if (!skipLogin()) {
            dataGrid = getDataGrid();
        }
    }

    public String escapeMetaCharacters(String inputString) {
        for (int i = 0; i < METACHARACTERS.length; i++) {
            if (inputString.contains(METACHARACTERS[i])) {
                return inputString.replace(METACHARACTERS[i], "");
            }
        }
        return inputString;
    }


    @Test
    public void dataGridShouldReturnVisibleColumn() {
        Collection<IDataGridColumn> visibleColumns = dataGrid.getColumns();
        assertThat(visibleColumns).isNotEmpty();
    }

    List<IGridValue> getData(IDataGridColumn column) {
        List<IGridValue> gridValues = new ArrayList<>();

        for (String value : dataGrid.getData(column)) {
            value = escapeMetaCharacters(value);
            gridValues.add(GridValueFactory.create(value, column.getDataType()));

        }
        return gridValues;

    }

}


