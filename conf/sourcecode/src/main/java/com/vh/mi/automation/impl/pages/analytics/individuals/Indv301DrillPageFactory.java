package com.vh.mi.automation.impl.pages.analytics.individuals;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.comp.dataGrid.drill.IDrillPageFactory;
import com.vh.mi.automation.api.exceptions.AutomationException;
import com.vh.mi.automation.api.pages.common.IDrillPage;
import com.vh.mi.automation.api.pages.common.IMIPage;
import com.vh.mi.automation.impl.pages.analytics.individuals.drill.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by nimanandhar on 12/16/2014.
 */
public class Indv301DrillPageFactory implements IDrillPageFactory {
    public static final IDrillPageFactory INSTANCE = new Indv301DrillPageFactory();

    private Indv301DrillPageFactory() {
    }


    @Override
    public IDrillPage initElements(WebDriver webDriver, String drillMenu) {
        Class drillPageClass = getDrillPageClass(drillMenu);
        Preconditions.checkState(IMIPage.class.isAssignableFrom(drillPageClass), " The class " + drillPageClass + " is not an instance of IMIPage");

        return (IDrillPage) PageFactory.initElements(webDriver, drillPageClass);
    }

    private Class getDrillPageClass(String drillMenu) {
        switch (drillMenu) {
            case "Summary":
                return IndividualDashboardMemberSummary.class;
            case "Quality Measures":
                return IndividualDashboardQualityMeasures.class;
            case "Clinical Event Chart":
                return IndividualDashboardClinicalEventChart.class;
            case "Individual Claim Details":
                return IndividualDashboardIndividualClaimDetails.class;
            case "Biometrics/Labs":
                return IndividualDashboardBiometricsLabs.class;
            case "Lab Results":
                return IndividualDashboardLabResults.class;
            case "Trend":
                return IndividualDashboardTrendAnalysis.class;
            case "True Performance Measure":
                return IndividualDashboardTruePerformance.class;

        }
        throw new AutomationException("Unrecognized drill Menu " + drillMenu);
    }
}

