package com.vh.mi.automation.test.pages.queryBuilder.claimsSearch;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.queryBuilder.mvca.expert.IMVCAExpert301E;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.outReach.MemberListSummary;
import com.vh.mi.automation.impl.pages.queryBuilder.mvca.expert.MVCAExpert301E;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 7/7/2017.
 */
@Test (groups = "Product-Critical")
public class MVCADynamicMemberListTest extends BaseTest {
    IMVCAExpert301E mvcaExpertPage;
    private final static int TOP_N = 3;
    private final static String DYNAMIC_MEMBER_LIST_NAME = "AUTO_MVCA_DYNAMIC_MEM_LIST" + Random.getRandomStringOfLength(4);
    private static final String MVCA_QUERY_NAME = "AUTO_MVCA_Test_Query_" + Random.getRandomStringOfLength(4);
    public static final String REPORT_GENERATION_SUCCESSFUL_MESSAGE = "Criteria " + MVCA_QUERY_NAME + " saved.";
    MemberListSummary memberListSummary;

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

    @Test(description = "MVCA => create query => create Dynamic Member List => verify if member list is created and is present in memberListSummary => ")
    public void verifyDynamicMemberListFeatureTest(){
        mvcaExpertPage.applyMinimumQueryCriteriaRequired(IMVCAExpert301E.Group.DIAGNOSIS_GROUP);
        mvcaExpertPage.saveQuery(MVCA_QUERY_NAME);
        assertThat(mvcaExpertPage.getReportOperationSuccessfulMessage()).isEqualTo(REPORT_GENERATION_SUCCESSFUL_MESSAGE);
        mvcaExpertPage.createDymanicMemberList(TOP_N,DYNAMIC_MEMBER_LIST_NAME);
        assertThat(mvcaExpertPage.getMemberList().checkIfMemberListExists(DYNAMIC_MEMBER_LIST_NAME)).isTrue();

        //verify created memberlist is present in memberlistsummary page
        memberListSummary = navigationPanel.doNavigateTo(MemberListSummary.class, defaultWaitTime);
        assertThat(memberListSummary.isMemberListPresent(DYNAMIC_MEMBER_LIST_NAME)).isTrue();

        //Delete MemberList
        mvcaExpertPage = navigationPanel.doNavigateTo(MVCAExpert301E.class,defaultWaitTime);
        mvcaExpertPage.deleteQueryWithName(MVCA_QUERY_NAME);
        assertThat(mvcaExpertPage.getReportOperationSuccessfulMessage().equals(mvcaExpertPage.getExpectedQueryDeletionMessage()));
        mvcaExpertPage.getMemberList().deleteMyMemberListWithName(DYNAMIC_MEMBER_LIST_NAME);
        assertThat(mvcaExpertPage.getMemberList().getOperationCompleteStatusMessage()).isEqualToIgnoringCase(mvcaExpertPage.getMemberList().getExpectedMessageForMemberListDeletion());
    }
}
