package com.vh.mi.automation.impl.pages.analytics.providerManagement.clinicManager.Drill.PhysicianDiseaseRegistryDrill;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i10359 on 11/21/17.
 */
public class CD016DiseaseRegistryDrillPageColumnIdExtractor extends DefaultColumnIdExtractor {
    private static final Pattern CD016DISEASEREGISTRYDRILLPAGE_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)_header.*$");
    private static final Pattern CD016DISEASEREGISTRYDRILLPAGE_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_header_sortCommandLink(.*)?$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:grid2:(.*)");


    @Override
    protected Pattern getColumnHeaderIdPattern() {
        return CD016DISEASEREGISTRYDRILLPAGE_HEADER_ID_PATTERN;
    }

    @Override
    protected Pattern getSortLinkIdPattern() {
        return CD016DISEASEREGISTRYDRILLPAGE_SORT_LINK_ID_PATTERN ;
    }

    @Override
    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }

}
