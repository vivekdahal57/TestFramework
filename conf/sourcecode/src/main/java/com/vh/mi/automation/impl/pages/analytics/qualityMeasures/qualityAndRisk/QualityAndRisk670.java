package com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.qrm.IFavoriteQRM;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.analytics.qualityMeasures.qualityAndRisk.IQualityAndRisk;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.utils.FileDownloadUtils;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.bl.newimpl.BusinessLevelsComponent;
import com.vh.mi.automation.impl.comp.dataGrid.customizer.DataGridCustomizer;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.comp.qrm.FavoriteQRM;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

/**
 * @author nimanandhar
 */
public class QualityAndRisk670 extends AbstractLandingPage implements IQualityAndRisk {
    private static final String MODULE_ID = "3";
    private final IBusinessLevelsComponent businessLevel;
    private final QualityAndRiskDataGrid dataGrid;
    private final IFavoriteQRM favoriteQRM;
    private WebElements webElements;
    private LoadingPopup loading;
    private String loadingPopUpElement = "_Loading1";

    public QualityAndRisk670(WebDriver driver) {
        super(driver, MODULE_ID);
        businessLevel = new BusinessLevelsComponent(driver);
        dataGrid = new QualityAndRiskDataGrid(driver);
        favoriteQRM = new FavoriteQRM(driver);
        webElements = new WebElements(driver);
    }

    @Override
    public IBusinessLevelsComponent getBusinessLevel() {
        return businessLevel;
    }

    @Override
    public QualityAndRiskDataGrid getDataGrid() {
        return dataGrid;
    }

    @Override
    public IFavoriteQRM getFavoriteQRM() {
        return favoriteQRM;
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
                    return QualityAndRiskDataGrid.Columns.fromLabel(colName);
                }
            });
        }

        return null;
    }

    public IDrillPage drillDownToPage(String page) {
        IDrillPage drillPage = dataGrid.getRows().get(0).doDrillByOnSameWindow(page);
        drillPage.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return drillPage;
    }

    @Override
    public void createListWithSelectedMember(int topN,String memListName){
        dataGrid.selectTopNCheckBoxInDataGrid(topN);
        SeleniumUtils.hoverOnElement(webElements.hoverElement,getDriver());
        WaitUtils.waitUntilEnabled(getDriver(),webElements.saveMemListBtn);
        webElements.addMemListLink.click();
        new LoadingPopup(getDriver(),loadingPopUpElement).waitTillDisappears();
        saveMemberList(memListName);
    }

    private void saveMemberList(String memListName){
        webElements.memListInputBox.sendKeys(memListName);
        webElements.saveMemListBtn.click();
        new LoadingPopup(getDriver(),loadingPopUpElement).waitTillDisappears();
        WaitUtils.waitUntilDisplayed(getDriver(), webElements.popUpStatusMessage, TimeOuts.TEN_SECONDS);
    }

    @Override
    public String getMemberListSuccessfullyCreatedStatus(){
        return webElements.popUpStatusMessage.getText();
    }

    @Override
    public boolean sendToExcelandValidate(String excelFileName, ExecutionContext context) throws IOException {
        if(!FileDownloadUtils.validateDownloadedFile(excelFileName,context,TimeOuts.THIRTY_SECONDS)){
            throw new AutomationException("Could not export to excel "+ excelFileName);
        }
        return true;
    }

    public static class WebElements{
        public WebElements(WebDriver driver){
            PageFactory.initElements(driver,this);
        }

        @FindBy(id="d2Form:simpleGrid:selectAllIcon_addMemberList")
        WebElement addMemListLink;

        @FindBy(id = "selectAllIcon_select_drillMenu_drillImage")
        WebElement hoverElement;

        @FindBy(id = "d2Form1:memListName")
        WebElement memListInputBox;

        @FindBy(xpath = "//form[@id='d2Form1']//input[@value='Save']")
        WebElement saveMemListBtn;

        @FindBy(id = "ani_message")
        WebElement popUpStatusMessage;

        @FindBy(linkText = "Customize")
         WebElement customizeLinkElement;

    }

}
