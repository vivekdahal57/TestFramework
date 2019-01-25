package com.vh.mi.automation.groovy.environment;

import com.vh.mi.automation.api.exceptions.NotImplementedException;
import groovy.util.BuilderSupport;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Builder Class to parse environment.groovy
 *
 * Will parse environment.groovy file and populate the environmentProperties map
 * @author nimandhar
 */
class EnvironmentBuilder extends BuilderSupport {
    public static final String DEFAULT_VALUES_KEY = "defaultValues";
    Map<String, Map<String, String>> environmentProperties = new HashMap<>();
    String currentEnvironment = null;
    String defaultEnvironment = null;

    //called for defaultValues{ tag
    @Override
    protected Object createNode(Object name) {
        assertThat(name).isEqualTo(DEFAULT_VALUES_KEY);
        environmentProperties.put(DEFAULT_VALUES_KEY, new HashMap<String, String>());
        currentEnvironment = DEFAULT_VALUES_KEY;
        return null;
    }

    //called for keyValue pairs
    @Override
    protected Object createNode(Object name, Object value) {
        environmentProperties.get(currentEnvironment).put(name.toString(), value.toString());
        return null;

    }

    @Override
    protected Object createNode(Object name, Map attributes) {
        switch (name.toString()) {
            case "environments":
                assertThat(attributes.containsKey("default")).as("default environment should be provided").isTrue();
                defaultEnvironment = (String) attributes.get("default");
                assertThat(defaultEnvironment).isNotNull();
                break;
            case "environment":
                assertThat(attributes.containsKey("name"));
                currentEnvironment = (String) attributes.get("name");
                assertThat(environmentProperties.containsKey(currentEnvironment)).isFalse(); // assert no duplicate environment are present
                environmentProperties.put(currentEnvironment, new HashMap<String, String>());
                break;
            default:
                throw new RuntimeException("Unexpected name " + name);
        }
        return null;
    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        throw new RuntimeException("Not called");
    }

    @Override
    protected void setParent(Object parent, Object child) {
        throw new NotImplementedException();
    }


    public Map<String, String> getDefaultEnvironmentProperties() {
        return getProperties(defaultEnvironment);
    }

    public Map<String, String> getProperties(String environment) {
        assertThat(environmentProperties.containsKey(environment)).as("No such environemnt " + environment).isTrue();

        Map<String, String> properties = new HashMap<>(environmentProperties.get(DEFAULT_VALUES_KEY));
        properties.putAll(environmentProperties.get(environment));
        return properties;
    }


}
