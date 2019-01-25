package com.vh.mi.automation.impl.pages.analytics.snfProfiler.Drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.pages.analytics.snfProfiler.Drill.ISP120SNFAdmissionsDrillPage;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponent;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 11/16/17.
 */
public class SP120SNFAdmissionsDrillPage extends AbstractDrillPage implements ISP120SNFAdmissionsDrillPage {
    private SP120SNFAdmissionsDrillPageDataGrid dataGrid;
    private IPaginationComponent paginationComponent;
    private WebElements webElements;

    public SP120SNFAdmissionsDrillPage(WebDriver webDriver) {
        super(webDriver);
        dataGrid = new SP120SNFAdmissionsDrillPageDataGrid(getDriver());
        paginationComponent = PaginationComponent.newD2FormPaginationComponent(getDriver());
        webElements = new WebElements(getDriver());

    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pagetitle.getText().equalsIgnoreCase("(SP120) SNF Admissions");
    }

    @Override
    public IPaginationComponent getPaginationComponent() {
        return paginationComponent;
    }

    @Override
    public String getPageTitle() {
        return webElements.pagetitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }


    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pagetitle;
    }
}
