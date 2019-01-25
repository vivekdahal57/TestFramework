package com.vh.mi.automation.api.comp.ap;

import com.vh.mi.automation.api.features.IAmWebComponent;
import org.joda.time.DateTime;

import java.util.Collection;

/**
 * Created by i80448 on 9/3/2014.
 */
public interface IPeriodComp extends IAmWebComponent {

    public void doSelect(DateTime from, DateTime to);

    public Collection<DateTime> getFromDates();

    public Collection<DateTime> getToDates();

    DateTime getSelectedFromDate();

    DateTime getSelectedToDate();
}
