package com.vh.mi.automation.api.config;

import com.vh.mi.automation.api.data.DataSourceConfiguration;
import com.vh.mi.automation.api.dto.Application;
import com.vh.mi.automation.groovy.environment.EnvironmentConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.SystemConfiguration;

import static com.vh.mi.automation.groovy.environment.EnvironmentConfiguration.*;

/**
 * @author nimanandhar
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
        compositeConfiguration.addConfiguration(new EnvironmentConfiguration(environment));
        compositeConfiguration.setThrowExceptionOnMissing(true);

        this.configuration = compositeConfiguration;

        application = new Application(configuration.getString(APP_URL), configuration.getString(APP_ID));
        waitTime = configuration.getInt(DEFAULT_WAIT_TIME_OUT);
        driverConfiguration = buildDriverConfiguration();
    }


    private DriverConfiguration buildDriverConfiguration() {
        DriverConfiguration.Builder builder = DriverConfiguration.newBuilder(configuration.getString(BROWSER), configuration.getLong(DEFAULT_WAIT_TIME_OUT));

        if (configuration.containsKey(RUN_ON_GRID))
            builder.runOnGrid(configuration.getBoolean(RUN_ON_GRID));

        if (configuration.containsKey(HUB_URL))
            builder.setHubUrl(configuration.getString(HUB_URL));

        if (configuration.containsKey(IMPLICIT_WAIT_TIME))
            builder.setImplicitWaitTime(configuration.getInt(IMPLICIT_WAIT_TIME));

        if (configuration.containsKey(VERSION))
            builder.setVersion(configuration.getString(VERSION));

        if (configuration.containsKey(FILE_DOWNLOAD_LOCATION))
            builder.setFileDownloadLocation(configuration.getString(FILE_DOWNLOAD_LOCATION));

        return builder.build();
    }


    public Application getApplication() {
        return application;
    }


    public String getMemberId() {
        return configuration.getString(MEMBER_ID);
    }

    public Boolean skipTestThatRequireDatabase() {
        return configuration.getBoolean(SKIP_TEST_THAT_REQUIRE_DATABASE);
    }

    public DataSourceConfiguration getHeuserDataSourceConfiguration() {
        return new DataSourceConfiguration.Builder()
                .withUrl(configuration.getString("heuserUrl"))
                .withUsername(configuration.getString("heuserUserName"))
                .withPassword(configuration.getString("heuserPassword"))
                .build();
    }

    public DataSourceConfiguration getVerticaDataSourceConfiguration() {
        return new DataSourceConfiguration.Builder()
                .withUrl(configuration.getString("verticaUrl"))
                .withUsername(configuration.getString("verticaUsername"))
                .withPassword(configuration.getString("verticaPassword"))
                .build();
    }

    public Integer getDefaultWaitTimeout() {
        return waitTime;
    }

    public String getAdminUrl() {
        return configuration.getString(ADMIN_URL);
    }

    public String getMxAppUrl() {
        return configuration.getString(MXAPP_URL);
    }

    public String getProxyTicketUrl() { return configuration.getString("proxyTicketURL"); }

    public String getAppUrl() { return configuration.getString("appUrl"); }

    public String getDemoerUrl(){
        return configuration.getString("demoerUrl");
    }

    public String getApiGatewayUrl(){
        return configuration.getString("apiGatewayUrl");
    }

    public String getApiWidgetServiceEndpointUrl(){
        return configuration.getString("apiWidgetServiceEndpointUrl");
    }

    public String getApiERServiceEndpointUrl(){
        return configuration.getString("apiERServiceEndpointUrl");
    }

    public String getEiDashboardUrl(){
        return configuration.getString(EI_DASHBOARD_URL);
    }

    public String getSfwDynamicCohortid(){
        return configuration.getString("sfw_dynamic_cohortid");
    }

    public String getSfwStaticCohortid(){
        return configuration.getString("sfw_static_cohortid");
    }

    public String getDynamicCohortid(){
        return configuration.getString("dynamic_cohortid");
    }

    public String getStaticCohortid(){
        return configuration.getString("static_cohortid");
    }

    public String getSAMLUrltoLogin() {return configuration.getString("samlUrlToLogin");}

    public String getSamlUrlPrefix() {
        if (configuration.containsKey("samlUrlPrefix")) {
            return configuration.getString("samlUrlPrefix");
        }
        return getApplication().getUrl();
    }

    public String getAppId() {
        return configuration.getString("appId");
    }

    public String getAppId2() {
        return configuration.getString("appId2");
    }

    public String getPostingSchema() {
        return configuration.getString(POSTING_SCHEMA);
    }

    public String getSwitchDatabase1() {
        return configuration.getString(SWITCH_DATABASE1);
    }

    public String getSwitchDatabase2() {
        return configuration.getString(SWITCH_DATABASE2);
    }

    public IDriverConfiguration getDriverConfiguration() {
        return driverConfiguration;
    }

    public String getFileDownloadLocation(){
        return configuration.getString(FILE_DOWNLOAD_LOCATION);
    }

    public String getFileUploadLocation(){
        return configuration.getString(FILE_UPLOAD_LOCATION);
    }

    public boolean useShareLocationForFiles(){
        return configuration.getBoolean(USE_SHARE_LOCATION_FOR_FILES);
    }

    public String getRmUrl() {
        return configuration.getString(RM_URL);
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

    public String getBeforeSchema() {
        return configuration.getString("beforeSchema");
    }

    public String getAfterSchema() {
        return configuration.getString("afterSchema");
    }

}
