package framework.groovy.environment

import framework.api.exceptions.NotImplementedException
import org.apache.commons.configuration.AbstractConfiguration
import org.apache.commons.configuration.Configuration

/**
 * @author nimanandhar
 */
class EnvironmentConfiguration extends AbstractConfiguration implements Configuration {
    private static final String CONFIGURATION_FILE = "conf" + File.separator + "environment.groovy"
    public static final String APP_URL = "appUrl"
    public static final String BROWSER = "browser"
    public static final String RUN_ON_GRID = "runOnGrid"
    public static final String HUB_URL = "hubUrl"
    public static final String SKIP_TEST_THAT_REQUIRE_DATABASE = "skipTestThatRequireDatabase"
    public static final String DEFAULT_WAIT_TIME_OUT = "waitTime"
    public static final String IMPLICIT_WAIT_TIME = "implicitWaitTime"
    public static final String VERSION = "version"
    public static final String FILE_DOWNLOAD_LOCATION = "fileDownloadLocation"
    public static final String FILE_UPLOAD_LOCATION = "fileUploadLocation"
    public static final String USE_SHARE_LOCATION_FOR_FILES = "useShareLocationForFiles"
    public static final String NODE_DOMAIN = "nodeDomain"
    public static final String NODE_USERNAME = "nodeUsername"
    public static final String NODE_PASSWORD = "nodePassword"
    public static final String APP_USERNAME = 'username'
    public static final String APP_PASSWORD = 'password'


    private String environment
    Map<String, String> properties


    EnvironmentConfiguration(String environment) {
        this.environment = environment

        //read in configuration from environment.groovy
        def builder = new EnvironmentBuilder()

        Binding binding = new Binding() {
            @Override
            Object getVariable(String name) {
                return { Object... args -> builder.invokeMethod(name, args) }
            }
        }


        def script = new GroovyShell(binding).parse(getConfigFile())

        script.run()

        if (environment == null) {
            properties = builder.getDefaultEnvironmentProperties()
        } else {
            properties = builder.getProperties(environment)
        }

    }

    /**
     * Two different directories are provided because the working
     * directory changes based on whether you are runnning via maven
     * or directly running a groovy script. Need to find a better
     * way to find resources
     * @return
     */
    File getConfigFile() {
        File configFile = new File(CONFIGURATION_FILE)
        if (configFile.exists()) return configFile

        configFile = new File("environment.groovy")
        if (configFile.exists()) return configFile

        throw new Error("Could not find environment.groovy. The current working directory is " + System.getProperty("user.dir"))
    }


    @Override
    boolean isEmpty() {
        return properties.isEmpty()
    }

    @Override
    boolean containsKey(String key) {
        return properties.containsKey(key)
    }

    @Override
    Iterator getKeys() {
        return properties.keySet().iterator()
    }

    @Override
    Object getProperty(String property) {
        return properties.get(property)
    }

    @Override
    protected void addPropertyDirect(String key, Object value) {
        throw new NotImplementedException() //not applicable
    }
}
