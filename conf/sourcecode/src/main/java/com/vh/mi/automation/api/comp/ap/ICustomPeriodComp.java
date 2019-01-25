package com.vh.mi.automation.api.comp.ap;

import org.joda.time.DateTime;

/**
 * Created by i80448 on 9/3/2014.
 */
public interface ICustomPeriodComp extends IPeriodComp {

    public void doSelectAndApply(DateTime from, DateTime to);

    public void doApplyDefaultSelects();
}
