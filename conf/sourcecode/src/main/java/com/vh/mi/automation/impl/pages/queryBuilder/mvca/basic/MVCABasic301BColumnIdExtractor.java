package com.vh.mi.automation.impl.pages.queryBuilder.mvca.basic;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

public class MVCABasic301BColumnIdExtractor extends DefaultColumnIdExtractor{
    private static final Pattern MVCABASIC301B_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern MVCABASIC301B_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");

    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return MVCABASIC301B_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return MVCABASIC301B_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
