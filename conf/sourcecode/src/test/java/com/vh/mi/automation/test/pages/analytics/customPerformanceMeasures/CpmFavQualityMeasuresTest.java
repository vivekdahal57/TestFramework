package com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.comp.navigation.INavigationPanel;
import com.vh.mi.automation.api.pages.common.ILoginPage;
import com.vh.mi.automation.api.pages.common.IWelcomePage;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.comp.favqm.FavouiteQualityMeasures;
import com.vh.mi.automation.impl.comp.favqm.FavouriteQualityMeasuresPopUp;
import com.vh.mi.automation.impl.comp.favqm.ListScope;
import com.vh.mi.automation.impl.comp.favqm.QualityMeasuresType;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01DataGrid;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.Indv301DrillPage;
import com.vh.mi.automation.impl.selenium.SeleniumUtils;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.vh.mi.automation.api.constants.MILandingPages.INDIVIDUALS_301;
import static org.fest.assertions.Assertions.assertThat;

@Test(groups = "Product-Critical")
public class CpmFavQualityMeasuresTest extends BaseTest {
    ILoginPage loginPage;
    CPM01 cpm01;
    Indv301DrillPage indv301DrillPage;
    FavouiteQualityMeasures favouiteQualityMeasures;
    FavouriteQualityMeasuresPopUp qualityMeasuresPopUp;
    private static final String FAV_QM_LIST_NAME_QRM = "test_QRM_LIST12435_" + Random.getRandomStringOfLength(3);
    private static final String FAV_QM_LIST_NAME_HEDIS = "test_HEDIS_LIST_" + Random.getRandomStringOfLength(3);
    private static final String FAV_QM_LIST_NAME_HEDIS_QRM = "test_HEDIS_QRM_LIST_" + Random.getRandomStringOfLength(3);
    Integer[] rowsToSelect = { 2, 1 };
    private String currentlyAppliedList;

    @Override
    public boolean skipBrowserCreation() {
        return true;
    }

    @BeforeClass()
    public void setUp() {
        super.setUp();
    }

    @BeforeMethod
    public void beforeTestMethod() {
        createNewBrowserInstance();
        loginPage = mi.open(context.getApplication());
        loginPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
       IWelcomePage welcomePage = loginPage
                .loginWith(getUser(), defaultWaitTime);
        INavigationPanel navigationPanel = welcomePage
                .selectFront(getUser().getAssignedApplications().get(0));
        navigationPanel.doWaitTillFullyLoaded(defaultWaitTime);
        cpm01 = navigationPanel.doNavigateTo(CPM01.class, defaultWaitTime);
        favouiteQualityMeasures = cpm01.getFavouiteQualityMeasures();
    }

    @AfterMethod
    public void afterTest() {
        if(indv301DrillPage != null) {
            String mainHandel = SeleniumUtils.getNextWindowHandle(getWebDriver());
            indv301DrillPage.doClose();
            getWebDriver().switchTo().window(mainHandel);
            if (currentlyAppliedList != null) {
                favouiteQualityMeasures
                        .deleteList(currentlyAppliedList, ListScope.PRIVATE);
                assertThat(favouiteQualityMeasures.getDeletionCompleteStatusMessage()).isEqualToIgnoringCase("The saved Quality Measures list " + currentlyAppliedList + " has been successfully removed.");
            }
            currentlyAppliedList = null;
            indv301DrillPage = null;
            closeBrowserInstance();
        }
        else{
            favouiteQualityMeasures.deleteList(FAV_QM_LIST_NAME_QRM, ListScope.PRIVATE);

        }
    }

    @Test(description  = "Custom Performance Measure => Quality Measures => Create List => Select Few QRM Measure and Save => check whether the selected Quality Measure is displayed")
    public void testThatSelectedQualityMeasureisDisplayed(){
        qualityMeasuresPopUp = favouiteQualityMeasures
                .openCreateListComponent();
        qualityMeasuresPopUp
                .selectQualityMeasures(QualityMeasuresType.QRM, rowsToSelect)
                .save(FAV_QM_LIST_NAME_QRM, ListScope.PRIVATE, false);
        currentlyAppliedList = FAV_QM_LIST_NAME_QRM;
        assertThat(qualityMeasuresPopUp.getQualityMeasuresSavedStatusMessage()).isEqualToIgnoringCase(qualityMeasuresPopUp.getExpectedMessageForQualityMeasuresSaved(FAV_QM_LIST_NAME_QRM));
        List<String> selectedQualityMeasures = qualityMeasuresPopUp.selectedQualityMeasures();

        for(String  QualityMeasures : cpm01.qualityMeasuresElements() ){
            assertThat(selectedQualityMeasures).contains(QualityMeasures);
        }
    }

    @Test(description = "Custom Performance Measures => Quality Measures => Create List => Quality Measure Sets(Select QRM) =>" +
            "Select Few QRM Measure And Save => Drill Down To Individual Page => Check If selected measure is applied in individual 301 page")
    public void testThatQRMAppliedInCPMIsCarriedToIndividual() {
        qualityMeasuresPopUp = favouiteQualityMeasures
                .openCreateListComponent();
        qualityMeasuresPopUp
                .selectQualityMeasures(QualityMeasuresType.QRM, rowsToSelect)
                .save(FAV_QM_LIST_NAME_QRM, ListScope.PRIVATE, false);
        assertThat(qualityMeasuresPopUp.getQualityMeasuresSavedStatusMessage()).isEqualToIgnoringCase(qualityMeasuresPopUp.getExpectedMessageForQualityMeasuresSaved(FAV_QM_LIST_NAME_QRM));
        CPM01DataGrid dataGrid = cpm01.getDataGrid();
        List<String> selectedQrmInCPM = dataGrid.getDynamicColumnLabel();
        currentlyAppliedList = FAV_QM_LIST_NAME_QRM;
        indv301DrillPage = cpm01.doDrillToIndv();
        assertThat(indv301DrillPage.getPageTitle())
                .isEqualTo(INDIVIDUALS_301.getPageTitle());
        List<String> selectedQrmInIndv = indv301DrillPage.getDataGrid()
                .getDynamicColumnLabel();
        for (String qrmInIndv : selectedQrmInIndv) {
            assertThat(selectedQrmInCPM).contains(qrmInIndv);
        }
    }

    @Test(description = "Custom Performance Measures => Quality Measures => Create List => Quality Measure Sets(Select HEDIS From Dropdown) =>" +
            "Select Few QRM Measure And Save => Drill Down To Individual Page => Check If selected measure is applied in individual 301 page")
    public void testThatHEDISAppliedInCPMIsCarriedToIndividual() {
        qualityMeasuresPopUp = favouiteQualityMeasures
                .openCreateListComponent();
        qualityMeasuresPopUp
                .selectQualityMeasures(QualityMeasuresType.HEDIS,
                        rowsToSelect)
                .save(FAV_QM_LIST_NAME_HEDIS, ListScope.PRIVATE, false);
        assertThat(qualityMeasuresPopUp.getQualityMeasuresSavedStatusMessage()).isEqualToIgnoringCase(qualityMeasuresPopUp.getExpectedMessageForQualityMeasuresSaved(FAV_QM_LIST_NAME_HEDIS));
        CPM01DataGrid dataGrid = cpm01.getDataGrid();
        List<String> selectedHEDIS = qualityMeasuresPopUp
                .getSelectedHedis();
        List<String> selectedHEDISInCPM = dataGrid.getDynamicColumnLabel();
        for (String hedis : selectedHEDIS) {
            assertThat(selectedHEDISInCPM).contains(hedis);
        }
        currentlyAppliedList = FAV_QM_LIST_NAME_HEDIS;
        indv301DrillPage = cpm01.doDrillToIndv();
        assertThat(indv301DrillPage.getPageTitle())
                .isEqualTo(INDIVIDUALS_301.getPageTitle());
        List<String> selectedHEDISInIndv = indv301DrillPage.getDataGrid()
                .getDynamicColumnLabel();
        for (String qrmInIndv : selectedHEDISInIndv) {
            assertThat(selectedHEDISInCPM).contains(qrmInIndv);
        }
    }

    @Test(description = "Custom Performance Measures => Quality Measures => Create List => Quality Measure Sets(Select QRM Dropdown) =>" +
            "Select Few QRM Measure => Select HEDIS Dropdown => Select Few HEDIS Measure And Save => Drill Down To Individual Page => " +
            "Check If selected measure is applied in individual 301 page")
    public void testThatQRMaddHEDISAppliedInCPMIsCarriedToIndividual() {
        qualityMeasuresPopUp = favouiteQualityMeasures
                .openCreateListComponent();
        qualityMeasuresPopUp
                .selectQualityMeasures(QualityMeasuresType.QRM, rowsToSelect)
                .selectQualityMeasures(QualityMeasuresType.HEDIS, rowsToSelect)
                .save(FAV_QM_LIST_NAME_HEDIS_QRM, ListScope.PRIVATE, false);
        assertThat(qualityMeasuresPopUp.getQualityMeasuresSavedStatusMessage()).isEqualToIgnoringCase(qualityMeasuresPopUp.getExpectedMessageForQualityMeasuresSaved(FAV_QM_LIST_NAME_HEDIS_QRM));
        List<String> selectedHEDIS = qualityMeasuresPopUp.getSelectedHedis();
        CPM01DataGrid dataGrid = cpm01.getDataGrid();
        List<String> selectedQrm = qualityMeasuresPopUp.getSelectedQms();
        List<String> selectedQrmAndHedisInCPM = dataGrid
                .getDynamicColumnLabel();
        for (String qrm : selectedQrm) {
            assertThat(selectedQrmAndHedisInCPM).contains(qrm);

        }
        if (!selectedHEDIS.isEmpty() && selectedHEDIS != null) {
            for (String hedis : selectedHEDIS) {
                assertThat(selectedQrmAndHedisInCPM).contains(hedis);
            }
        }

        currentlyAppliedList = FAV_QM_LIST_NAME_HEDIS_QRM;
        indv301DrillPage = cpm01.doDrillToIndv();
        assertThat(indv301DrillPage.getPageTitle())
                .isEqualTo(INDIVIDUALS_301.getPageTitle());
        List<String> selectedQrmInIndv = indv301DrillPage.getDataGrid()
                .getDynamicColumnLabel();
        for (String qrmInIndv : selectedQrmInIndv) {
            assertThat(selectedQrmAndHedisInCPM).contains(qrmInIndv);
        }

    }

}
