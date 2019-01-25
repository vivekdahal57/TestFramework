package com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.Drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
//import com.vh.mi.automation.api.pages.analytics.claims.Claims01DataGridColumn;
import com.vh.mi.automation.api.pages.analytics.hospitalProfiler.Drill.IHP120AdmissionDrillPage;
import com.vh.mi.automation.impl.comp.dataGrid.customizer.DataGridCustomizer;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponent;
import com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.HospitalProfilesHP100Columns;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 11/13/17.
 */
public class HP120AdmissionDrillPage extends AbstractDrillPage implements IHP120AdmissionDrillPage {
    private HP120AdmissionDrillPageDataGrid dataGrid;
    private WebElements webElements;
    private IPaginationComponent paginationComponent;

    public HP120AdmissionDrillPage(WebDriver webDriver){
        super(webDriver);
        webElements = new WebElements(getDriver());
        dataGrid=new HP120AdmissionDrillPageDataGrid(webDriver);
        paginationComponent= PaginationComponent.newD2FormPaginationComponent(getDriver());
    }

    @Override
    public boolean isFullyLoaded(){
        return webElements.pagetitle.getText().equalsIgnoreCase("(HP120) Admissions");
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
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
    public IPaginationComponent getPaginationComponent() {
        return paginationComponent;
    }

    @Override
    public boolean isDataGridCustomizable() {
        return true;
    }

    @Override
    public IDataGridCustomizer getDataGridCustomizer() {
        if (isDataGridCustomizable()) {
            webElements.customizeLinkElement.click();
            return new DataGridCustomizer(getDriver(), new IColumnSpec() {
                @Override
                public IDataGridColumn getColumnByLabel(String colName) {
                    return HP120AdmissionsDrillPageColumns.fromLabel(colName);
                }
            });
        }

        return null;
    }
     static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pagetitle;

         @FindBy(id="d2Form:sendToCsv")
         private WebElement sendBtn;

         @FindBy(linkText = "Customize")
         private WebElement customizeLinkElement;
     }

}
