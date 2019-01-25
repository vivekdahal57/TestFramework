package com.vh.mi.automation.impl.pages.queryBuilder.mvca.expert;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.pages.queryBuilder.mvca.expert.MVCAExpertDataGridColumn;
import com.vh.mi.automation.impl.comp.dataGrid.AbstractDataGrid;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i81306 on 6/26/2017.
 */
public class EMVCADatagrid extends AbstractDataGrid {

    public EMVCADatagrid(WebDriver driver) {
        super(driver);
        columnIdExtractor = new MVCAExpert301EColumnIdExtractor();
    }

    @Override
    protected IDataGridColumn getColumn(String id) {
        return MVCAExpertDataGridColumn.fromId(id);
    }
}
