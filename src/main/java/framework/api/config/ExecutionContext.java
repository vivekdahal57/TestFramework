package framework.api.config;

import framework.api.dto.Application;
import framework.groovy.environment.EnvironmentConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.SystemConfiguration;

import static framework.groovy.environment.EnvironmentConfiguration.*;

/**
 * @author bibdahal
 */
public class ExecutionContext {
    private final Application application;
    private final Integer waitTime;
    private final Configuration configuration;
    private final DriverConfiguration driverConfiguration;

    public static ExecutionContext forEnvironment(String environment) {
        return new ExecutionContext(environment);
    }

    private ExecutionContext(String environment) {
        CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
        compositeConfiguration.addConfiguration(new SystemConfiguration());
        compositeConfiguration
                .addConfiguration(new EnvironmentConfiguration(environment));
        compositeConfiguration.setThrowExceptionOnMissing(true);

        this.configuration = compositeConfiguration;

        application = new Application(configuration.getString(APP_URL));
        waitTime = configuration.getInt(DEFAULT_WAIT_TIME_OUT);
        driverConfiguration = buildDriverConfiguration();
    }

    private DriverConfiguration buildDriverConfiguration() {
        DriverConfiguration.Builder builder = DriverConfiguration
                .newBuilder(configuration.getString(BROWSER),
                        configuration.getLong(DEFAULT_WAIT_TIME_OUT));

        if (configuration.containsKey(RUN_ON_GRID))
            builder.runOnGrid(configuration.getBoolean(RUN_ON_GRID));

        if (configuration.containsKey(HUB_URL))
            builder.setHubUrl(configuration.getString(HUB_URL));

        if (configuration.containsKey(IMPLICIT_WAIT_TIME))
            builder.setImplicitWaitTime(
                    configuration.getInt(IMPLICIT_WAIT_TIME));

        if (configuration.containsKey(FILE_DOWNLOAD_LOCATION))
            builder.setFileDownloadLocation(
                    configuration.getString(FILE_DOWNLOAD_LOCATION));

        return builder.build();
    }

    public Application getApplication() {
        return application;
    }

    public String getAppUrl() {
        return configuration.getString(APP_URL);
    }

    public Boolean skipTestThatRequireDatabase() {
        return configuration.getBoolean(SKIP_TEST_THAT_REQUIRE_DATABASE);
    }

    public Integer getDefaultWaitTimeout() {
        return waitTime;
    }

    public IDriverConfiguration getDriverConfiguration() {
        return driverConfiguration;
    }

    public String getFileDownloadLocation() {
        return configuration.getString(FILE_DOWNLOAD_LOCATION);
    }

    public String getFileUploadLocation() {
        return configuration.getString(FILE_UPLOAD_LOCATION);
    }

    public boolean useShareLocationForFiles() {
        return configuration.getBoolean(USE_SHARE_LOCATION_FOR_FILES);
    }

    public String getNodeDomain() {
        return configuration.getString(NODE_DOMAIN);
    }

    public String getNodeUsername() {
        return configuration.getString(NODE_USERNAME);
    }

    public String getNodePassword() {
        return configuration.getString(NODE_PASSWORD);
    }

    public String getAppUsername() {
        return configuration.getString(APP_USERNAME);
    }

    public String getAppPassword() {
        return configuration.getString(APP_PASSWORD);
    }
}
