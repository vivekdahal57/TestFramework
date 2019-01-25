package com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.customPerformanceMeasures.ICPM01;
import com.vh.mi.automation.impl.comp.favqm.FavouiteQualityMeasures;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.vh.mi.automation.api.utils.Random.getRandomStringOfLength;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10359 on 1/30/18.
 */
public class CPM01CreateFavouriteViewTest extends BaseTest {
    private ICPM01 cpm01;
    private IBusinessLevelsComponent businessLevelsComponent;
    Map<String, String> normSelection = new HashMap<>();



    @BeforeClass
    public void setUp(){
        super.setUp();
        cpm01 = navigationPanel.doNavigateTo(CPM01.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(cpm01.getPageTitle());

    }

    @Test(dataProvider  = "drillOptionsProviderForNormSelection", description = "make a map of drillOption and view name to be saved => select the option from Norm Selection => save the Selected Norm withe view name => validate the saved norm and delete ")
    public void favouriteViewTest(String drillOption){

        normSelection.put(drillOption, drillOption +randomValue());

            cpm01.hoverAndClickNormSelection(drillOption);
            cpm01.doWaitTillPopUpDisappears();

        cpm01.saveNormSelection(normSelection.get(drillOption));
       cpm01.savedNormValidation(drillOption, normSelection.get(drillOption));

    }


    public static String randomValue() {
        return getRandomStringOfLength(2);
    }


    @DataProvider(name = "drillOptionsProviderForNormSelection")
    public  Object[][] drillOptionsProviderForNormSelection() {
        List<String> drillOptions = cpm01.getNormSelectionList();
        return DataProviderUtils.getObjects(drillOptions);
    }

}
