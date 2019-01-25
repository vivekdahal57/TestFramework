package com.vh.mi.automation.impl.pages.analytics.diseaseRegistry;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i10359 on 3/13/18.
 */
public class DiseaseRegistryCD016ColumnIdExtractor extends DefaultColumnIdExtractor{
    private static final Pattern DISEASEREGISTRY_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)_header.*$");
    private static final Pattern DISEASEREGISTRY_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_header_sortCommandLink$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:grid1.*:(.*)");

    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return DISEASEREGISTRY_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return DISEASEREGISTRY_SORT_LINK_ID_PATTERN;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }
}
