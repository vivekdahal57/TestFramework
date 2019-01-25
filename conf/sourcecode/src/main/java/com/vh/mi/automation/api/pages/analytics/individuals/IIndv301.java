package com.vh.mi.automation.api.pages.analytics.individuals;

import com.vh.mi.automation.api.comp.IPreferencesBar;
import com.vh.mi.automation.api.comp.dataGrid.IDataGrid;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDataGridCustomizer;
import com.vh.mi.automation.api.comp.dataGrid.customizer.IDxcgDataGridCustomizer;
import com.vh.mi.automation.api.comp.extractDownloadPopup.ICSVExtractPopUp;
import com.vh.mi.automation.api.comp.extractDownloadPopup.ISendToExcelPopUp;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.features.*;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDahsboardHealthAnalysisSummary;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.IndividualDashboardHealthRiskAssessmentData;
import com.vh.mi.automation.impl.pages.outReach.letterGeneration.SelectTemplatePage;

import java.io.IOException;

/**
 * Created by i80448 on 9/17/2014.
 */
public interface IIndv301 extends IAmLandingPage, IHaveBusinessLevel, IHaveAnalysisPeriod, IHaveReportingBy,IHavePaginationComponent,IHaveFavoriteQualityMeasures, IHaveMemberList {

    public void popupExists();

    public IDataGrid getDataGrid();

    public boolean isDataGridCustomizable();

    public IDataGridCustomizer getDataGridCustomizer();

    public IDxcgDataGridCustomizer getDxcgDataGridCustomizer();

    public ICSVExtractPopUp getCSVExtractPopUp();

    public ISendToExcelPopUp getSendToExcelPopUp();

    public boolean isDxCGEnabled();

    public void createListWithSelectedMember(int topN,String memListName);

    public boolean checkIfStaticMemberListIsSucessfullyCreated(String memListName);

    public void createDymanicMemberList(int topN,String memListName);

    public boolean checkIfMemberListIsNotEmpty();

    public boolean updatedDataSeenInIndividualPage(String fieldName, String updateText);

    public IndividualDahsboardHealthAnalysisSummary goToHASPageFor(String indvID);

    public IndividualDashboardHealthRiskAssessmentData goToHRAPageFor(String indvID);

    public boolean sendToExcelAndValidate(String excelFileName, ExecutionContext context)throws IOException;

    public boolean sendToExcelAndValidateZipFile(String excelFileName, ExecutionContext context) throws IOException;

    public boolean isExcelOffline();

//  need to uncomment for verifing dynamic member list
// public MemberList getMemberListComponent();

    }
