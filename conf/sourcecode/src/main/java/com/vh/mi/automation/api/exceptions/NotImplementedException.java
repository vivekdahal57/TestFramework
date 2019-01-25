package com.vh.mi.automation.api.exceptions;

/**
 * Created by nimanandhar on 1/19/2015.
 */
public class NotImplementedException extends AutomationException {

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException() {
        super("Not Implemented");
    }
}
