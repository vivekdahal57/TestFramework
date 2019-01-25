package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.DrillPhysician;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill.ICP110PhysiciansDrillPage;


import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 11/20/17.
 */
public class CP110PhysiciansDrillPage extends AbstractDrillPage implements ICP110PhysiciansDrillPage {
    private CP110PhysiciansDrillPageDataGrid dataGrid;
    private WebElements webElements;


    public CP110PhysiciansDrillPage(WebDriver webDriver){
        super(webDriver);
        dataGrid = new CP110PhysiciansDrillPageDataGrid(webDriver);
        webElements = new WebElements(getDriver());
    }


    @Override
    public boolean isFullyLoaded() {
        return webElements.pageTitle.getText().equals("(CP110) Physician Manager");
    }

    @Override
    public String getPageTitle() {
        return  webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
        return null;
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

    private static class WebElements{

        private WebElements(WebDriver webDriver){
            PageFactory.initElements(webDriver,this);
        }

        @FindBy(id = "pageTitle")
        private WebElement pageTitle;

    }




}
