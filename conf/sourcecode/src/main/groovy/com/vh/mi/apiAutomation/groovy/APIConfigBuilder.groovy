package com.vh.mi.apiAutomation.groovy

import com.vh.mi.automation.api.config.ExecutionContext

/**
 * Created by i82325 on 11/14/2017.
 */
class APIConfigBuilder {
    protected final ExecutionContext context

    public APIConfigBuilder() {
        context = ExecutionContext.forEnvironment(System.getProperty("environment"));
    }

    public Map getLevel(String levelValue) {
        def appId = context.getAppId()
        ConfigObject conf = new ConfigSlurper().parse(new File("conf" + File.separator + "apitest" + File.separator + appId + File.separator + "businessLevels.groovy").toURI().toURL())
        Map config = conf.flatten()
        Map allValues = new HashMap()
        allValues.put("nlvlId", config.get(levelValue + ".nlvlId"))
        allValues.put("levelDesc", config.get(levelValue + ".levelDesc"))
        allValues.put("levelId", config.get(levelValue + ".levelId"))
        return allValues
    }
}
