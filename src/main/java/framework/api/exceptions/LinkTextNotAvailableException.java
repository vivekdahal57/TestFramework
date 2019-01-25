package framework.api.exceptions;

/**
 * Thrown when an expected link is not available on page
 *
 * @author bibdahal
 */
public class LinkTextNotAvailableException extends AutomationException {
    public LinkTextNotAvailableException(String linkText) {
        super("The linkText " + linkText + " is not available");
    }
}
