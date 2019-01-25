package com.vh.mi.automation.api.pages.queryBuilder.stratifier;

import com.vh.mi.automation.impl.pages.queryBuilder.Individual301;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.CohortDetails;
import com.vh.mi.automation.impl.pages.queryBuilder.stratifier.PopulationAnalyzerCohortAnalyzer.CohortAnalyzer;

import java.util.List;

/**
 * Created by i20345 on 2/15/2017.
 */
public interface IPopulationAnalyser {
    public CohortDetails goToCohortDetails(String cohortDescription);

    public void filterWithCohortDescription(String cohortDescription);
    public void removeCohort(String cohortDescription);

    public Individual301 goToViewIndividual(String cohortDescription);
    public int getDataTableSize();
    public String getMemberTrendInfoSectionHeading();

    public CohortAnalyzer selectCreatedCohortsAndNavigateToCohortAnalyzer(List<String> cohortList);
    public String getCohortNumber(String cohortName);
    public void clickShowAllFavouriteCohorts();
    public boolean checkIfListedCohortsAreAllMarkedFavourite();
    public boolean checkIfCreatedCohortNumberExistsInFavouriteCohorts(String cohortNumber);

}
