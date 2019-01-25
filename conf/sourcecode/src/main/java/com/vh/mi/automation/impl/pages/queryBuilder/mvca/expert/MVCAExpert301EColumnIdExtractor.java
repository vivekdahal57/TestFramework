package com.vh.mi.automation.impl.pages.queryBuilder.mvca.expert;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

public class MVCAExpert301EColumnIdExtractor extends DefaultColumnIdExtractor {
    private static final Pattern MVCAExpert301_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern MVCAExpert301_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");

    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return MVCAExpert301_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return MVCAExpert301_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
