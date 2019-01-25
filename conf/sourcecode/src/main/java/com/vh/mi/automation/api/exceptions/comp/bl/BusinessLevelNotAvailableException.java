package com.vh.mi.automation.api.exceptions.comp.bl;

import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.exceptions.AutomationException;

/**
 * Thrown when the user supplies a Level that is not available in the front
 *
 * @author nimanandhar
 */
public class BusinessLevelNotAvailableException extends AutomationException {

    public BusinessLevelNotAvailableException(BL level) {
        super("The business level you provided " + level + " is not available");
    }
}
