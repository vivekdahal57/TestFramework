package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Provider;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i82716 on 6/29/2017.
 */
public class ProviderDrillPageColumnIdExtractor extends DefaultColumnIdExtractor {
    private static final Pattern ProviderDrillPage_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern ProviderDrillPage_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");


    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return ProviderDrillPage_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return ProviderDrillPage_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
