package com.vh.mi.automation.impl.pages.analytics.conversionAnalyzer;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by nimanandhar on 12/9/2014.
 */
public class ConversionAnalyzerColumnIdExtractor extends DefaultColumnIdExtractor {
    private static final Pattern CONVERSIONANALYZER_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern CONVERSIONANALYZER_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");


    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return CONVERSIONANALYZER_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return CONVERSIONANALYZER_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
