package com.vh.mi.automation.test.pages.queryBuilder.claimsSearch;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.queryBuilder.mvca.expert.IMVCAExpert301E;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.outReach.MemberListSummary;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.Diagnosis.DiagnosisGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.General.ClaimsLevel;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.QueryBuilder;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.RefineLogic;
import com.vh.mi.automation.impl.pages.queryBuilder.mvca.expert.MVCAExpert301E;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 6/26/2017.
 */
@Test (groups = { "Report-Generation", "Product-Critical", "Component-Interaction"})
public class ClaimsSearchExtractFromMVCAMemberList extends BaseTest {
    IMVCAExpert301E mvcaExpertPage;
    QueryBuilder queryBuilder;
    DiagnosisGroup diagnosisGroup;
    ClaimsLevel claimsLevel;
    RefineLogic refineLogic;
    MemberListSummary memberListSummary;

    private static final String MVCA_QUERY_NAME = "MVCA_Test_Query_" + Random.getRandomStringOfLength(4);
    private static final String MVCA_MEMBERLIST_NAME = "MVCA_Test_MemberList_" + Random.getRandomStringOfLength(4);
    private static final String CS_REPORT_NAME = "CS_MVCA_REPORT_TEST";
    public static final String REPORT_GENERATION_SUCCESSFUL_MESSAGE = "Criteria " + MVCA_QUERY_NAME + " saved.";
    public static final String MEMBERlIST_OPERATION_SUCCESSFUL_MESSAGE = "Member List " + MVCA_MEMBERLIST_NAME + " created";

    public static final String EXPECTED_REPORT_SUCCESSFUL_MESSAGE = "Query " + CS_REPORT_NAME + " has been saved.";
    private static final int TOP_N = 5;

    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp(){
        super.setUp();
        User user = getUser("miautomation_memberlist_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        mvcaExpertPage = navigationPanel.doNavigateTo(MVCAExpert301E.class,defaultWaitTime);
    }

    @Test
    public void claimsSearchExtractCanBeGeneratedFromEMVCAMemberlistTest(){
        mvcaExpertPage.applyMinimumQueryCriteriaRequired(IMVCAExpert301E.Group.DIAGNOSIS_GROUP);
        mvcaExpertPage.saveQuery(MVCA_QUERY_NAME);
        assertThat(mvcaExpertPage.getReportOperationSuccessfulMessage()).isEqualTo(REPORT_GENERATION_SUCCESSFUL_MESSAGE);
        mvcaExpertPage.createStaticMemberlistWithTopNMembers(MVCA_MEMBERLIST_NAME, TOP_N);
        assertThat(mvcaExpertPage.getMemberList().getOperationSuccessfulMessage()).isEqualTo(MEMBERlIST_OPERATION_SUCCESSFUL_MESSAGE);

        //verify created memberlist is present in memberlistsummary page
        memberListSummary = navigationPanel.doNavigateTo(MemberListSummary.class, defaultWaitTime);
        assertThat(memberListSummary.isMemberListPresent(MVCA_MEMBERLIST_NAME)).isTrue();

        queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(queryBuilder.getPageTitle());

        diagnosisGroup = queryBuilder.goToCriteria(DiagnosisGroup.class);
        diagnosisGroup.includeTopNGroups(TOP_N);
        claimsLevel = diagnosisGroup.goToCriteria(ClaimsLevel.class);
        claimsLevel.selectMemberListFromMVCA(MVCA_MEMBERLIST_NAME);

        refineLogic = claimsLevel.goToRefineLogicClaimsSearch();
        refineLogic.selectExtraOptions();
        refineLogic.includeAllFieldsFor("General");
        refineLogic.applyExtraOptions();
        refineLogic.saveQueryWithDefaultOptionsWithName(CS_REPORT_NAME);
        //assertThat(refineLogic.getTextFromExtractGenerationPopupMessage()).isEqualTo
                //(EXPECTED_REPORT_SUCCESSFUL_MESSAGE);

        //delete generated reports during tests
        mvcaExpertPage = navigationPanel.doNavigateTo(MVCAExpert301E.class,defaultWaitTime);
        mvcaExpertPage.deleteQueryWithName(MVCA_QUERY_NAME);
        assertThat(mvcaExpertPage.getReportOperationSuccessfulMessage().equals(mvcaExpertPage.getExpectedQueryDeletionMessage()));
        mvcaExpertPage.getMemberList().deleteMyMemberListWithName(MVCA_MEMBERLIST_NAME);
        assertThat(mvcaExpertPage.getMemberList().getOperationCompleteStatusMessage()).isEqualToIgnoringCase(mvcaExpertPage.getMemberList().getExpectedMessageForMemberListDeletion());

    }
}
