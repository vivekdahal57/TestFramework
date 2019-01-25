package com.vh.mi.automation.impl.pages.analytics.providerProfiler;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i82716 on 6/27/2017.
 */
public class ProviderProfilerColumnIdExtractor extends DefaultColumnIdExtractor {
    private static final Pattern ProviderProfiler_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern ProviderProfiler_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");


    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return ProviderProfiler_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return ProviderProfiler_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}

