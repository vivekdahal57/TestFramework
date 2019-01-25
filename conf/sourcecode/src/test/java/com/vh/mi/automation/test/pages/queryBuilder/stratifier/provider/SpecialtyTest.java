package com.vh.mi.automation.test.pages.queryBuilder.stratifier.provider;

import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.QueryBuilder;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.provider.Specialty;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i20288 on 1/23/2017.
 */

@Test (groups = { "Product-Critical" })
public class SpecialtyTest extends BaseTest {
    private static final Integer TOP_N_SELECTEION = 4;
    private static final String SELECTED_TABLE_NO_RECORDS_FOUND_TEXT = "No record found";
    Specialty specialty;
    QueryBuilder queryBuilder;

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @BeforeMethod
    public void beforeEach() {
        queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class, TimeOuts.THIRTY_SECONDS);
        specialty = queryBuilder.selectCriteriaComponent(Specialty.class);
        specialty.selectSpecialtyTab();
    }
    @Test(description = "Query Builder => Stratifier => Query Builder => Provider => Speciality Tab, " +
            "Include all, and assert if Included codes are actually included.")
    public void includeAllFunctionalityTest(){
        specialty.IncludeAll();
        String totalCountInRemainingTable = specialty.getTotalRecordsInRemainingTable();
        specialty.clicRefreshButton();
        String totalCountInSelectedTable = specialty.getTotalRecordsInSelectedTable();
        assertThat(totalCountInRemainingTable).isEqualTo(totalCountInSelectedTable);
  }

    @Test(description = "Query Builder => Stratifier => Query Builder => Criteria Provider => Speciality Tab, " +
            "Exclude all, and assert if Excluded codes are actually excluded.")
    public void excludeAllFunctionalityTest() {
        specialty.ExcludeAll();
        String totalCountInRemainingTable = specialty.getTotalRecordsInRemainingTable();
        specialty.clicRefreshButton();
        String totalCountInSelectedTable = specialty.getTotalRecordsInSelectedTable();
        assertThat(totalCountInRemainingTable).isEqualTo(totalCountInSelectedTable);
    }

    @Test(description = "Query Builder => Stratifier => Query Builder => Provider => Speciality Tab, " +
            "Include Top N selection, then Remove All  and assert that the selected codes are removed.")
    public void removeAllOfIncludeFunctionalityTest() {
        specialty.IncludeTopNInFirstPage(TOP_N_SELECTEION);
        specialty.RemoveAllInclude();
        specialty.clicRefreshButton();
        assertThat(specialty.getNoRecordsFoundTextInSelectedTable()).isEqualTo(SELECTED_TABLE_NO_RECORDS_FOUND_TEXT);
      }

    @Test(description = "Query Builder => Stratifier => Query Builder => Provider => Speciality Tab, " +
            "Exclude Top N selection, then Remove All  and assert that the selected codes are removed.")
    public void removeAllOfExcludeFunctionalityTest() {
        specialty.ExcludeTopNInFirstPage(TOP_N_SELECTEION);
        specialty.RemoveAllExclude();
        specialty.clicRefreshButton();
        assertThat(specialty.getNoRecordsFoundTextInSelectedTable()).isEqualTo(SELECTED_TABLE_NO_RECORDS_FOUND_TEXT);
    }

    @Test(description = "Query Builder => Stratifier => Query Builder => Criteria Provider => Speciality Tab, " +
            "Include All On Page and assert if Included codes are actually included.")
    public void includeAllOnPageFunctionalityTest() {
        specialty.IncludeAllOnPage();
        String totalONPageCountInRemainingTable = specialty.getTotalONPageCountInRemainingTable();
        specialty.clicRefreshButton();
        String totalCountInSelectedTable = specialty.getTotalRecordsInSelectedTable();
        assertThat(totalONPageCountInRemainingTable).isEqualTo(totalCountInSelectedTable);
    }

    @Test(description = "Query Builder => Stratifier => Query Builder => Provider => Speciality Tab, " +
            "Exclude All On Page and assert if excluded codes are actually excluded.")
    public void excludeAllOnPageFunctionalityTest() {
        specialty.ExcludeAllOnPage();
        String totalONPageCountInRemainingTable = specialty.getTotalONPageCountInRemainingTable();
        specialty.clicRefreshButton();
        String totalCountInSelectedTable = specialty.getTotalRecordsInSelectedTable();
        assertThat(totalONPageCountInRemainingTable).isEqualTo(totalCountInSelectedTable);
    }

    @Test(description = "Query Builder => Stratifier => Query Builder => Provider => Speciality Tab, " +
            "Include All On Page, then Remove All Selected and assert that the selected codes are removed.")
    public void removeAllFunctionalityTestForInclude() {
        specialty.IncludeAllOnPage();
        specialty.clicRefreshButton();
        specialty.RemoveAllSelectedInclude();
        specialty.clicRefreshButton();
        assertThat(specialty.getNoRecordsFoundTextInSelectedTable()).isEqualTo(SELECTED_TABLE_NO_RECORDS_FOUND_TEXT);
      }

    @Test(description = "Query Builder => Stratifier => Query Builder => Provider => Speciality Tab, " +
            "Exclude All On Page, then Remove All Selected and assert that the selected codes are removed.")
    public void removeAllFunctionalityTestForExclude() {
        specialty.ExcludeAllOnPage();
        specialty.clicRefreshButton();
        specialty.RemoveAllSelectedExclude();
        specialty.clicRefreshButton();
        assertThat(specialty.getNoRecordsFoundTextInSelectedTable()).isEqualTo(SELECTED_TABLE_NO_RECORDS_FOUND_TEXT);
    }

    @AfterMethod
    public void afterEach(){
        specialty.reset();
    }
}


