package framework.api.exceptions.accountprofile;

import framework.api.exceptions.AutomationException;

/**
 * Created by i82298 on 10/29/2015.
 */
public class InvalidCredentialsException extends AutomationException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
