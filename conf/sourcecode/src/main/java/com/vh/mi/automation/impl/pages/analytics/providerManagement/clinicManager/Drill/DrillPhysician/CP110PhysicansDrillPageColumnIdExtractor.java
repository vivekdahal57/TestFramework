package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.DrillPhysician;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i10359 on 11/20/17.
 */
public class CP110PhysicansDrillPageColumnIdExtractor extends DefaultColumnIdExtractor{
    private static final Pattern CP110PHYSICIANSDRILLPAGE_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)_header.*$");
    private static final Pattern CP110PHYSICIANSDRILLPAGE_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_header_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)");

    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return CP110PHYSICIANSDRILLPAGE_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return CP110PHYSICIANSDRILLPAGE_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }

}
