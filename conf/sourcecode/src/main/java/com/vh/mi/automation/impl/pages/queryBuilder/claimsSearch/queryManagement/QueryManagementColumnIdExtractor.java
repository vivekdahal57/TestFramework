package com.vh.mi.automation.impl.pages.queryBuilder.claimsSearch.queryManagement;

import com.vh.mi.automation.impl.comp.dataGrid.DefaultColumnIdExtractor;

import java.util.regex.Pattern;

/**
 * Created by i81306 on 5/5/2017.
 */
    public class QueryManagementColumnIdExtractor extends DefaultColumnIdExtractor {
        private static final Pattern QUERYMANAGEMENT_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)header$");
        private static final Pattern QUERYMANAGEMENT_SORT_LINK_ID_PATTERN = Pattern.compile("^.*column_(.*)_sortCommandLink$");
        private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)header$");

        @Override
        protected Pattern getColumnHeaderIdPattern() {
            return QUERYMANAGEMENT_HEADER_ID_PATTERN;
        }

        @Override
        protected Pattern getSortLinkIdPattern() {
            return QUERYMANAGEMENT_SORT_LINK_ID_PATTERN;
        }

        @Override
        protected Pattern getDynamicColumnIdPattern() {
            return DYNAMIC_COLUMN_ID_PATTERN;
        }
    }

