package com.vh.mi.automation.impl.pages.analytics.providerProfiler.Drill.Procedure;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i10359 on 1/12/18.
 */
public class ProcedureDrillPageColumnIdExtractor extends DefaultColumnIdExtractor {

    private static final Pattern PROCEDURE_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
    private static final Pattern PROCEDURE_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");

    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return PROCEDURE_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return PROCEDURE_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
