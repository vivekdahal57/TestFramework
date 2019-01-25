package com.vh.mi.automation.api.pages.queryBuilder.stratifier.DemographicsAndRisk;

import com.vh.mi.automation.api.pages.common.IQuerybuilderPage;
import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.RefineLogic;

/**
 * Created by i81306 on 6/22/2017.
 */
public interface IDemographicsAndRiskCode extends IQuerybuilderPage{
    public void enterAge(Integer lowerAge, Integer upperAge);
    public RefineLogic goToRefineLogicPage();
    public Individual301 goToIndividualPage();
    public String getMembersAccordingToSelectedCriteria();
    public void selectGenderAndMemberStatus();
    public void enterRiskScores(Integer lowerValue, Integer UpperValue);

}
