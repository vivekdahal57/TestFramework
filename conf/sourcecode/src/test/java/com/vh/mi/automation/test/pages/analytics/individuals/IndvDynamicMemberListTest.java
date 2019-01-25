package com.vh.mi.automation.test.pages.analytics.individuals;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 6/23/2017.
 */
@Test (groups = { "Product-Critical" })
public class IndvDynamicMemberListTest extends BaseTest {

    private IIndv301 indv301;
    private final static int TOP_N = 5;
    private final static String DYNAMIC_MEMBER_LIST_NAME = "AUTO_INDV_DYNAMIC_MEM_LIST" + Random.getRandomStringOfLength(4);

    @Override
    public boolean skipLogin(){
        return true;
    }

    @BeforeClass
    public void setUp(){
        super.setUp();
        User user = getUser("miautomation_memberlist_user");
        loginAndSelectFront(user,context.getDefaultWaitTimeout());
        indv301 = navigationPanel.doNavigateTo(Indv301.class,defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(indv301.getPageTitle());
        indv301.popupExists();
    }

    @Test
    public void dynamicMemberListGenerationTest(){
        indv301.createDymanicMemberList(TOP_N,DYNAMIC_MEMBER_LIST_NAME);
        assertThat(indv301.getMemberList().checkIfMemberListExists(DYNAMIC_MEMBER_LIST_NAME)).isTrue();
        assertThat(indv301.checkIfMemberListIsNotEmpty()).describedAs("Member list is Empty.").isTrue();

        //Delete MemberList
        indv301.getMemberList().deleteMyMemberListWithName(DYNAMIC_MEMBER_LIST_NAME);
        assertThat(indv301.getMemberList().getOperationCompleteStatusMessage()).isEqualToIgnoringCase(indv301.getMemberList().getExpectedMessageForMemberListDeletion());
    }

}
