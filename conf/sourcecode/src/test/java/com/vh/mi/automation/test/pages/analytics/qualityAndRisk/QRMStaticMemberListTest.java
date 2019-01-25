package com.vh.mi.automation.test.pages.analytics.qualityAndRisk;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.individuals.IIndv301;
import com.vh.mi.automation.api.pages.analytics.qualityMeasures.qualityAndRisk.IQualityAndRisk;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.analytics.individuals.Indv301;
import com.vh.mi.automation.impl.pages.analytics.qualityMeasures.qualityAndRisk.QualityAndRisk670;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 6/26/2017.
 */
public class QRMStaticMemberListTest extends BaseTest {

    private IQualityAndRisk qualityAndRisk;
    private IIndv301 indv301;
    private final static int TOP_FIVE = 5;
    private final static String STATIC_MEMBER_LIST_NAME = "AUTO_QRM_STATIC_MEM_LIST" + Random.getRandomStringOfLength(4);

    @Override
    public boolean skipLogin(){
        return true;
    }

    @BeforeClass
    public void setUp(){
        super.setUp();
        User user = getUser("miautomation_memberlist_user");
        loginAndSelectFront(user,context.getDefaultWaitTimeout());
        qualityAndRisk = navigationPanel.doNavigateTo(QualityAndRisk670.class, defaultWaitTime);
    }

    @Test
    public void staticMemberGenerationTest(){
        qualityAndRisk.createListWithSelectedMember(TOP_FIVE,STATIC_MEMBER_LIST_NAME);
        assertThat(qualityAndRisk.getMemberListSuccessfullyCreatedStatus()).isEqualToIgnoringCase("Memberlist created");
        indv301 = navigationPanel.doNavigateTo(Indv301.class,defaultWaitTime);
        assertThat(indv301.getMemberList().checkIfMemberListExists(STATIC_MEMBER_LIST_NAME)).isTrue();
        assertThat(indv301.checkIfMemberListIsNotEmpty()).isTrue();

        //CleanUp(Delete) MemberList
        indv301.getMemberList().deleteMyMemberListWithName(STATIC_MEMBER_LIST_NAME);
        assertThat(indv301.getMemberList().getOperationCompleteStatusMessage()).isEqualToIgnoringCase(indv301.getMemberList().getExpectedMessageForMemberListDeletion());

    }

}
