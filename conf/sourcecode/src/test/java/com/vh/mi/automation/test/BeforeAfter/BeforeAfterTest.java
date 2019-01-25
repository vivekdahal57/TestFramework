package com.vh.mi.automation.test.BeforeAfter;

import com.vh.mi.automation.api.comp.IQueryProfiler;
import com.vh.mi.automation.api.constants.MILandingPages;
import com.vh.mi.automation.api.constants.SwitchBoard;
import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.api.features.IAmLandingPage;
import com.vh.mi.automation.groovy.Module;
import com.vh.mi.automation.impl.comp.QueryProfiler;
import com.vh.mi.automation.impl.reporting.HTMLContents;
import com.vh.mi.automation.impl.reporting.HTMLReportWriter;
import com.vh.mi.automation.impl.utils.BeforeAfterUtils;
import com.vh.mi.automation.test.comp.navigationPanel.NavigationPanelBaseTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by i81306 on 1/10/2018.
 */
@Test(groups = "Before-After")
public class BeforeAfterTest extends NavigationPanelBaseTest {
    //private Map<String, Set<String>>  mismatchTables = new HashMap<>();
    LinkedList<String[]> tableHTMLData = new LinkedList<String[]>();
    private IAmLandingPage miLandingPage;
    private IQueryProfiler queryProfiler;

    @Override
    protected boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        User user = getUser("miautomation_beforeAfter_user");
        loginAndSelectFront(user,context.getDefaultWaitTimeout());
    }

    @Test(dataProvider = "analyticsModulesProvider")
    public void sampleBeforeAfterTest(Module link){
        Set<String> tables = new HashSet<>();

        MILandingPages page = MILandingPages.byId(link.getId()).get();
        miLandingPage = navigationPanel.doNavigateTo(page.getLandingPageClass(), defaultWaitTime);
        assertThat(miLandingPage.getPageTitle()).isEqualTo(page.getPageTitle());

        miLandingPage.doCustomizeAllColumns();
        queryProfiler = new QueryProfiler(webDriver);
        assertThat(queryProfiler.isFullyLoaded()).isTrue();

        List<String> queriesToExecute = queryProfiler.getSchemaQueries();
        for(String sql : queriesToExecute){
            tables.addAll(BeforeAfterUtils.compareQueryForMisMatchTables(sql));
        }

        if(tables.size() > 0){
            //mismatchTables.put(miLandingPage.getClass().toString(), tables);
            tableHTMLData.add(new String[]{page.getPageTitle(), tables.toString()});

        }
        assertThat(tables.size()).isEqualTo(0);
    }

    @AfterClass
    public void testTest(){
        new HTMLReportWriter().generateHTMLReport(tableHTMLData, HTMLContents.BEFORE_AFTER_BODY, HTMLContents.BEFORE_AFTER_FILE_NAME);
    }

    @DataProvider(name = "analyticsModulesProvider")
    protected Object[][] analyticsModulesProvider() {
        List<Module> allModules = new ArrayList<>();
            for (Module module : navigationPanelTestHelper.getModules(SwitchBoard.ANALYTICS)) {
                allModules.addAll(module.getLeafModules());
            }
        return DataProviderUtils.getObjects(allModules);
    }
}
