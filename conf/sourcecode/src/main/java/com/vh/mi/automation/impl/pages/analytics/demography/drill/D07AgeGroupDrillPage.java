package com.vh.mi.automation.impl.pages.analytics.demography.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.analytics.demography.drill.D07AgeGroupDataGridColumn;
import com.vh.mi.automation.api.pages.analytics.demography.drill.ID07AgeGroupDrillPage;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.customizer.DataGridCustomizer;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponent;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class D07AgeGroupDrillPage extends AbstractDrillPage implements ID07AgeGroupDrillPage{

    private WebElements webElements;
    private D07AgeGroupDataGrid dataGrid;
    private IPaginationComponent paginationComponent;

    public D07AgeGroupDrillPage(WebDriver driver){
        super(driver);
        webElements = new WebElements(driver);
        dataGrid = new D07AgeGroupDataGrid(driver);
        paginationComponent = PaginationComponent.newD2FormPaginationComponent(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return "(D07) Total Paid by Age Group".equalsIgnoreCase(webElements.pageTitle.getText());
    }



    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }



    @Override
    public IDrillPage drillDownToPage(String page) {
        IDrillPage drillPage = dataGrid.getRows().get(0).doDrillByOnSameWindow(page);
        drillPage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return drillPage;
    }

    @Override
    public IDataGridCustomizer getDataGridCustomizer() {
        if (isDataGridCustomizable()) {
            webElements.customizeLinkElement.click();
            return new DataGridCustomizer(getDriver(), new IColumnSpec() {
                @Override
                public IDataGridColumn getColumnByLabel(String colName) {
                    return D07AgeGroupDataGridColumn.fromLabel(colName);
                }
            });
        }

        return null;
    }

    @Override
    public boolean isDataGridCustomizable() {
        return true;
    }


    @Override
    public IPaginationComponent getPaginationComponent() {
        return paginationComponent;
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
         WebElement pageTitle;


        @FindBy(linkText = "Customize")
        private WebElement customizeLinkElement;
    }
}
