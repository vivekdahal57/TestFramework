package com.vh.mi.automation.impl.pages.queryBuilder.stratifier;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i82716 on 4/25/2017.
 */
public class PopulationAnalyserColumnIdExtractor extends DefaultColumnIdExtractor{
    private static final Pattern PopulationAnalyser_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern PopulationAnalyser_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");

    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return PopulationAnalyser_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return PopulationAnalyser_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
