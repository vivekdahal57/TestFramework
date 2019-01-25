package com.vh.mi.automation.impl.configuration;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.constants.SwitchBoard;
import org.apache.commons.configuration.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Get configuration values required by certain tests
 * The configuration is taken from test/fronts/app-id.properties if available
 * otherwise, the default configuration is read from test/defaults.properties file
 * Created by nimanandhar on 11/28/2014.
 */

public class FrontConfiguration {
    private static Logger logger = LoggerFactory.getLogger(FrontConfiguration.class);
    public static final String DEFAULT_CONFIGURATION_FILE = "conf" + File.separator + "test" + File.separator + "default.properties";
    public static final String FRONT_CONFIGURATION_FILE = "conf" + File.separator + "test" + File.separator + "fronts" + File.separator + "{application.id}.properties";
    public final Configuration configuration;

    public FrontConfiguration(String applicationId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(applicationId));

        CompositeConfiguration configuration = new CompositeConfiguration();

        //use system parameters if available
        configuration.addConfiguration(new SystemConfiguration());


        //then  use application specific configuration
        String frontConfigurationFile = FRONT_CONFIGURATION_FILE.replace("{application.id}", applicationId);
        Optional<PropertiesConfiguration> frontConfiguration = getConfiguration(frontConfigurationFile);
        if (frontConfiguration.isPresent()) {
            configuration.addConfiguration(frontConfiguration.get());
        }

        //finally use defaults
        Optional<PropertiesConfiguration> defaultConfiguration = getConfiguration(DEFAULT_CONFIGURATION_FILE);
        if (defaultConfiguration.isPresent()) {
            configuration.addConfiguration(defaultConfiguration.get());
        }

        this.configuration = configuration;
    }


    private static Optional<PropertiesConfiguration> getConfiguration(String fileName) {
        File propertiesFile = new File(fileName);
        if (!propertiesFile.exists()) {
            logger.warn("The configuration file " + propertiesFile + " does not exist");
            return Optional.absent();
        }

        try {
            return Optional.of(new PropertiesConfiguration(propertiesFile));
        } catch (ConfigurationException e) {
            logger.warn("Exception occurred while gettingConfiguration. FileName = " + fileName + " Exception  " + e.getMessage());
            return Optional.absent();
        }
    }


    public List<SwitchBoard> getSwitchboards() {
        List<SwitchBoard> switchBoards = new ArrayList<>();

        for (String s : configuration.getStringArray("application.switchboards")) {
            switchBoards.add(SwitchBoard.valueOf(s));
        }
        return switchBoards;
    }

    public List<SwitchBoard> getSwitchboardsToBeExcludedFromOrderingTest() {
        List<SwitchBoard> excludedSwitchboards = new ArrayList<>();
        for (String s : configuration.getStringArray("tests.navigationPanel.ordering.ignoreSwitchboards")) {
            excludedSwitchboards.add(SwitchBoard.valueOf(s));
        }
        return excludedSwitchboards;
    }
}
