package com.vh.mi.automation.groovy

/**
 * @author nimanandhar
 */
class ModuleInfo {
    private Map<String, Module> moduleMap

    private ModuleInfo() {

    }
    public static ModuleInfo INSTANCE = new ModuleInfo()


    Module getModule(String moduleId) {
        initializeModuleMap()
        if (!moduleMap.containsKey(moduleId))
            throw new RuntimeException("Module ${moduleId} not found")
        return moduleMap.get(moduleId)
    }

    Module getModuleByPageId(String pageId) {
        initializeModuleMap()

        Module module = moduleMap.values().find { it.pageId.equals(pageId) }
        if (module == null)
            throw new RuntimeException("No module found with page id ${pageId}")
        return module
    }

    private void initializeModuleMap() {
        if (moduleMap != null)
            return; //already initialized

        def builder = new NavigationPanelBuilder()
        Binding binding = new Binding() {
            @Override
            Object getVariable(String name) {
                return { Object... args -> builder.invokeMethod(name, args) }
            }
        }
        def script = new GroovyShell(binding).parse(new File("conf" + File.separator + "navigationPanel.groovy"))
        moduleMap = script.run()
    }
}
