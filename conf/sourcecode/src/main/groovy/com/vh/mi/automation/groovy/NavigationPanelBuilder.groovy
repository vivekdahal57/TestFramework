package com.vh.mi.automation.groovy

import com.vh.mi.automation.api.constants.SwitchBoard

/**
 * @author nimanandhar
 */
class NavigationPanelBuilder extends BuilderSupport {
    Map<String, Module> modules;
    SwitchBoard currentSwitchboard = null

    @Override
    protected Object createNode(Object name) {
        assert name.equals("navigationPanels")
        modules = [:]
    }

    @Override
    protected Object createNode(Object name, Object value) {
        assert name.equals("switchboard")
        currentSwitchboard = SwitchBoard.valueOf(value)
    }

    @Override
    protected Object createNode(Object name, Map map) {
        switch (name) {
            case "landingPage":
            case "section":
                Module module = new Module(map)
                module.switchBoard = currentSwitchboard
                modules.put(map['id'], module)
                return module
            default:
                throw new RuntimeException("Unexpected node ${name}")
        }

    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        throw new RuntimeException("N/A")
    }

    @Override
    protected void setParent(Object parent, Object child) {
        if (parent instanceof Module) {
            assert child instanceof Module
            parent.addChild(child)
        }
    }

}
