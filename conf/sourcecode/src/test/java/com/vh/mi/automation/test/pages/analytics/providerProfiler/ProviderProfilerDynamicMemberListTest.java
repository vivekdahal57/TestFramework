package com.vh.mi.automation.test.pages.analytics.providerProfiler;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.Drill.Provider.IIndividualDrillPage;
import com.vh.mi.automation.api.pages.analytics.providerProfiler.IProviderProfilerV044;
import com.vh.mi.automation.api.utils.Random;
import com.vh.mi.automation.impl.pages.analytics.providerProfiler.ProviderProfilerV044;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i82716 on 6/27/2017.
 */
@Test (groups = { "Product-Critical", "Report-Generation" })
public class ProviderProfilerDynamicMemberListTest extends BaseTest {
    IProviderProfilerV044 providerProfiler;
    private static final String SPECIALTY_NAME = "Hospital";
    private IIndividualDrillPage individualDrillPage;

    private final static int TOP_N = 3;
    private final static String DYNAMIC_MEMBER_LIST_NAME = "AUTO_PROVIDER_PROFILER_DYNAMIC_MEM_LIST" + Random.getRandomStringOfLength(4);

    @Override
    public boolean skipLogin(){
        return true;
    }

    @BeforeClass
    public void setUp(){
        super.setUp();
        User user = getUser("miautomation_memberlist_user");
        loginAndSelectFront(user,context.getDefaultWaitTimeout());
        providerProfiler = navigationPanel.doNavigateTo(ProviderProfilerV044.class,defaultWaitTime);
    }

    @Test
    public void providerProfilerDynamicMemberListGeneration(){
        individualDrillPage = providerProfiler.drillDownFromProviderToIndividual(SPECIALTY_NAME);
        individualDrillPage.createDymanicMemberList(TOP_N,DYNAMIC_MEMBER_LIST_NAME);
        assertThat(individualDrillPage.getMemberList().checkIfMemberListExists(DYNAMIC_MEMBER_LIST_NAME)).isTrue();

        //CleanUp(Delete) MemberList
        assertThat(individualDrillPage.deleteMemberList(DYNAMIC_MEMBER_LIST_NAME)).isTrue();
    }

}
