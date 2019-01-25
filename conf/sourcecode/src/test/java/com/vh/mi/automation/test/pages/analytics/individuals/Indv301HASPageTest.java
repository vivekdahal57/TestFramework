package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.constants.TimeOuts;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDahsboardHealthAnalysisSummary;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i10314 on 8/2/2017.
 */
@Test(groups = "Product-Critical")
public class Indv301HASPageTest extends BaseTest {
    private Indv301 indv301;
    private IndividualDahsboardHealthAnalysisSummary indvHAS;
    private static final String INDIVIDUAL_ID = "CC_220068523";
    private static final String RECORD_SUCCESSFULLY_UPDATED_TEXT = "HAS Record Updated.";

    private static final String FIELD_NAME_DM_STATUS = "DM Status";
    private static final String FIELD_NAME_DM_STATUS_INDIVIDUAL_PAGE = "DM_STATUS";
    private static final String UPDATE_TEXT_DM_STATUS = "DMTest";

    private static final String FIELD_NAME_DM_FOLLOW_UP = "DM Follow-Up Action";
    private static final String FIELD_NAME_DM_FOLLOW_UP_INDIVIDUAL_PAGE = "DM_FOLLOWUP_ACTION";
    private static final String UPDATE_TEXT_DM_FOLLOW_UP = "CALL MEMBER";

    private static final String FIELD_NAME_CM_STATUS = "CM Status";
    private static final String FIELD_NAME_CM_STATUS_INDIVIDUAL_PAGE = "CMSTATUS";
    private static final String UPDATE_TEXT_CM_STATUS = "CMTest";

    private User user;

    @Override
    protected boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        user = getUser("miautomation_indv_user");
        super.loginAndSelectFront(user, defaultWaitTime);
        indv301 = navigationPanel.doNavigateTo(Indv301.class, TimeOuts.SIXTY_SECONDS);
        indv301.popupExists();
    }

    @Test(priority = 1, groups = "Product-Critical", description = "Navigate to Analytics => Individuals => HAS page for any individual and modify some data" +
            "then save and assert if the changes are seen in HAS page, also assert if those changes are seen in Individual page.")
    public void verifyHASPageWhenModifiedTest() {

        indvHAS = indv301.goToHASPageFor(INDIVIDUAL_ID);

        indvHAS.updateData(FIELD_NAME_DM_STATUS, UPDATE_TEXT_DM_STATUS);
        indvHAS.updateData(FIELD_NAME_DM_FOLLOW_UP, UPDATE_TEXT_DM_FOLLOW_UP);
        indvHAS.updateData(FIELD_NAME_CM_STATUS, UPDATE_TEXT_CM_STATUS);

        assertThat(indvHAS.getUpdatedDataSavedSuccessfullyText()).isEqualTo(RECORD_SUCCESSFULLY_UPDATED_TEXT);

        assertThat(indvHAS.updatedDataSeenInHASPage(FIELD_NAME_DM_STATUS, UPDATE_TEXT_DM_STATUS)).isTrue();
        assertThat(indvHAS.updatedDataSeenInHASPage(FIELD_NAME_DM_FOLLOW_UP, UPDATE_TEXT_DM_FOLLOW_UP)).isTrue();
        assertThat(indvHAS.updatedDataSeenInHASPage(FIELD_NAME_CM_STATUS, UPDATE_TEXT_CM_STATUS)).isTrue();

        String userID = user.getUserId();
        userID = userID.replaceAll("[_]","").replaceAll("\\s","").toLowerCase();
        assertThat(indvHAS.checkLastSavedByUser()).isEqualTo(userID);

        indvHAS.closeHASPageAndGoBackToIndividualPage();

        IDataGridCustomizer columnCustomizer = indv301.getDataGridCustomizer();
        columnCustomizer.doSelectAll();
        columnCustomizer.doApplySelection();

        assertThat(indv301.updatedDataSeenInIndividualPage(FIELD_NAME_DM_STATUS_INDIVIDUAL_PAGE, UPDATE_TEXT_DM_STATUS)).isTrue();
        assertThat(indv301.updatedDataSeenInIndividualPage(FIELD_NAME_DM_FOLLOW_UP_INDIVIDUAL_PAGE, UPDATE_TEXT_DM_FOLLOW_UP)).isTrue();
        assertThat(indv301.updatedDataSeenInIndividualPage(FIELD_NAME_CM_STATUS_INDIVIDUAL_PAGE, UPDATE_TEXT_CM_STATUS)).isTrue();

    }
}
