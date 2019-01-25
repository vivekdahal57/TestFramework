package com.vh.mi.automation.impl.pages.analytics.individuals.drill;

import com.sun.java.util.jar.pack.*;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.exceptions.NotImplementedException;
import com.vh.mi.automation.api.features.IHaveDataGrid;
import com.vh.mi.automation.api.pages.analytics.individuals.drill.IIndividualDashboardIndividualClaimDetails;
import com.vh.mi.automation.impl.comp.dataGrid.customizer.Indv301DataGridCustomizer;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.analytics.individuals.AbstractIndividualDashboard;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301Columns;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301DataGrid;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.vh.mi.automation.impl.pages.analytics.individuals.IndClaimDetailsColumns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author nimanandhar
 */
public class IndividualDashboardIndividualClaimDetails extends AbstractIndividualDashboard implements IIndividualDashboardIndividualClaimDetails, IHaveDataGrid {

    private final WebElements webElements;
    private IndvClaimDetailsDataGrid dataGrid;
    private LoadingPopup loading;
    private static final Logger logger = LoggerFactory.getLogger(IndividualDashboardIndividualClaimDetails.class);

    public IndividualDashboardIndividualClaimDetails(WebDriver driver) {
        super(driver);
        webElements = new WebElements(driver);
        dataGrid =  new IndvClaimDetailsDataGrid(driver);
        loading = new LoadingPopup(driver);
    }

    public enum Component {
        SERVICE_PERIOD("Service_Period_menu");

        private Component(String id){
            this.id = id;
        }
        private   String id;
        public  String getId() {
            return id;
        }
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        throw new NotImplementedException();
    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public boolean isDataGridCustomizable() {
        return true;
    }

    @Override
    public IDataGridCustomizer getDataGridCustomizer() {
        if (isDataGridCustomizable()) {
            SeleniumUtils.click(webElements.customizeLinkElement, getDriver());
            return new Indv301DataGridCustomizer(getDriver(), new IColumnSpec() {
                @Override
                public IDataGridColumn getColumnByLabel(String colName) {

                    return IndClaimDetailsColumns.fromLabel(colName);
                }
            });
        }
        return null;
    }

    @Override
    public void waitTillLoadingDisappears() {
        loading.waitTillDisappears();
    }

    public String getRenameDiagnosistext() {
        return webElements.RenameDiagnosisLabel.getText();
    }

    public String getRenamePOSFDBGrouperLabel() {

        return webElements.RenamePOSFDBGrouperLabel.getText();
    }

    public void clickonNext() {

        SeleniumUtils.click(webElements.clickOnNext);
    }

    public int getRecordno() {
        int CurrentRecordno = Integer.parseInt(webElements.currentRecordnumber.getText().substring(0, 1));
        return CurrentRecordno;
    }

    @Override
    public boolean isDrillPageValid() {
        logger.info("Drill page is valid if data is present in the datagrid");
        if (getDataGrid().getRows().size() > 0) {
            logger.info("Since Datagrid contains values , Drill Page is Valid");
            return true;
        }else {
            logger.info("No Data is present in the datagrid. Thus drill page is invalid");
            return false;
        }
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {

            PageFactory.initElements(webDriver, this);
        }

        @FindBy(linkText = "Customize")
        private WebElement customizeLinkElement;

        @FindBy(id = "pageTitle")
        WebElement pageTitle;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:column_DIAG___header\"]/table/tbody/tr/td/span")
        WebElement RenameDiagnosisLabel;

        @FindBy(id = "d2Form:simpleGrid:column_POSRXDESC_header_sortCommandLink")
        WebElement RenamePOSFDBGrouperLabel;

        @FindBy(className = "d2-nextBtn")
        WebElement clickOnNext;

        @FindBy(className = "d2-pageNum")
        WebElement currentRecordnumber;

    }
}
