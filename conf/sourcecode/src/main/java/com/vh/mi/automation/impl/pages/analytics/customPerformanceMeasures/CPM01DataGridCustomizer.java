package com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.comp.dataGrid.customizer.ICPM01DataGridCustomizer;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IColumnSpec;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01DataGridColumn;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;

import java.util.List;

/**
 Created by i82298 on 2/27/2017. */
public class CPM01DataGridCustomizer extends AbstractWebComponent implements
        ICPM01DataGridCustomizer {
    WebElements webElements;
    private IColumnSpec columnSpec;

    public CPM01DataGridCustomizer(WebDriver driver, IColumnSpec columnSpec) {
        super(driver);
        webElements = new WebElements(driver);
        this.columnSpec = columnSpec;
        PageFactory.initElements(driver, this);
    }

    private WebElement catagoryRootContainer(ICPM01DataGridColumn column) {
        WebElement catagoryRootContainer = null;
        for (WebElement element : webElements.catagoryRootContainers) {
            WebElement catagoryNameContainer = element
                    .findElement(By.xpath("./td[1]/span"));
            if (catagoryNameContainer != null) {
                if (column.getLabel()
                        .equalsIgnoreCase(catagoryNameContainer.getText())) {
                    catagoryRootContainer = element;
                }
            }
        }
        return catagoryRootContainer;
    }

    @Override public boolean isFullyLoaded() {
        return webElements.customizeModelTitle.isDisplayed();
    }

    public ICPM01DataGridColumn getRootColumn(ICPM01DataGridColumn column) {
        if (column.getParentColumn() != null) {
            getRootColumn(column.getParentColumn());
        }
        return column;

    }

    @Override public boolean isSelected(ICPM01DataGridColumn col) {

        WebElement colCheckBoxContainerElement = getColumnSelectCheckBox(col);

        return colCheckBoxContainerElement.isSelected();
    }

    private WebElement getColumnSelectCheckBox(ICPM01DataGridColumn col) {
        ICPM01DataGridColumn catagory;
        ICPM01DataGridColumn subCatagory;
        if (col.getParentColumn() != null) {
            if (col.getParentColumn().getParentColumn() != null) {
                catagory = col.getParentColumn().getParentColumn();
                clickOnCatagory(catagory);
            }
            subCatagory = col.getParentColumn();
            clickOnSubCatagory(subCatagory);
        } else {
            throw new SkipException(
                    "Skipping test because Cloumn : " + col.getLabel()
                            + " is a parent column ");
        }
        return getColumnContainerElement(col)
                .findElement(By.xpath("./td[1]/input"));
    }

    private WebElement getColumnContainerElement(ICPM01DataGridColumn col) {
        List<WebElement> columnNameContainers = webElements.customizeContainer
                .findElements(By.xpath("./tr"));
        WebElement colContainerElement = null;
        for (WebElement element : columnNameContainers) {
            if (element.findElement(By.xpath("./td[2]")).getText()
                    .equalsIgnoreCase(col.getLabel())) {
                colContainerElement = element;
                break;
            }
        }
        if (colContainerElement != null) {
            return colContainerElement;
        } else {
            throw new SkipException(
                    "Skipping test because Cloumn: " + col.getLabel()
                            + " is a sub catagory column");
        }
    }

    private void clickOnSubCatagory(ICPM01DataGridColumn subCatagory) {
        List<WebElement> tabs = SeleniumUtils.findElementById(getDriver(), "_customizeGrid:tb")
                .findElements(By.xpath("./div"));
        for (WebElement tab : tabs) {
            try {
                if (tab.getText().equalsIgnoreCase(subCatagory.getLabel())) {
                    tab.click();
                }
            }catch(org.openqa.selenium.StaleElementReferenceException ex){
                System.out.println("Error in finding element");
            }

        }

    }

    private void clickOnCatagory(ICPM01DataGridColumn catagory) {
        WebElement catagoryComponent = catagoryRootContainer(catagory);
        clickOnCatagory(catagoryComponent);

    }

    private void clickOnCatagory(WebElement catagoryComponent) {
        catagoryComponent.findElement(By.xpath("./td[3]/a")).click();

    }

    @Override public void doSelect(ICPM01DataGridColumn col) {

        if (getColumnSelectCheckBox(col).isSelected()) {
            return;
        } else {
            getColumnSelectCheckBox(col).click();
        }
    }

    @Override public void doSelectAll() {
        openSelectAllDrillMenu();
        webElements.selectAllLink.click();

    }

    @Override
    public void doSelectAllForAllCategory() {
        String xpath = "//table[@id='_summaryGrid']//tr[${ROW}]//a";
        for(int i = 1; i <= webElements.mainCatecoryLinks.size(); i++){
            WebElement button = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace("${ROW}", ""+i));
            button.click();
            openSelectAllDrillMenu();
            webElements.selectAllLink.click();
        }
    }

    private void openSelectAllDrillMenu() {

        SeleniumUtils.hoverOnElement( webElements.selectAllDrillMenu,getDriver());
    }

    @Override public void doUnselectAll() {
        openSelectAllDrillMenu();
        webElements.unSelectAllLink.click();
    }

    @Override public void doUnselect(ICPM01DataGridColumn col) {
        if (getColumnSelectCheckBox(col).isSelected()) {
            getColumnSelectCheckBox(col).click();
        } else {
            return;
        }
    }

    @Override public void doSelectMainCatagory(
            ICPM01DataGridColumn mainCatagoryColumn) {
        if (mainCatagoryColumn.getParentColumn() != null) {
            throw new SkipException(
                    "Skipping test because Column: " + mainCatagoryColumn
                            .getLabel() + " is not a root catagory column");
        } else {
            clickOnCatagory(mainCatagoryColumn);
        }
    }

    @Override public void doSelectSubCatagory(
            ICPM01DataGridColumn subCatagoryColumn) {
        ICPM01DataGridColumn catagory;
        ICPM01DataGridColumn subCatagory;
        if (subCatagoryColumn.getParentColumn() != null) {
            if (subCatagoryColumn.getParentColumn().getParentColumn() != null) {
                throw new SkipException(
                        "Skipping test because Cloumn : " + subCatagoryColumn
                                .getLabel()
                                + " is a sub catagory column ");
            } else {
                clickOnCatagory(subCatagoryColumn.getParentColumn());
                clickOnSubCatagory(subCatagoryColumn);
            }

        } else {
            throw new SkipException(
                    "Skipping test because Cloumn : " + subCatagoryColumn.getLabel()
                            + " is a sub catagory  column ");
        }
    }

    @Override public void doApplySelection() {
        webElements.applyBtn.click();
        new LoadingPopup(getDriver()).waitTillDisappears();

    }

    @Override public void doSaveAndApplySelection() {
        webElements.saveBtn.click();
        new LoadingPopup(getDriver()).waitTillDisappears();

    }

    @Override public void doResetSelections() {
        webElements.resetBtn.click();
        new LoadingPopup(getDriver()).waitTillDisappears();

    }

    @Override public void doCancelAndClose() {
        webElements.closeBtn.click();
        new LoadingPopup(getDriver()).waitTillDisappears();
    }

    private static class WebElements {
        private WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "_modalPanelTitleLabel")
        private WebElement customizeModelTitle;

        @FindBy(xpath = "//table[@id='_summaryGrid']/tbody/tr")
        private List<WebElement> catagoryRootContainers;

        @FindBy(id = "_tabContainer")
        private WebElement tabContainer;

        @FindBy(id = "_customizeGrid:tb")
        private WebElement customizeContainer;

        @FindBy(xpath = "//*[@id='_buttonContainer']/input[3]")
        private WebElement saveBtn;

        @FindBy(xpath = "//*[@id='_buttonContainer']/input[2]")
        private WebElement applyBtn;

        @FindBy(xpath = "//*[@id='_buttonContainer']/input[4]")
        private WebElement resetBtn;

        @FindBy(xpath = "//*[@id='_buttonContainer']/input[1]")
        private WebElement closeBtn;

        @FindBy(id="selectAll-CPCustomization_select_drillMenu_drillImage")
        private WebElement selectAllDrillMenu;

        @FindAll({@FindBy(id="_customizeGrid:unselAll_1"),@FindBy(id="_customizeGrid:unselAll")})
        private WebElement unSelectAllLink;

        @FindAll({@FindBy(id="_customizeGrid:selAll_1"),@FindBy(id="_customizeGrid:selAll")})
        private WebElement selectAllLink;

        @FindBy(xpath = "//table[@id='_summaryGrid']//a[@class='d2-drillbtn']")
        private List<WebElement> mainCatecoryLinks;
    }
}
