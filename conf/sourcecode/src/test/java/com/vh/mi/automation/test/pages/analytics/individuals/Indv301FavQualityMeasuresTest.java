package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.comp.favqm.FavouiteQualityMeasures;
import com.vh.mi.automation.impl.comp.favqm.FavouriteQualityMeasuresPopUp;
import com.vh.mi.automation.impl.comp.favqm.ListScope;
import com.vh.mi.automation.impl.comp.favqm.QualityMeasuresType;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301DataGrid;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 7/26/2017.
 */
@Test(groups = "Product-Critical")
public class Indv301FavQualityMeasuresTest extends BaseTest {
    private IIndv301 indv301;
    FavouiteQualityMeasures favouiteQualityMeasures;
    FavouriteQualityMeasuresPopUp qualityMeasuresPopUp;
    Integer[] rowsToSelect = { 2, 1 };
    private static final String FAV_QM_LIST_NAME_HEDIS_QRM = "AUTO_HEDIS_QRM_LIST_" + Random.getRandomStringOfLength(3);

    @BeforeClass
    public void setUp(){
        super.setUp();
        indv301 = navigationPanel.doNavigateTo(Indv301.class, defaultWaitTime);
        indv301.popupExists();
    }

    @Test(description = "Individuals(301) => Quality Measures => Create List => Quality Measure Sets(Select QRM Dropdown) =>" +
            "Select Few QRM Measure => Select HEDIS individualDropdown => Select Few HEDIS Measure And Save => " +
            "Check If selected measure is applied in 301 page")
    public void testThatQRMAndHEDISMeasureIsApplied(){
        favouiteQualityMeasures = indv301.getFavouiteQualityMeasures();
        qualityMeasuresPopUp = favouiteQualityMeasures
                .openCreateListComponent();
        qualityMeasuresPopUp
                .selectQualityMeasures(QualityMeasuresType.QRM, rowsToSelect)
                .selectQualityMeasures(QualityMeasuresType.HEDIS, rowsToSelect)
                .save(FAV_QM_LIST_NAME_HEDIS_QRM, ListScope.PRIVATE, false);

        assertThat(qualityMeasuresPopUp.getQualityMeasuresSavedStatusMessage()).
                isEqualToIgnoringCase(qualityMeasuresPopUp.getExpectedMessageForQualityMeasuresSaved(FAV_QM_LIST_NAME_HEDIS_QRM));
        List<String> selectedHEDIS = qualityMeasuresPopUp.getSelectedHedis();
        Indv301DataGrid dataGrid = ((Indv301DataGrid)indv301.getDataGrid());
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

        //delete measures
        favouiteQualityMeasures
                .deleteList(FAV_QM_LIST_NAME_HEDIS_QRM, ListScope.PRIVATE);
        assertThat(favouiteQualityMeasures.getDeletionCompleteStatusMessage())
                .isEqualToIgnoringCase("The saved Quality Measures list " + FAV_QM_LIST_NAME_HEDIS_QRM + " has been successfully removed.");
    }
}
