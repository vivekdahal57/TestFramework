package com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.drill;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i10359 on 3/14/18.
 */
public class ComorbidityCD108ColumnIdExtractor extends DefaultColumnIdExtractor{
    private static final Pattern COMORBIDITY_HEADER_IN_PATTERN = Pattern.compile("^.*column_(.*)_header.*$");
    private static final Pattern COMORBIDITY_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_header_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:gridTable.*:(.*)");


    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return COMORBIDITY_HEADER_IN_PATTERN ;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return COMORBIDITY_SORT_LINK_ID_PATTERN  ;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }

}
