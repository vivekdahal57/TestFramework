package com.vh.mi.automation.impl.utils;

import com.github.mnadeem.TableNameParser;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.impl.dataSources.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by i81306 on 1/10/2018.
 */
public class BeforeAfterUtils {
    private static ExecutionContext context = ExecutionContext.forEnvironment(System.getProperty("environment"));
    private static JdbcTemplate JDBCTEMPLATE = new JdbcTemplate(DataSourceFactory.getInstance(context).getNewVerticaDataSource());

    private static String BEFORE_SCHEMA = context.getBeforeSchema();
    private static String AFTER_SCHEMA = context.getAfterSchema();
    private static Logger logger = LoggerFactory.getLogger(BeforeAfterUtils.class);

    public static List<String> compareQueryForMisMatchTables(String query) {
        List<String> mismatchTables = new ArrayList<>();
        if (!isQueryPass(getDataComparisionQuery(query))) {
            mismatchTables.addAll(getDataMismatchTable(query));
        }
        return mismatchTables;
    }

    private static String getQueryForSchema(String query, String schema) {
        String schemaQuery = query;
        Set<String> tables = (Set) new TableNameParser(query).tables();
        for (String tableName : tables) {
            schemaQuery = schemaQuery.replace(tableName, schema + "." + tableName);
        }
        return schemaQuery;
    }

    private static String getDataComparisionQuery(String query) {
        String compareQuery = " SELECT CASE WHEN count = 0 THEN 'PASS' ELSE 'FAIL' END result  FROM ( "
                + " SELECT COUNT(*) as count FROM ( ( ("
                + getQueryForSchema(query, BEFORE_SCHEMA) + " ) MINUS (" + getQueryForSchema(query, AFTER_SCHEMA)
                + " ) ) UNION ALL ( (" + getQueryForSchema(query, AFTER_SCHEMA) + " ) MINUS ( "
                + getQueryForSchema(query, BEFORE_SCHEMA) + ") ) ) b ) a";
        return compareQuery;
    }

    private static String getCountComparisionQueryForTable(String table) {
        String compareQuery = " SELECT CASE WHEN count1 = count2 THEN 'PASS' ELSE 'FAIL' END result "
                + "FROM ( "
                + " SELECT * FROM ( "
                + " SELECT COUNT(*) count1 FROM "
                + BEFORE_SCHEMA + "." + table
                + " ) a, ( "
                + " SELECT COUNT(*) count2 FROM "
                + BEFORE_SCHEMA + "." + table
                + " ) b ) c ";
        return compareQuery;
    }

    private static List<String> getDataMismatchTable(String query) {
        Set<String> tables = (Set) new TableNameParser(query).tables();
        List<String> mismatchTables = new ArrayList<>();
        for (String table : tables) {
            String dataSql = "SELECT * FROM " + table;
            if (isQueryPass(getCountComparisionQueryForTable(table))) {
                if (!isQueryPass(getDataComparisionQuery(dataSql))) mismatchTables.add(table);
            } else {
                mismatchTables.add(table);
            }
        }
        logger.info("Data does not match for Tables:  " + mismatchTables.toString());
        return mismatchTables;
    }

    private static boolean isQueryPass(String query) {
        boolean result;
        try {
            String status = JDBCTEMPLATE.queryForObject(query, String.class);
            result = "PASS".equals(status);
            if(result){
                logger.info("The Executed query is PASS " + query);
            }else{
                logger.info("The Executed query is FAIL " + query);
            }
        } catch (Exception e) {
            logger.info("There was an error while executing the query " + query + "\n" + e);
            result = false;
        }
        return result;
    }
}
