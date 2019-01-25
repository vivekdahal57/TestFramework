package com.vh.mi.automation.test.pages.analytics.providerManagement.clinicManager;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridRow;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill.I670QualityAndRiskDrillPage;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill.I690QRMDetailDrillPage;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.Drill.ICP110PhysiciansDrillPage;
import com.vh.mi.automation.api.pages.analytics.providerManagement.clinicManager.ICP100;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.CP100;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.CP100Columns;
import com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.DrillPhysician.CP110PhysiciansDrillPageColumns;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 11/20/17.
 */

public class CP100DrillTest extends BaseTest {
    private ICP100 cp100;
    private ICP110PhysiciansDrillPage cp110;
    private I670QualityAndRiskDrillPage i670QualityAndRiskDrillPage;
    private I690QRMDetailDrillPage i690QRMDetailDrillPage;
    private IDataGrid dataGrid;

    @BeforeClass(description = "check 'blanks' in Clinic Manager =>Drills down to physician manager and check 'unknown blanks'")
    public void setUp() {
        super.setUp();
        cp100 = navigationPanel.doNavigateTo(CP100.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(cp100.getPageTitle());
        assertThat(cp100.getDataGrid().checkUnknownBlanksForColumn(CP100Columns.UNIT)).isTrue();
        cp110 = (ICP110PhysiciansDrillPage) cp100.drillDownToPage("Physicians");
        assertThat(cp110.getPageTitle().equalsIgnoreCase("(CP110) Physician Manager"));
        assertThat(cp110.getDataGrid().checkUnknownBlanksForColumn(CP110PhysiciansDrillPageColumns.UNIT)).isTrue();
        dataGrid = cp110.getDataGrid();
    }


    @Test(dataProvider = "drillOptionsProvider", priority = 0 , description = "Drills To DiseaseRegistry,Quality and risk Measures,Source Procedure,Profiler DashBoard and Individuals and check whether the page loads and table is empty ")
    public void clickingOnDrillOptionShouldOpenDrillPage(String drillOption) {
        IDrillPage drillPage;
        if(drillOption.equalsIgnoreCase("Individuals")){
            drillPage = dataGrid.getRows().get(0).doDrillBy(drillOption);
        }else{
            drillPage =  dataGrid.getRows().get(0).doDrillByOnSameWindow(drillOption);
        }

        drillPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());

        String actualPageTitle = drillPage.getDisplayedPageTitle();
        String expectedPageTitle = drillPage.getPageTitle();
        boolean isDrillPageValid = drillPage.isDrillPageValid();

        //Note it is essential that we close the drill page before asserting
        //because we don't want the close to be skipped because an assertion failed
        if(drillOption.equalsIgnoreCase("Individuals")) {
            drillPage.doClose();
        } else{
            drillPage.getPreferencesBar().backButton();
            cp110.doWaitTillFullyLoaded(defaultWaitTime);
        }
        Assertions.assertThat(actualPageTitle).isEqualTo(expectedPageTitle);
        Assertions.assertThat(isDrillPageValid).isTrue();
    }

    @Test(priority = 1, description = "Drills to Quality And Risk => Drills To Detail")
    public void drillDownToQualityAndRisk(){
        i670QualityAndRiskDrillPage = (I670QualityAndRiskDrillPage)cp110.drillDownToPage("Quality and Risk Measures");
        assertThat(i670QualityAndRiskDrillPage.getPageTitle().equalsIgnoreCase("(670) Quality & Risk"));
        i690QRMDetailDrillPage = (I690QRMDetailDrillPage)i670QualityAndRiskDrillPage.drillDownToPage("Detail");
        assertThat(i690QRMDetailDrillPage.getPageTitle().equalsIgnoreCase("(690) QRM Detail"));
    }

    @DataProvider(name = "drillOptionsProvider")
    public  Object[][] drillOptionsProvider() {
        List<String> valuesToBeRemoved = new ArrayList<>();
        valuesToBeRemoved.add("Month");
        valuesToBeRemoved.add("Quarter");
        valuesToBeRemoved.add("Year");
        IDataGridRow firstRow = dataGrid.getRows().get(0);
        ImmutableList<String> drillOptions = firstRow.getDrillOptions();

        if (!firstRow.hasDrillOptions()) {
            throw new RuntimeException("First Row does not have drill options");
        }

        List<String> drillListToBeUsed = drillOptions.stream()
                .filter(e -> (valuesToBeRemoved.stream().filter(d -> d.equals(e)).count())<1)
                .collect(Collectors.toList());

        List<String> drillOptionsList = new ArrayList<>();
            for (String drillOption : drillListToBeUsed) {
                drillOptionsList.add(drillOption);
            }
            return DataProviderUtils.getObjects(drillOptionsList);
        }
    }



