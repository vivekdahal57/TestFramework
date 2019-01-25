package com.vh.mi.automation.test.pages.queryBuilder.claimsSearch;

import com.vh.mi.automation.api.dto.User;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.Diagnosis.DiagnosisGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.General.ClaimsLevel;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.QueryBuilder;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.RefineLogic;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.Rx.RxGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.placeOfService.PlaceOfService;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.procedure.ProcedureGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.provider.ProviderGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.queryManagement.QueryManagement;
import com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.specialty.SpecialtyGroup;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.provider.Specialty;
import com.vh.mi.automation.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Created by i81306 on 5/5/2017.
 */
@Test (groups = { "Report-Generation", "Component-Interaction" })
public class ClaimsSearchExtractGenerationTest extends BaseTest {
    private static final String DEFAULT_QUERY_NAME = "CLAIMS_SEARCH_EXTRACT_TEST";
    private static final String CLAIM_TYPE = "All";
    private static final int TOP_ONE = 1;

    QueryBuilder queryBuilder;
    DiagnosisGroup diagnosisGroup;
    ProcedureGroup procedureGroup;
    ProviderGroup providerGroup;
    RxGroup rxGroup;
    SpecialtyGroup specialtyGroup;
    PlaceOfService placeOfService;
    ClaimsLevel claimsLevel;
    RefineLogic refineLogic;

    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass()
    public void setUp() {
        super.setUp();
        User user = getUser("miautomation_reports_user");
        loginAndSelectFront(user, context.getDefaultWaitTimeout());
        queryBuilder = navigationPanel.doNavigateTo(QueryBuilder.class, defaultWaitTime);
        assertThat(navigationPanel.getCurrentPageTitle()).isEqualTo(queryBuilder.getPageTitle());
    }

    @Test
    public void extractGenerationTest(){
        diagnosisGroup = queryBuilder.goToCriteria(DiagnosisGroup.class);
        diagnosisGroup.includeTopNGroups(TOP_ONE);

        procedureGroup = queryBuilder.goToCriteria(ProcedureGroup.class);
        procedureGroup.includeTopNGroups(TOP_ONE);

        providerGroup = queryBuilder.goToCriteria(ProviderGroup.class);
        providerGroup.includeTopNGroups(TOP_ONE);

        specialtyGroup = queryBuilder.goToCriteria(SpecialtyGroup.class);
        specialtyGroup.includeTopNGroups(TOP_ONE);

        placeOfService = queryBuilder.goToCriteria(PlaceOfService.class);
        placeOfService.includeTopNGroups(TOP_ONE);

        rxGroup = queryBuilder.goToCriteria(RxGroup.class);
        rxGroup.includeTopNGroups(TOP_ONE);

        claimsLevel = rxGroup.goToCriteria(ClaimsLevel.class);
        claimsLevel.selectClaimType(CLAIM_TYPE);

        refineLogic = claimsLevel.goToRefineLogicClaimsSearch();
        refineLogic.selectExtraOptions();
        refineLogic.includeAllFieldsFor("General");
        refineLogic.applyExtraOptions();

        refineLogic.changeExpressionToOR();

        refineLogic.saveQueryWithDefaultOptionsWithName(DEFAULT_QUERY_NAME);
        assertThat(refineLogic.getTextFromExtractGenerationPopupMessage().equals(refineLogic.getSuccessfulExtractGenerationMessage()));
    }
}
