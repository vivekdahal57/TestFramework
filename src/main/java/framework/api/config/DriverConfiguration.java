package framework.api.config;

import com.google.common.base.Preconditions;
import framework.api.annotations.documentation.Immutable;

/**
 * @author bibdahal
 */
@Immutable
public class DriverConfiguration implements IDriverConfiguration {
    private Browser browser;
    private final boolean runOnGrid;
    private final String hubUrl;
    private final String fileDownloadLocation;
    private final long defaultWaitTimeout;
    private final Integer implicitWaitTime;

    private DriverConfiguration(Builder builder) {
        this.browser = builder.browser;
        this.defaultWaitTimeout = builder.defaultWaitTime;
        this.hubUrl = builder.hubUrl;
        this.runOnGrid = builder.runOnGrid;
        this.implicitWaitTime = builder.implicitWaitTime;
        this.fileDownloadLocation = builder.fileDownloadLocation;
    }

    public static Builder newBuilder(String browser, Long defaultWaitTimeout) {
        return new Builder(browser, defaultWaitTimeout);
    }

    @Override
    public Browser getBrowser() {
        return browser;
    }

    @Override
    public long getDefaultWaitTimeout() {
        return defaultWaitTimeout;
    }

    @Override
    public Integer getImplicitWaitTime() {
        return implicitWaitTime;
    }

    @Override
    public boolean runOnGrid() {
        return runOnGrid;
    }

    public String getHubUrl() {
        return hubUrl;
    }

    public String getFileDownloadLocation() {
        return fileDownloadLocation;
    }

    @Override
    public void setBrowser(String browser){
        if(!browser.isEmpty()){
            this.browser = Browser.valueOf(browser);
        }
    }

    @Override
    public String toString() {
        return "DriverConfiguration{" +
                "browser='" + browser + '\'' +
                ", runOnGrid=" + runOnGrid +
                ", hubUrl='" + hubUrl + '\'' +
                ", defaultWaitTimeout=" + defaultWaitTimeout +
                ", fileDownloadLocation=" + fileDownloadLocation +
                '}';
    }


    public static class Builder {
        //RequiredParameters
        private final Browser browser;
        private final Long defaultWaitTime;

        //Optional parameters
        private String hubUrl = null;
        private Boolean runOnGrid = false;
        private Integer implicitWaitTime = 0;
        private String fileDownloadLocation= null;

        private Builder(String browser, Long defaultWaitTime) {
            Preconditions.checkNotNull(browser != null);
            Preconditions.checkNotNull(defaultWaitTime);

            this.browser = Browser.valueOf(browser);
            this.defaultWaitTime = defaultWaitTime;
        }

        public Builder runOnGrid(boolean runOnGrid) {
            this.runOnGrid = runOnGrid;
            return this;
        }

        public Builder setHubUrl(String hubUrl) {
            Preconditions.checkNotNull(hubUrl);
            this.hubUrl = hubUrl;
            return this;
        }

        public Builder setImplicitWaitTime(Integer implicitWaitTime) {
            this.implicitWaitTime = implicitWaitTime;
            return this;
        }

        public Builder setFileDownloadLocation(String fileDownloadLocation) {
            this.fileDownloadLocation = fileDownloadLocation;
            return this;
        }

        public DriverConfiguration build() {
            if (runOnGrid && hubUrl == null)
                throw new IllegalStateException("HubUrl is Null");
            return new DriverConfiguration(this);
        }
    }


}
