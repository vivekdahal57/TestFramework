package com.vh.mi.automation.api.exceptions.comp.bl;

import com.vh.mi.automation.api.exceptions.AutomationException;

/**
 * Thrown when option provided by the user is not available or applicable
 * Created by nimanandhar on 1/19/2015.
 */
public class BusinessLevelOptionNotAvailable extends AutomationException {

    public BusinessLevelOptionNotAvailable(String option) {
        super("The option " + option + " is not available for selection");
    }
}
