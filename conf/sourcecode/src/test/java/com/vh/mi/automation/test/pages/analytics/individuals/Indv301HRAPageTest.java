package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardHealthRiskAssessmentData;
import com.vh.mi.automation.test.base.BaseTest;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10314 on 8/24/2017.
 */
@Test(groups = "Product-Critical")
public class Indv301HRAPageTest extends BaseTest {
    private static final String INDIVIDUAL_ID = "220211591";
    private static final String HRA_HEIGHT = "5";
    private static final String EDIT_HRA_HEIGHT = "6";

    private static final String HRA_SMOKE_STATUS = "YES";
    private static final String EDIT_HRA_SMOKE_STATUS = "NO";

    private static final String HRA_WEIGHT = "200";
    private static final String EDIT_HRA_WEIGHT = "250";

    private Indv301 indv301;
    private IndividualDashboardHealthRiskAssessmentData indvHRA;
    private User user;

    private List<Pair<String, String>> FIELDS = new ArrayList<Pair<String, String>>();
    private List<Pair<String, String>> EDIT_FIELDS = new ArrayList<Pair<String, String>>();

    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setup(){
        super.setUp();
        user = getUser("miautomation_indv_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());

        FIELDS.add(Pair.of("Height in Inch", HRA_HEIGHT));
        FIELDS.add(Pair.of("Smoke Yes or No", HRA_SMOKE_STATUS));
        FIELDS.add(Pair.of("Weight in Pound", HRA_WEIGHT));

        EDIT_FIELDS.add(Pair.of("Height in Inch", EDIT_HRA_HEIGHT));
        EDIT_FIELDS.add(Pair.of("Smoke Yes or No", EDIT_HRA_SMOKE_STATUS));
        EDIT_FIELDS.add(Pair.of("Weight in Pound", EDIT_HRA_WEIGHT));
    }

    @Test(description = "Navigate to Analytics => Individuals and Drill to HRA page for any individual ID, add values" +
            "in some fields and assert if they are added successfully, again modify some values and assert if they are " +
            "modified successfully.")
    public void hraPageAddAndModifyVerificationTest(){
        indv301 = navigationPanel.doNavigateTo(Indv301.class,defaultWaitTime);
        indv301.popupExists();
        indvHRA = indv301.goToHRAPageFor(INDIVIDUAL_ID);
        indvHRA.addOrEditAndSaveTheseFields(FIELDS);
        assertThat(indvHRA.savedDataSeenInHRAPage(FIELDS)).isTrue();

        String userID = user.getUserId();
        userID = userID.replaceAll("[_]","").replaceAll("\\s","").toLowerCase();
        assertThat(indvHRA.checkLastSavedByUser()).isEqualTo(userID);

        indvHRA.addOrEditAndSaveTheseFields(EDIT_FIELDS);
        assertThat(indvHRA.savedDataSeenInHRAPage(EDIT_FIELDS));
    }
}
