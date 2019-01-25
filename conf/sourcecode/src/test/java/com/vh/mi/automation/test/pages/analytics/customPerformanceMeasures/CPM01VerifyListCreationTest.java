package com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures;

import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.analytics.customPerformanceMeasures.CPM01;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10314 on 8/21/2017.
 */
@Test(groups = "Product-Critical")
public class CPM01VerifyListCreationTest extends BaseTest {
    public static final String QNR_FIRST_CRITERIA = "12027";
    public static final String QNR_SECOND_CRITERIA = "12054";
    public static final String HEDIS_FIRST_CRITERIA = "13-0";
    public static final String HEDIS_SECOND_CRITERIA = "131-1";
    public static final String LIST_NAME = "AUTOMATION_LIST_" + Random.getRandomStringOfLength(4);
    public static final String SAVED_TEXT = "Quality Measures List ${List Name} is saved successfully.";
    private static final String LIST_NAME_PLACE_HOLDER = "${List Name}";

    List<String> QNR_CRITERIAS = new ArrayList<String>();
    List<String> HEDIS_CRITERIAS = new ArrayList<String>();
    List<String> ALL_CRITERIAS = new ArrayList<String>();


    CPM01 cpm01;

    @BeforeClass
    public void setup() {
        super.setUp();
        cpm01 = navigationPanel.doNavigateTo(CPM01.class, defaultWaitTime);

        QNR_CRITERIAS.add(QNR_FIRST_CRITERIA);
        QNR_CRITERIAS.add(QNR_SECOND_CRITERIA);
        HEDIS_CRITERIAS.add(HEDIS_FIRST_CRITERIA);
        HEDIS_CRITERIAS.add(HEDIS_SECOND_CRITERIA);
        ALL_CRITERIAS.addAll(QNR_CRITERIAS);
        ALL_CRITERIAS.addAll(HEDIS_CRITERIAS);
    }

    @Test(description = "Navigate to Custom Performance Measures => Hover to Quality Measures and click Create List => Select some" +
            "QRM and HEDIS Measures and save with an appropriate name => Assert if the List is saved successfully and also Assert" +
            "if the selected Measures are applied successfully in CPM01 form.")
    public void verifyListCreationWithSelectedCriteria() {
        cpm01.selectQNRCriterias(QNR_CRITERIAS);
        cpm01.selectHEDISCriterias(HEDIS_CRITERIAS);
        assertThat(cpm01.saveCreatedList(LIST_NAME)).isEqualTo(SAVED_TEXT.replace(LIST_NAME_PLACE_HOLDER, LIST_NAME));
        assertThat(cpm01.checkIfTheseCriteriasAreAppliedSuccessfully(ALL_CRITERIAS)).isTrue();
    }
}
