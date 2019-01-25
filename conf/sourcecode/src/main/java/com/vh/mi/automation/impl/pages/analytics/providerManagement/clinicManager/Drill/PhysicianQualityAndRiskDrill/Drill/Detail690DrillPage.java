package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianQualityAndRiskDrill.Drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill.I690QRMDetailDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.xml.soap.Detail;

/**
 * Created by i10359 on 11/28/17.
 */
public class Detail690DrillPage extends AbstractDrillPage implements I690QRMDetailDrillPage{
    private WebElements webElements;


    public Detail690DrillPage(WebDriver webDriver){
        super(webDriver);
        webElements = new WebElements(getDriver());

    }

    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.getText().equals("(690) QRM Detail");
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
    }

    @Override
    public IDataGrid getDataGrid() {
        return null;
    }

    private static class WebElements{

        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;
    }

}
