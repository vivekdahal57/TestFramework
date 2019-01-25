package com.vh.mi.automation.impl.comp.favqm;

import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.comp.pagination.IPaginationComponent;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponent;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.vh.mi.automation.impl.comp.favqm.FavQmUtils.getSelectedQRM;
import static com.vh.mi.automation.impl.comp.favqm.FavQmUtils.getSelectedQualityMeasures;

/**
 * Created by i82298 on 12/9/2016.
 */
public class FavouriteQualityMeasuresPopUp extends AbstractWebComponent {

    public static final String QM_TYPE_DROPDOWN_X_PATH = "./*[@id='d2FormQmSelect:_qmSelectorMeasureType_header']//td/div/div[@class='d2-tglPanel-mark_1']";
    public static final String FAV_QM_QROPDOWN_BODY_ID = "d2FormQmSelect:_qmSelectorMeasureType_body";
    WebElements webElements;
    IPaginationComponent paginationComponent;

    public List<String> getSelectedQms() {
        return selectedQms;
    }

    public List<String> selectedQualityMeasures(){
        return selectedQualityMeasures;
    }

    public List<String> getSelectedHedis() {
            return selectedHedis;
    }

    List<String> selectedQms = new ArrayList<>();
    List<String> selectedHedis = new ArrayList<>();
    List<String> selectedQualityMeasures = new ArrayList<>();

    FavQualityMeasuresDataGrid favQualityMeasuresDataGrid;

    public FavouriteQualityMeasuresPopUp(WebDriver driver) {
        super(driver);
        favQualityMeasuresDataGrid = new FavQualityMeasuresDataGrid(
                getDriver());
        paginationComponent = PaginationComponent
                .newD2FormPaginationComponent(getDriver());
        webElements = new WebElements(getDriver());
    }

    @Override
    public boolean isFullyLoaded() {
        return false;
    }

    public FavQualityMeasuresDataGrid getDataGrid() {
        return favQualityMeasuresDataGrid;
    }

    private FavouriteQualityMeasuresPopUp enterListName(String name) {
        checkArgument(!isNullOrEmpty(name));
        webElements.listName.clear();
        webElements.listName.sendKeys(name);
        return this;
    }

    private FavouriteQualityMeasuresPopUp makeListPublic(boolean isPublic) {
        if (isPublic) {
            if (!webElements.publicCheckBox.isSelected()) {
                webElements.publicCheckBox.click();
            }
        } else {
            if (webElements.publicCheckBox.isSelected()) {
                webElements.publicCheckBox.click();
            }
        }

        return this;
    }

    public FavouriteQualityMeasuresPopUp makeListDefault(boolean isDefault) {
        if (isDefault) {
            if (!webElements.defaultListCheckBox.isSelected()) {
                webElements.defaultListCheckBox.click();
            }
        } else {
            if (webElements.defaultListCheckBox.isSelected()) {
                webElements.defaultListCheckBox.click();
            }
        }
        return this;
    }

    public FavouriteQualityMeasuresPopUp selectQualityMeasures(
            QualityMeasuresType qmType, Integer[] dataGridRowIndex) {
            doChangeQualityMeasuresType(qmType);
            for (Integer rowIndex : dataGridRowIndex) {
                IDataGridRow selectedRow = favQualityMeasuresDataGrid
                        .doSelectRow(qmType, rowIndex);
                if (qmType == QualityMeasuresType.QRM) {
                    selectedQms.add(getSelectedQualityMeasures(selectedRow));
                    selectedQualityMeasures.add(getSelectedQualityMeasures(selectedRow));
                } else if (qmType == QualityMeasuresType.HEDIS) {
                    selectedHedis.add(FavQmUtils.getSelectedHedis(selectedRow));
                }
            }
        return this;
    }



    public void save(String listName, ListScope scope, boolean isDefault) {
        enterListName(listName);
        setListScope(scope);
        makeListDefault(isDefault);
        webElements.saveButton.click();
        new LoadingPopup(getDriver(), "_qmSelectLoading").waitTillDisappears();
        WaitUtils.waitUntilDisplayed(getDriver(),webElements.statusMessage, TimeOuts.TEN_SECONDS);
    }



    private void setListScope(ListScope scope) {
        if (scope == ListScope.PUBLIC) {
            makeListPublic(true);
        } else if (scope == ListScope.PRIVATE) {
            makeListPublic(false);
        } else {
            throw new IllegalArgumentException(
                    "Unknown List Scope [" + scope.toString() + "]");
        }
    }

    public boolean isSaveErrorPopUpShown() {
        WebElement errorPopUpTitle = webElements.saveListErrorPopUp
                .findElement(By.id("_qmSaveErrorHeader"));
        return errorPopUpTitle.isDisplayed();
    }

    public void doChangeQualityMeasuresType(QualityMeasuresType newType) {
        QualityMeasuresType currentType = getCurrentlySelectedQualityMeasuresType();
        if (currentType == newType) {
            return;
        } else {
            displayQmTypeDropDownMenu();
            webElements.favQmDropDownMenu.findElement(
                    By.linkText(QualityMeasuresType.getQmTypeText(newType)))
                    .click();
            new LoadingPopup(getDriver(), "_qmSelectLoading")
                    .waitTillDisappears();
        }
    }

    private void displayQmTypeDropDownMenu() {
        webElements.favQualityMeasuresTypeDropDown.findElement(By.xpath(
                QM_TYPE_DROPDOWN_X_PATH))
                .click();
    }

    public QualityMeasuresType getCurrentlySelectedQualityMeasuresType() {
        String selectedType = webElements.favQualityMeasuresTypeDropDown
                .findElement(
                        By.id(FAV_QM_QROPDOWN_BODY_ID))
                .getText();
        return QualityMeasuresType.getName(selectedType);
    }

    public void clickOnErrorPopUpOkBtn() {
        webElements.saveListErrorPopUp.findElement(By.id("reset_ok"));
    }

    public void cancel() {
        webElements.cancelButton.clear();
    }

    public String getQualityMeasuresSavedStatusMessage(){
        String message = webElements.statusMessage.getText();
        WaitUtils.waitUntilDisappear(getDriver(), webElements.statusMessage, TimeOuts.THIRTY_SECONDS );
        return message;
    }

    public String getExpectedMessageForQualityMeasuresSaved(String qualityMeasureName){
        return "Quality Measures List " + qualityMeasureName + " is saved successfully.";
    }


    private static class WebElements {

        public WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(id = "d2FormQmSelect:savedQrmListName")
        WebElement listName;

        @FindBy(id = "d2FormQmSelect:checkBox")
        WebElement publicCheckBox;

        @FindBy(id = "d2FormQmSelect:defaultList")
        WebElement defaultListCheckBox;

        @FindBy(id = "d2FormQmSelect:_qmSelectPanelReset")
        WebElement saveButton;

        @FindBy(id = "d2FormQmSelect:_qmSelectPanelReset_1")
        WebElement cancelButton;

        @FindBy(id = "d2FormQmSelect:_qmSelectorMeasureType")
        WebElement favQualityMeasuresTypeDropDown;

        @FindBy(id = "_qmSelectorMeasureType_menu")
        WebElement favQmDropDownMenu;

        @FindBy(id = "_qmSaveErrorCDiv")
        WebElement saveListErrorPopUp;

        @FindBy(id= "ani_message")
        WebElement statusMessage;
    }
}
