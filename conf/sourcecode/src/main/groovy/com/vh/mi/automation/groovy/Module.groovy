package com.vh.mi.automation.groovy

import com.google.common.base.Preconditions
import com.vh.mi.automation.api.constants.SwitchBoard
import com.vh.mi.automation.impl.utils.AlphanumComparator

/**
 * @author nimanandhar
 */

class Module implements Comparable<Module> {
    String id
    String linkName
    String pageId
    String pageTitle
    SwitchBoard switchBoard

    Module parent
    List<Module> children


    boolean hasParent() {
        return parent != null
    }

    boolean hasChildren() {
        return children != null && !children.isEmpty()
    }

    void addChild(Module childModule) {
        if (children == null) {
            children = new ArrayList<>()
        }
        children.add(childModule)
        childModule.parent = this
    }

    boolean isSection() {
        return hasChildren()
    }

    boolean isLandingPage() {
        return pageId && pageTitle
    }

    public static ModuleBuilder builder() {
        return new ModuleBuilder()
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
        sb.append("Module{" + "id='" + id + '\'' + ", linkName='" + linkName + '\'');
        pageId ? sb.append(", pageId='" + pageId + '\'') : null
        pageTitle ? sb.append(", pageTitle='" + pageTitle + '\'') : null
        sb.append(", switchBoard='" + switchBoard + '\'}')
        return sb.toString()
    }

    public List<Module> getLeafModules() {
        return getLeafModulesRecursive(this);
    }


    private List<Module> getLeafModulesRecursive(Module module) {
        if (!module.hasChildren()) {
            return Arrays.asList(module);
        }
        List<Module> leafModules = new ArrayList<>();
        for (Module subModule : module.getChildren()) {
            leafModules.addAll(getLeafModulesRecursive(subModule));
        }
        return leafModules;
    }

    boolean isLeaf() {
        return !hasChildren();
    }

    @Override
    int compareTo(Module other) {
        Preconditions.checkNotNull(other)

        String firstText = linkName.replaceAll("[\\W]", "").toLowerCase();
        String secondText = other.linkName.replaceAll("[\\W]", "").toLowerCase();
        return new AlphanumComparator().compare(firstText, secondText);
    }
}