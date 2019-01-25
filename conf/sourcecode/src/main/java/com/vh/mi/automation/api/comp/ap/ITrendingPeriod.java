package com.vh.mi.automation.api.comp.ap;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption;
import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.features.IAmWebComponent;
import org.joda.time.DateTime;

/**
 * @author i80448
 */
public interface ITrendingPeriod extends IAmWebComponent{

    public void doSelectPeriodOne(DateTime from, DateTime to);

    public void doSelectPeriodTwo(DateTime from, DateTime to);

    /**
     * Applies the currently selected custom periods. <br>
     * <b><i>NOTE: transitions component into <u>DEAD</u> state</i></b>
     */
    public void doApplyPeriodsSelection();

    /**
     * <b><i>NOTE: transitions component into <u>DEAD</u> state</i></b>
     *
     * @param option
     */
    public void doSelectDefaultOption(APOption option);

    public IPeriod getSelectedPeriodOne();

    public IPeriod getSelectedPeriodTwo();

    public ImmutableList<APOption> getDefaultOptions();

    /**
     * Gets current option selected by user. Note that this provides just the selected option
     * but not the actual selected value.
     * Use AnalysisPeriod instance to get the selected value.
     *
     * @return {@link com.vh.mi.automation.api.comp.ap.IAnalysisPeriod.APOption}
     */
    public APOption getCurrentUserSelection();
}
