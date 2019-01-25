package com.vh.mi.automation.api.comp;

import com.vh.mi.automation.api.features.IAmWebComponent;

import java.util.List;

/**
 * Created by i81306 on 1/10/2018.
 */
public interface IQueryProfiler extends IAmWebComponent {
     List<String> getSchemaQueries();
}
