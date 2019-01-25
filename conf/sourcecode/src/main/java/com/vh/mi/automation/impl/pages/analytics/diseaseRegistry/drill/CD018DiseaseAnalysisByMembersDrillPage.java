package com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.drill;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.pages.analytics.diseaseRegistry.drill.IDiseaseRegistryCD018DrillPage;
import com.vh.mi.automation.impl.pages.common.AbstractDrillPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by i10359 on 3/14/18.
 */
public class CD018DiseaseAnalysisByMembersDrillPage extends AbstractDrillPage implements IDiseaseRegistryCD018DrillPage{
    private WebElements webElements;
    private CD018DiseaseAnalysisByMembersDrillPageDataGrid dataGrid;


    public CD018DiseaseAnalysisByMembersDrillPage(WebDriver driver){
        super(driver);
        webElements = new WebElements(driver);
        dataGrid = new CD018DiseaseAnalysisByMembersDrillPageDataGrid(driver);

    }

    @Override
    public IDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public String getPageTitle() {
        return webElements.pageTitle.getText();
    }

    @Override
    public String getPageId() {
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
