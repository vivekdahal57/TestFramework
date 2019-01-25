package framework.api.exceptions;

/**
 * Thrown for exceptions resulting from the implementation
 * of the automation framework.
 * Created by nimanandhar on 9/1/2014.
 */
public class AutomationException extends RuntimeException {

    public AutomationException(String message) {
        super(message);
    }
}
