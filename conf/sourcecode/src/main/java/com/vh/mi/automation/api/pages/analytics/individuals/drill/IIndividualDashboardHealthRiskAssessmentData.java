package com.vh.mi.automation.api.pages.analytics.individuals.drill;

import com.vh.mi.automation.api.pages.analytics.individuals.IIndividualDashboard;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Created by i10314 on 8/24/2017.
 */
public interface IIndividualDashboardHealthRiskAssessmentData extends IIndividualDashboard {
    public void addOrEditAndSaveTheseFields(List<Pair<String, String>> fields);
    public boolean savedDataSeenInHRAPage(List<Pair<String, String>> fields);
    public String checkLastSavedByUser();
}
