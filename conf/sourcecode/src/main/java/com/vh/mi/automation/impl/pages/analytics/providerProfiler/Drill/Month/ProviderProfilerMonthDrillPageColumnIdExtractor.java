package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Month;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i10359 on 1/12/18.
 */
public class ProviderProfilerMonthDrillPageColumnIdExtractor extends DefaultColumnIdExtractor{

    private static final Pattern MONTH_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern MONTH_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");

    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return MONTH_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return MONTH_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
