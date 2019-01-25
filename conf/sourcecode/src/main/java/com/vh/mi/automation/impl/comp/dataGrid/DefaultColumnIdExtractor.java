package com.vh.mi.automation.impl.comp.dataGrid;

import com.vh.mi.automation.api.comp.dataGrid.IColumnIdExtractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Extracts ID of Column from the id attribute
 * Created by nimanandhar on 12/4/2014.
 */
public class DefaultColumnIdExtractor implements IColumnIdExtractor {
    private static final Pattern COLUMN_HEADER_ID_PATTERN = Pattern.compile("^.*column_(.*)_header.*$");
    private static final Pattern COLUMN_SORT_LINK_PATTERN = Pattern.compile("^.*column_(.*)_header_sortCommandLink$");
    private static final Pattern COLUMN_SORT_LINK_TREND_PATTERN = Pattern.compile("^.*column_(.*)_header_sortCommandLink_1$");
    private static final Pattern DYNAMIC_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGrid.*:(.*)");
    private static final Pattern DYNAMIC_TREND_COLUMN_ID_PATTERN = Pattern.compile("^d2Form:simpleGridTrend.*:(.*)");

    public String extractColumnIdFromHeader(String idAttribute) {
        Matcher matcher = getColumnHeaderIdPattern().matcher(idAttribute);
        if (matcher.find()) {
            return matcher.group(1);
        }

        //did not match a regular column id, this id must be dynamic
        Matcher dynamicIdMatcher = getDynamicColumnIdPattern().matcher(idAttribute);
        Matcher dynamicTrendIdMatcher = getDynamicColumnIdTrendPattern().matcher(idAttribute);
        if (dynamicIdMatcher.find()) {
            String dynamicId = dynamicIdMatcher.group(1);
            assertThat(dynamicId.matches("^j_id\\d+$"));
            return dynamicId;
        } else if (dynamicTrendIdMatcher.find()) {
            String dynamicTrendId = dynamicTrendIdMatcher.group(1);
            assertThat(dynamicTrendId.matches("^j_id\\d+$"));
            return dynamicTrendId;

        } else {
            throw new RuntimeException("Could not extract Column id from " + idAttribute);
        }
    }

    @Override
    public String extractColumnIdFromSortLink(String idAttribute) {
        Matcher matcher = getSortLinkIdPattern().matcher(idAttribute);
        Matcher trendmatcher = getSortLinkIdTrendPattern().matcher(idAttribute);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else if (trendmatcher.find()) {
            return trendmatcher.group(1);
        }
        throw new RuntimeException("Cannot extract id from " + idAttribute);
    }

    protected Pattern getColumnHeaderIdPattern() {
        return COLUMN_HEADER_ID_PATTERN;
    }

    protected Pattern getSortLinkIdPattern() {
        return COLUMN_SORT_LINK_PATTERN;
    }
    protected Pattern getSortLinkIdTrendPattern() {
        return COLUMN_SORT_LINK_TREND_PATTERN;
    }

    protected Pattern getDynamicColumnIdPattern() {
        return DYNAMIC_COLUMN_ID_PATTERN;
    }

    protected Pattern getDynamicColumnIdTrendPattern() {
        return DYNAMIC_TREND_COLUMN_ID_PATTERN;
    }

}
