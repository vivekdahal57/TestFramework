package com.vh.mi.automation.api.comp;

import com.google.common.collect.ImmutableCollection;
import com.vh.mi.automation.api.features.IAmWebComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides the essence of being MI reporting by component.
 *
 * @author i80448
 */
public interface ICohortSelection extends IAmWebComponent {
    void selectACohort();
}
