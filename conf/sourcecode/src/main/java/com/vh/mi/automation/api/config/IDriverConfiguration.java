package com.vh.mi.automation.api.config;

/**
 * @author i80448
 */
public interface IDriverConfiguration {

    public long getDefaultWaitTimeout();

    public Integer getImplicitWaitTime();

    public Browser getBrowser();

    public void setBrowser(String browser);

    public boolean runOnGrid();

    public String getHubUrl();

    public String getVersion();

    public String getFileDownloadLocation();

    public enum Browser {
        FIREFOX("firefox"),
        IE("internet explorer"),
        CHROME("chrome");

        Browser(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }
    }

}
