package com.vh.mi.automation.impl.pages.queryBuilder.stratifier;

import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.IPopulationAnalyser;
import com.vh.mi.automation.api.pages.queryBuilder.stratifier.PopulationAnalyserDataGridColumn;
import com.vh.mi.automation.api.utils.WaitUtils;
import com.vh.mi.automation.impl.comp.loading.LoadingPopup;
import com.vh.mi.automation.impl.pages.common.AbstractLandingPage;
import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyzerCohortAnalyzer.CohortAnalyzer;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by i20345 on 12/27/2016.
 */
public class PopulationAnalyser extends AbstractLandingPage implements IPopulationAnalyser {
    public static final String MODULE_ID = "180";
    private static final String COHORT_DESCRIPTION_PLACEHOLDER="${cohortDescription}";
    private WebElements webElements;
    private final IDataGrid dataGrid;
    private final LoadingPopup loadingPopup;

    Actions action;
    Individual301 individual301;
    CohortAnalyzer cohortAnalyzer;

    public PopulationAnalyser(WebDriver driver) {
        super(driver, MODULE_ID);
        dataGrid = new PopulationAnalyserDataGrid(driver);
        loadingPopup = new LoadingPopup(driver);
        webElements=new WebElements(getDriver());
        action = new Actions(getDriver());
        individual301 = new Individual301(getDriver());
    }

     public CohortDetails goToCohortDetails(String cohortDescription)
    {
        CohortDetails cohortDetails = PageFactory.initElements(getDriver(),CohortDetails.class);
        filterWithCohortDescription(cohortDescription);

        WebElement hoverCohortElement = getHoverCohortElement(cohortDescription);
        SeleniumUtils.hover(getDriver(),hoverCohortElement);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnViewCohortDetails);
        SeleniumUtils.click(webElements.btnViewCohortDetails);
        cohortDetails.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return cohortDetails;
    }

    public RefineLogic goToViewQueryDetails(String cohortDescription){
        RefineLogic refineLogic= PageFactory.initElements(getDriver(),RefineLogic.class);
        filterWithCohortDescription(cohortDescription);
        WebElement hoverCohortElement = getHoverCohortElement(cohortDescription);
        SeleniumUtils.hover(getDriver(),hoverCohortElement);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnViewQueryDetails);
        SeleniumUtils.click(webElements.btnViewQueryDetails);
        refineLogic.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return refineLogic;
    }

    public Individual301 goToViewIndividual(String cohortDescription){

        Individual301 individual301 = PageFactory.initElements(getDriver(),Individual301.class);
        WebElement hoverCohortElement = getHoverCohortElement(cohortDescription);
        SeleniumUtils.hover(getDriver(),hoverCohortElement);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnViewIndividual);
        SeleniumUtils.click(webElements.btnViewIndividual);
        WaitUtils.waitForSeconds(TimeOuts.TWO_SECOND);
        SeleniumUtils.switchToNewWindow(getDriver());

        individual301.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);

        return individual301;
    }


    public int getDataTableSize(){
        return dataGrid.getData().rowMap().size();
    }

    public void filterWithCohortDescription(String cohortDescription){
        dataGrid.doFilter(PopulationAnalyserDataGridColumn.COHORT_DESCRIPTION, cohortDescription);
        waitForAjaxCallToFinish();
        if(getDataTableSize() == 0){
            throw new Error("No Cohort with Description : " + cohortDescription);
        }
    }

    public void removeCohort(String cohortDescription){
        WebElement hoverCohortElement = getHoverCohortElement(cohortDescription);
        SeleniumUtils.hover(getDriver(),hoverCohortElement);
        WaitUtils.waitUntilEnabled(getDriver(), webElements.btnDeleteCohort);
        SeleniumUtils.click(webElements.btnDeleteCohort);
        WaitUtils.waitForSeconds(TimeOuts.ONE_SECOND);
        if(SeleniumUtils.isAlertPresent(getDriver())){
            SeleniumUtils.clickOkOnAlert(getDriver());
        }
        waitForAjaxCallToFinish();
        loadingPopup.waitTillDisappears();
    }

    private WebElement getHoverCohortElement(String cohortDescription){
        String xpath = "//*[@id='d2Form:simpleGrid:tb']//td[text()='${cohortDescription}']//following::td[2]/a[1]";
        return SeleniumUtils.findElementByXPath(getDriver(), xpath.replace(COHORT_DESCRIPTION_PLACEHOLDER, cohortDescription));
    }

    private void waitForAjaxCallToFinish() {
        WaitUtils.waitForMilliSeconds(100);
        loadingPopup.waitTillDisappears();
        checkState(!loadingPopup.isDisplayed(), "The popup should have disappeared");
    }

   /* public String getAnyCohortFromTheTable(){
        String existingCohortName = webElements.anyCreatedCohort.getText();
        return existingCohortName;
    }*/

    public String getMemberTrendInfoSectionHeading(){
        WaitUtils.waitUntilEnabled(getDriver(),webElements.memberTrendInfoSectionHeading);
        return webElements.memberTrendInfoSectionHeading.getText();
    }

    public CohortAnalyzer selectCreatedCohortsAndNavigateToCohortAnalyzer(List<String> cohortList){
        for (String name : cohortList){
            dataGrid.doFilter(PopulationAnalyserDataGridColumn.COHORT_DESCRIPTION,name);
            WaitUtils.waitUntilEnabled(getDriver(),webElements.chkBoxCohortDescription);
            SeleniumUtils.click(webElements.chkBoxCohortDescription);
            dataGrid.doClearRecentFilteredValue();
        }
        SeleniumUtils.hover(getDriver(),webElements.hoverAnalyzer);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnAnalyzer);
        SeleniumUtils.click(webElements.btnAnalyzer);
        cohortAnalyzer = new CohortAnalyzer(getDriver());
        cohortAnalyzer.doWaitTillFullyLoaded(TimeOuts.SIXTY_SECONDS);
        return cohortAnalyzer;
    }

    public String getCohortNumber(String cohortName){
        dataGrid.doFilter(PopulationAnalyserDataGridColumn.COHORT_DESCRIPTION,cohortName);
        String xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr[1]/td[2]";
        String cohortNumber = SeleniumUtils.findElementByXPath(getDriver(),xpath).getText();
        dataGrid.doClearRecentFilteredValue();
        return cohortNumber;
    }

    public void clickShowAllFavouriteCohorts(){
        WaitUtils.waitUntilEnabled(getDriver(),webElements.btnShowFavorites);
        SeleniumUtils.click(webElements.btnShowFavorites);
        WaitUtils.waitUntilEnabled(getDriver(),webElements.fullyLoadedElement);
    }

    public boolean checkIfListedCohortsAreAllMarkedFavourite(){
        String btnXpath = "//*[@id=\"d2Form:paginationPanel\"]//td[position()=last()]//preceding::td[1]/a[@style=\"cursor:pointer\"]";
        String rowCountXpath = "//*[@class=\"d2-pagination-table\"]//span[.=\"Records\"]//following::span[3]";
        String totalRowCount = SeleniumUtils.findElementByXPath(getDriver(),rowCountXpath).getText();

        Integer totalFavouriteCount = 0;
        boolean flag = true;
        do{
            try {
                String favouriteCohortXpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr/td[1]//a/img[@title=\"Remove from favorites\"]";
                Integer favouriteCohortCount =  SeleniumUtils.findElementsByXPath(getDriver(),favouriteCohortXpath).size();
                totalFavouriteCount = totalFavouriteCount + favouriteCohortCount;

                SeleniumUtils.click(SeleniumUtils.findElementByXPath(getDriver(),btnXpath),getDriver());
                loadingPopup.waitTillDisappears();
            }catch (NoSuchElementException ex){
                flag = false;
            }
        }while (flag);

        if (Integer.parseInt(totalRowCount) == totalFavouriteCount){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkIfCreatedCohortNumberExistsInFavouriteCohorts(String cohortNumber){
        dataGrid.doFilter(PopulationAnalyserDataGridColumn.COHORT,cohortNumber);
        String xpath = "//*[@class=\"rich-table-cell string-cell d2-align-left\"]";
        List<WebElement> cohortNumberList = SeleniumUtils.findElementsByXPath(getDriver(),xpath);
        dataGrid.doClearRecentFilteredValue();
        for (WebElement c : cohortNumberList){
            if (c.getText().equalsIgnoreCase(cohortNumber)){
                return true;
            }
        }
        return false;
    }

    private static class WebElements {

        public WebElements(WebDriver webDriver) {
            PageFactory.initElements(webDriver, this);
        }

        @FindBy(id = "d2Form:simpleGrid:column_COHORTDESC_sortCommandLink")
        private WebElement fullyLoadedElement;

        @FindBy(xpath = "//div[@id='dropmenudiv']//a[text()='Cohort Details']")
        private WebElement btnViewCohortDetails;

        @FindBy(xpath = "//div[@id='dropmenudiv']//a[text()='View Individuals']")
        private WebElement btnViewIndividual;

        @FindBy(xpath = "//div[@id='dropmenudiv']//a[text()='Delete Cohort']")
        private WebElement btnDeleteCohort;

        /*@FindBy(xpath = "/*//*[@id=\"d2Form:simpleGrid:tb\"]/tr[2]/td[3]")
        private WebElement anyCreatedCohort;*/

        @FindBy(xpath = "//*[@id=\"d2Form:gridContainer\"]//span[.=\"Member Trend\"]")
        private WebElement memberTrendInfoSectionHeading;

        @FindBy(xpath = "//tr/td[.='Business Levels']//following::td[15]")
        private WebElement viewIndividualHover;

        @FindBy(xpath = "//div[@id='dropmenudiv']//a[text()='View Query Details']")
        private WebElement btnViewQueryDetails;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr[1]//input[@type=\"checkbox\"]")
        private WebElement chkBoxCohortDescription;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid\"]//tr[@class=\"rich-table-subheader\"]/th[5]//td/a[1]")
        private WebElement hoverAnalyzer;

        @FindBy(xpath = "//*[@id=\"dropmenudiv\"]/a")
        private WebElement btnAnalyzer;

        //*[@id="d2Form:simpleGrid:ShowFavorite_1"]

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:ShowFavorite\"]")
        private WebElement btnShowFavorites;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr[1]/td[1]//a/img[@title=\"Add to favorites\"]")
        private WebElement btnFavouriteMarker;

        @FindBy(xpath = "//*[@id=\"d2Form:simpleGrid:tb\"]/tr[1]/td[1]//a/img[@title=\"Remove from favorites\"]")
        private WebElement getBtnFavouriteRemover;

    }
}
