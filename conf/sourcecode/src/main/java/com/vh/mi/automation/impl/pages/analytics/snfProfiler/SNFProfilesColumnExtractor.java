package com.vh.mi.automation.impl.pages.analytics.snfProfiler;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i10359 on 11/16/17.
 */
public class SNFProfilesColumnExtractor extends DefaultColumnIdExtractor {
    private static final Pattern SNFPROFILESSP100_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern SNFPROFILES_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");


    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return SNFPROFILESSP100_HEADER_ID_PATTERN ;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return SNFPROFILES_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
