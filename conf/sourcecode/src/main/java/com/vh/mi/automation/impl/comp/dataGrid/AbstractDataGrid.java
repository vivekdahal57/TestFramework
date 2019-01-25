package com.vh.mi.automation.impl.comp.dataGrid;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.vh.mi.automation.api.annotations.logging.LogMethodExecutionTime;
import com.vh.mi.automation.api.comp.dataGrid.*;
import com.vh.mi.automation.api.constants.SortOrder;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.exceptions.InvalidFilterException;
import com.vh.mi.automation.api.exceptions.NoFilterSetException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.google.common.base.Preconditions.checkState;
import static com.vh.mi.automation.impl.selenium.SeleniumUtils.elementExists;

/**
 * @author nimanandhar
 */

public abstract class AbstractDataGrid extends AbstractWebComponent implements IDataGrid {
    private static final String ID_PLACEHOLDER = "${columnId}";
    public static final Integer FIRST_ROW = 0;
    private static final String NTH_DATA_PLACEHOLDER = "${nthData}";
    private static final String NTH_CHECKBOX_PLACEHOLDER = "${nthCheckbox}";
    private static final String DRILLOPTION_PLACEHOLDER = "${drillOption}";

    protected IDataGridHeaderParser gridHeaderParser;
    protected IColumnIdExtractor columnIdExtractor;

    private final WebElements webElements;
    private final LoadingPopup loadingPopup;

    private Map<IDataGridColumn, Integer> columnMapCache;
    private List<IDataGridColumn> columnsCache;
    private List<IDataGridColumn> recentFilteredColumns = new ArrayList<>();

    private static Logger logger = LoggerFactory.getLogger(AbstractDataGrid.class);

    public AbstractDataGrid(WebDriver driver) {
        super(driver);
        gridHeaderParser = new DefaultDataGridHeaderParser();
        columnIdExtractor = new DefaultColumnIdExtractor();
        webElements = new WebElements(driver);
        loadingPopup = new LoadingPopup(driver);
    }

    @Override
    public List<IDataGridColumn> getColumns() {
        if (columnsCache == null) {
            columnsCache = new ArrayList<>(getColumnMap().keySet());
        }
        return columnsCache;
    }

    public IDataGridColumn getColumnForUnknownBlankCheck(){
        return null;
    }

    @Override
   public boolean checkUnknownBlanksForColumn(IDataGridColumn  column){
       List<String> columns = getData(column);
       for(String  columnValues : columns){
           if(columnValues.contains("Unknown")){
               logger.info("Text 'Unknown' found");
               return false;
           }
       }
       logger.info("Text 'Unknown' not found");
       return true;
    }
    @Override
    public boolean checkUnknownBlanksForDefault() {
        List<String> columns = getData(getColumnForUnknownBlankCheck());
        for(String  columnValues : columns){
            if(columnValues.contains("Unknown")){
                logger.info("Text 'Unknown' found as " + columnValues);
                return false;
            }
        }
        logger.info("Text 'Unknown' not found");
        return true;
    }

    @Override
    public Integer getColumnIndex(IDataGridColumn column) {
        if (!getColumnMap().containsKey(column)) {
            throw new AutomationException("No such column " + column + " in data grid");
        }
        return getColumnMap().get(column);
    }

    private Map<IDataGridColumn, Integer> getColumnMap() {
        if (columnMapCache == null) {
            columnMapCache = new HashMap<>();
            WaitUtils.waitForSeconds(5);
            Set<Map.Entry<Integer, String>> entr = gridHeaderParser.parse(webElements.simpleGridHeader).entrySet();
            for (Map.Entry<Integer, String> entry : entr) {
                String id = columnIdExtractor.extractColumnIdFromHeader(entry.getValue());
                if (isDynamicId(id)) continue;

                IDataGridColumn column = getColumn(id);
                if (column == null) {
                    logger.error("No column found for id " + id);
                } else {
                    columnMapCache.put(column, entry.getKey());
                }
            }
        }
        return columnMapCache;
    }

    protected boolean isDynamicId(String id) {
        return id.matches("^j_id\\d+$");
    }

    protected abstract IDataGridColumn getColumn(String id);


    @Override
    public final SortOrder getCurrentSortOrder() {
        String xpath = ".//img[contains(@src,'_arrow.jpg')]";
        WebElement sortImageElement = webElements.simpleGridHeader.findElement(By.xpath(xpath));
        String imgSrc = sortImageElement.getAttribute("src");
        if (imgSrc.endsWith("up_arrow.jpg")) {
            return SortOrder.ASC;
        } else if (imgSrc.endsWith("down_arrow.jpg")) {
            return SortOrder.DESC;
        } else {
            throw new RuntimeException("Unexpected value for sortImageElement source " + imgSrc);
        }
    }


    @Override
    public final Table<Integer, IDataGridColumn, String> getData() {
        Table<Integer, IDataGridColumn, String> dataTable = HashBasedTable.create();
        for (IDataGridColumn column : getColumns()) {
            List<String> gridValues = getData(column);
            for (int row = FIRST_ROW; row < gridValues.size(); row++) {
                dataTable.put(row, column, gridValues.get(row));
            }
        }
        return dataTable;

    }


    @Override
    @LogMethodExecutionTime
    public final String getTableDataAsText() {
        return webElements.dataGridBodyElement.getText();
    }


    @Override
    public final List<String> getData(IDataGridColumn column) {
        String xPath = ".//tr/td[position()=" + getColumnIndex(column) + "]";
        List<WebElement> elements = webElements.simpleGridBody.findElements(By.xpath(xPath));
        List<String> gridValues = new ArrayList<>();
        for (WebElement element : elements) {
            gridValues.add(element.getText());
        }
        return gridValues;
    }

    @Override
    public void selectTopNCheckBoxInDataGrid(int topN){
        for (int i=1;i<=topN;i++){
            String xpath = "(//*[@id='d2Form:simpleGrid:tb']//td/input[@type='checkbox'])[${nthCheckbox}]";
            SeleniumUtils.findElementByXPath(getDriver(),xpath.replace(NTH_CHECKBOX_PLACEHOLDER,String.valueOf(i))).click();
        }
    }

    @Override
    public final List<String> getNthData(IDataGridColumn column,int topN) {
        String xPath = "";
        List<String> gridValues = new ArrayList<>();
        for(int i=1;i<=topN;i++){
            xPath = selectNthDataXpath(column,String.valueOf(i));
            gridValues.add(webElements.simpleGridBody.findElement(By.xpath(xPath)).getText());
        }
        return gridValues;
    }

    private String selectNthDataXpath(IDataGridColumn column,String nthData){
        String xpath = ".//tr[${nthData}]/td[position()=" + getColumnIndex(column) + "]";
        return xpath.replace(NTH_DATA_PLACEHOLDER, nthData);
    }


    @Override
    public final ImmutableList<IDataGridRow> getRows() {
        List<WebElement> trs = getRowsElement();

        List<IDataGridRow> transform = Lists.transform(trs, new Function<WebElement, IDataGridRow>() {
            @Override
            public IDataGridRow apply(WebElement trElement) {
                return new DataGridRow(getDriver(), trElement, getDrillFunction());
            }
        });

        return ImmutableList.copyOf(transform);
    }

    protected List<WebElement> getRowsElement() {
        return webElements.dataGridBodyElement.findElements(By.tagName("tr"));
    }

    protected Function<String, IDrillPage> getDrillFunction() {
        return new Function<String, IDrillPage>() {
            @Override
            public IDrillPage apply(String input) {
                return PageFactory.initElements(getDriver(), getDrillPageClass(input));
            }
        };
    }

    /**
     * Returns the pageObject class corresponding to the drill option
     * The pageObject class is required for instantiation of the PageObject
     * after we have drilled to a specific page.
     *
     * @param drillOption
     * @return A concrete Page Object Class corresponding to the drillOption
     */
    protected Class<? extends IDrillPage> getDrillPageClass(String drillOption) {
        throw new AutomationException("Not Implemented . Please implement getDrillPageClass to support drilling");
    }




    @Override
    public final void doFilter(IDataGridColumn column, String filterValue) {
        Preconditions.checkArgument(isFilterable(column));

        Map<IDataGridColumn, String> filterValueMap = new HashMap<>();
        filterValueMap.put(column, filterValue);
        doFilter(filterValueMap);
    }


    @Override
    public final void doFilter(Map<IDataGridColumn, String> columnFilterValueMap) {
        recentFilteredColumns.clear();
        for (IDataGridColumn column : columnFilterValueMap.keySet()) {
            if (!isFilterable(column))
                continue;
            recentFilteredColumns.add(column);
            getFilterElement(column).sendKeys(columnFilterValueMap.get(column));
        }
        applyFilter();
    }


    private WebElement getFilterElement(IDataGridColumn column) {
        String xpath = ".//*[contains(@id,'_${columnId}_') and not(@disabled='disabled') and contains(@id,'filterInputText')]".replace(ID_PLACEHOLDER, column.getId());
        return webElements.simpleGridHeader.findElement(By.xpath(xpath));
    }

    private void applyFilter() {
        hoverOnFilterMenu();
        SeleniumUtils.click(webElements.applyFilterLink,getDriver());
        if (isAlertPresent()) {
            String alertText = getAlertTextAndDismissAlert();
            throw new InvalidFilterException(alertText);
        } else {
            waitForAjaxCallToFinish();
        }

    }

    @Override
    public IDrillPage applyDrill(String drillOption) {
        hoverOnDrillMenu();
        String xpath = "//*[@id=\"dropmenudiv\"]/a[text()=\"${drillOption}\"]";
        WebElement drillOptionToClick = SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(DRILLOPTION_PLACEHOLDER, drillOption));
        SeleniumUtils.click(drillOptionToClick);
        return PageFactory.initElements(getDriver(), getDrillPageClass(drillOption));
    }


    @Override
    public boolean isSortable(IDataGridColumn column) {
        String xpath = ".//*[contains(@id,'_${columnId}_') and contains(@id,'sortCommandLink')]".replace(ID_PLACEHOLDER, column.getId());
        return elementExists(webElements.simpleGridHeader, xpath);
    }

    @Override
    public boolean isFilterable(IDataGridColumn column) {
        String xpath = ".//*[contains(@id,'_${columnId}_') and not(@disabled='disabled') and contains(@id,'filterInputText')]".replace((ID_PLACEHOLDER), column.getId());
        return elementExists(webElements.simpleGridHeader, xpath);
    }

    @Override
    public final void doResetFilter() {
        hoverOnFilterMenu();
        webElements.resetFilterLink.click();

        if (isAlertPresent()) {
            String alertText = getAlertTextAndDismissAlert();
            if (alertText.equals("No filters Set"))
                throw new NoFilterSetException(alertText);
            else
                throw new InvalidFilterException(alertText);
        }

        waitForAjaxCallToFinish();
    }

    private void hoverOnFilterMenu() {
        Actions actions = new Actions(getDriver()).moveToElement(webElements.filterDrillMenuElement);
        actions.build().perform();
    }

    private void hoverOnDrillMenu(){
        Actions actions = new Actions(getDriver()).moveToElement(webElements.DrillMenuElement);
        actions.build().perform();
    }


    private boolean isAlertPresent() {
        try {
            getDriver().switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private String getAlertTextAndDismissAlert() {
        String alertText = null;
        try {
            alertText = getDriver().switchTo().alert().getText();
            getDriver().switchTo().alert().dismiss();
        } catch (Exception e) {
            //dismiss
        }
        return alertText;
    }


    @Override
    public final void doSort(IDataGridColumn column, SortOrder sortOrder) {
        Preconditions.checkArgument(isSortable(column));

        if (column.equals(getCurrentSortedColumn())) {
            if (!getCurrentSortOrder().equals(sortOrder)) {
                //sorted in opposite order, toggle sorting to sort in right order

                SeleniumUtils.click(getSortLinkElement(column),getDriver());
            }
        } else {
            if (sortOrder.equals(SortOrder.DESC)) {
                WebElement columnElement = getSortLinkElement(column);
                Assertions.assertThat(columnElement.isDisplayed());
                SeleniumUtils.click(columnElement, getDriver()); //click once to sort in descending order
            } else {
                SeleniumUtils.click(getSortLinkElement(column), getDriver()); //first click sorts it in desc order
                waitForAjaxCallToFinish();
                SeleniumUtils.click(getSortLinkElement(column), getDriver()); //second click sorts it in ascending order
            }
        }
        waitForAjaxCallToFinish();
    }


    private WebElement getSortLinkElement(IDataGridColumn column) {
        return webElements.simpleGridHeader.findElement(By.xpath(".//*[contains(@id,'_" + column.getId() + "_') and contains(@id,'sortCommandLink')]"));
    }

    @Override
    public IDataGridColumn getCurrentSortedColumn() {
        String xpath = ".//img[contains(@src,'arrow.jpg')]/parent::a";
        WebElement currentSortedColumnElement = webElements.simpleGridHeader.findElement(By.xpath(xpath));
        String id = columnIdExtractor.extractColumnIdFromSortLink(currentSortedColumnElement.getAttribute("id"));
        return getColumn(id);
    }

    @Override
    public final boolean isFullyLoaded() {
        return true;
    }

    private void waitForAjaxCallToFinish() {
        WaitUtils.waitForMilliSeconds(100);
        loadingPopup.waitTillDisappears();
        WaitUtils.waitForMilliSeconds(400);
        checkState(!loadingPopup.isDisplayed(), "The popup should have disappeared");
    }

    @Override
    public final void doFlushAllCache() {
        columnMapCache = null;
        columnsCache = null;
    }

    @Override
    public void doClearRecentFilteredValue() {
        for (IDataGridColumn recentFilteredColumn : recentFilteredColumns) {
            String xpath = ".//*[contains(@id,'_${columnId}_') and not(@disabled='disabled') and contains(@id,'filterInputText')]".replace((ID_PLACEHOLDER), recentFilteredColumn.getId());
            webElements.simpleGridHeader.findElement(By.xpath(xpath)).clear();
        }
    }
    public List<String> getDynamicColumnLabel() {
        List<String> dynamicColumns = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : gridHeaderParser
                .parse(webElements.simpleGridHeader).entrySet()) {
            String id = columnIdExtractor
                    .extractColumnIdFromHeader(entry.getValue());
            if (isDynamicId(id)) {
                WebElement dynamicColumn = getDriver()
                        .findElement(By.id(entry.getValue()))
                        .findElement(By.tagName("a"));
                if (!"".equals(dynamicColumn.getText())) {
                    dynamicColumns.add(dynamicColumn.getText());
                }

            }
        }
        return dynamicColumns;
    }



    private static class WebElements {

        private WebElements(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//table[(starts-with(@id,'d2Form:simpleGrid') or starts-with(@id,'d2Form:metricsGrid') or starts-with(@id,'d2Form:grid2') or starts-with(@id, 'd2Form:grid1') or starts-with(@id, 'd2Form:gridTable') or starts-with(@id,'d2Form:simpleGridTrend')) and string-length(@id)<=22]/tbody")
        WebElement simpleGridBody;

        @FindBy(xpath ="//table[(starts-with(@id,'d2Form:simpleGrid') or starts-with(@id,'d2Form:metricsGrid') or starts-with(@id,'d2Form:grid2') or starts-with(@id, 'd2Form:grid1') or starts-with(@id, 'd2Form:gridTable') or starts-with(@id,'d2Form:simpleGridTrend')) and string-length(@id)<=22]/thead")
        WebElement simpleGridHeader;

        @FindAll({
                @FindBy(xpath = "//div[@class='d2-cursor d2-filterbtn' or @class='d2-drill-menu']"),
                @FindBy(xpath = "//div[@id='iFilter_drillMenu_menu']//preceding::span[1]"),
                @FindBy(xpath = "//div[@id='applyFilterIcon_drillMenu_menu']//preceding::span[1]"),
                @FindBy(xpath = "//span[@class='d2-drill-menu']")}
        )
        WebElement filterDrillMenuElement;


        @FindAll({@FindBy(linkText = "Apply Filter"),

                  @FindBy(id = "d2Form:simpleGrid:filter_menuTable:0:filter_applyFilter"),
                @FindBy(id = "d2Form:simpleGrid:icons_applyFilter_1")
                })
        WebElement applyFilterLink;

        @FindAll({
                @FindBy(id = "d2Form:simpleGrid:tb"),
                @FindBy(id = "d2Form:simpleGrid2:tb"),
                @FindBy(id = "d2Form:grid1:tb"),
                @FindBy(id = "d2Form:grid2:tb")
        })
        WebElement dataGridBodyElement;

        @FindBy(linkText = "Reset Filter")
        WebElement resetFilterLink = null;

        @FindBy(xpath = "//*[contains(@id,'d2Form:simpleGrid')]/div/a[1]")
        WebElement DrillMenuElement;


    }
}