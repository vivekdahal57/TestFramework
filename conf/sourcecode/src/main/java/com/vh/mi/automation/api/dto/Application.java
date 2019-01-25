package com.vh.mi.automation.api.dto;

/**
 * 
 * @author i80448
 *
 */
public class Application {
	private final String url;
    private final String appId;

    public Application(String url,String appId) {
        this.url = url;
        this.appId = appId;
    }

    public String getUrl() {
        return url;
    }

    public String getAppId() {
        return appId;
    }

    @Override
    public String toString() {
        return "Application{url='" + url + '\'' + ", appId='" + appId + '\'' + '}';
    }
}
