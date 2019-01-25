package com.vh.mi.automation.impl.pages.analytics.hospitalProfiler.Drill;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i10359 on 11/13/17.
 */
public class HP120AdmissionDrillPageColumnIdExtractor extends DefaultColumnIdExtractor{

private static final Pattern HP120ADMISSIONDRILLPAGE_HEADER_ID_PATTERN_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
private static final Pattern HP120ADMISSIONDRILLPAGE_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");

    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return HP120ADMISSIONDRILLPAGE_HEADER_ID_PATTERN_HEADER_ID_PATTERN  ;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
       return  HP120ADMISSIONDRILLPAGE_SORT_LINK_ID_PATTERN  ;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
