package com.vh.mi.automation.impl.pages.favorites.myJobs;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by msedhain on 02/16/2017.
 */
public class MyJobsColumnIdExtractor extends DefaultColumnIdExtractor {
    private static final Pattern MYJOBS_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern MYJOBS_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");


    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return MYJOBS_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return MYJOBS_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
