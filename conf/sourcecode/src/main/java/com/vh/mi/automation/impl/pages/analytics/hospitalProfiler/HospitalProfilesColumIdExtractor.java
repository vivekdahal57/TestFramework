package com.vh.mi.automation.impl.pages.analytics.hospitalProfiler;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i82298 on 11/23/2015.
 */
public class HospitalProfilesColumIdExtractor extends DefaultColumnIdExtractor {
    private static final Pattern HOSPITALPROFILESHP100_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern HOSPITALPROFILESHP100_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");


    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return HOSPITALPROFILESHP100_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return HOSPITALPROFILESHP100_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
