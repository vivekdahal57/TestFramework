package com.vh.mi.automation.impl.pages.analytics.diseaseRegistry.drill;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i10359 on 3/15/18.
 */
public class CD018DiseaseAnalysisByMembersDrillPageDefaultColumnExtractor extends DefaultColumnIdExtractor{
    private static final Pattern  CD018DISEASEANALYSISBYMEMBERS_HEADER_IN_PATTERN = Pattern.compile("^.*column_(.*)header.*$");
    private static final Pattern  CD018DISEASEANALYSISBYMEMBERS_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern  DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header");

    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return CD018DISEASEANALYSISBYMEMBERS_HEADER_IN_PATTERN ;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return CD018DISEASEANALYSISBYMEMBERS_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return  DYNAMIC_COLUMN_ID_PATTERN;
    }
}
