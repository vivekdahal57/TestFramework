package com.vh.mi.automation.impl.pages.outReach;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by msedhain on 02/16/2017.
 */
public class MemberListSummaryColumnExtractor extends DefaultColumnIdExtractor {
    private static final Pattern MEMBERLIST_SUMMARY_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern MEMBERLIST_SUMMARY_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");


    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return MEMBERLIST_SUMMARY_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return MEMBERLIST_SUMMARY_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
