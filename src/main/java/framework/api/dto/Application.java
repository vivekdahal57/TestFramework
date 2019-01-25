package framework.api.dto;

/**
 * 
 * @author bibdahal
 *
 */
public class Application {
	private final String url;

    public Application(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Application{url='" + url + '\'' + '}';
    }
}
