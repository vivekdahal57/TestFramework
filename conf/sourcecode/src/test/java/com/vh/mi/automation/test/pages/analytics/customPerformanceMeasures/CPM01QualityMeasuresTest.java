package com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.comp.favqm.FavouiteQualityMeasures;
import com.vh.mi.automation.impl.comp.favqm.FavouriteQualityMeasuresPopUp;
import com.vh.mi.automation.impl.comp.favqm.ListScope;
import com.vh.mi.automation.impl.comp.favqm.QualityMeasuresType;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01DataGrid;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 2/7/18.
 */
public class CPM01QualityMeasuresTest extends BaseTest {
    private CPM01 cpm01;
    FavouiteQualityMeasures favouiteQualityMeasures;
    FavouriteQualityMeasuresPopUp qualityMeasuresPopUp;
    private static final String FAV_QM_LIST_NAME_QRM = "test_QRM_LIST12435_" + Random.getRandomStringOfLength(3);
    Integer[] rowsToSelect = { 2, 1 };


    @BeforeClass
    public void setUp(){
        super.setUp();
        cpm01 = navigationPanel.doNavigateTo(CPM01.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(cpm01.getPageTitle());
        favouiteQualityMeasures = cpm01.getFavouiteQualityMeasures();
    }

    @Test
    public void test(){
        qualityMeasuresPopUp = favouiteQualityMeasures
                .openCreateListComponent();
        qualityMeasuresPopUp
                .selectQualityMeasures(QualityMeasuresType.QRM, rowsToSelect)
                .save(FAV_QM_LIST_NAME_QRM, ListScope.PRIVATE, false);
        assertThat(qualityMeasuresPopUp.getQualityMeasuresSavedStatusMessage()).isEqualToIgnoringCase(qualityMeasuresPopUp.getExpectedMessageForQualityMeasuresSaved(FAV_QM_LIST_NAME_QRM));

        List<String> selectedQualityMeasures = qualityMeasuresPopUp.selectedQualityMeasures();

        for(String  QualityMeasures : cpm01.qualityMeasuresElements() ){
            assertThat(selectedQualityMeasures).contains(QualityMeasures);
        }
        favouiteQualityMeasures.deleteList(FAV_QM_LIST_NAME_QRM, ListScope.PRIVATE);
    }

}
