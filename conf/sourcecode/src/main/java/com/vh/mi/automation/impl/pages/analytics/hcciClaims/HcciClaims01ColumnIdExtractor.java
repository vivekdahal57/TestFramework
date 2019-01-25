package com.vh.mi.automation.impl.pages.analytics.hcciClaims;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by nimanandhar on 12/9/2014.
 */
public class HcciClaims01ColumnIdExtractor extends DefaultColumnIdExtractor {
    private static final Pattern CLAIMS101_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)_header$");
    private static final Pattern CLAIMS101_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_header_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");


    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return CLAIMS101_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return CLAIMS101_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
