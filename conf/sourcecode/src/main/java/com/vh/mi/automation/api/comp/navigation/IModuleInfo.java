package com.vh.mi.automation.api.comp.navigation;

import com.google.common.base.Optional;
import com.vh.mi.automation.api.constants.Modules;
import com.vh.mi.automation.api.constants.SwitchBoard;

/**
 * Created by nimanandhar on 10/22/2014.
 */
public interface IModuleInfo {
    Optional<Modules[]> getParentModules();

    String getId();

    String getName();

    SwitchBoard getSwitchboard();
}
