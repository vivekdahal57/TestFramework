package com.vh.mi.automation.api.comp.dataGrid.customizer;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.vh.mi.automation.api.comp.dataGrid.IDataGridColumn;

/**
 * Created by i80448 on 11/14/2014.
 */
public interface IDxcgDataGridCustomizerState extends ICustomizerState {
    public ImmutableCollection<String> getSections();

    public ImmutableMap<String, ImmutableCollection<IDataGridColumn>> getDxcgAvailableOptions();

    public ImmutableMap<String, ImmutableCollection<IDataGridColumn>> getDxcgSelectedOptions();
}
