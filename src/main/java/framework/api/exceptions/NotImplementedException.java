package framework.api.exceptions;

/**
 * Created by bibdahal
 */
public class NotImplementedException extends AutomationException {

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException() {
        super("Not Implemented");
    }
}
