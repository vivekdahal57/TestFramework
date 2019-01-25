package com.vh.mi.automation.impl.dataSources;

import com.vh.mi.automation.api.annotations.documentation.NotThreadSafe;
import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.data.DataSourceConfiguration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.testng.SkipException;

import javax.sql.DataSource;

/**
 * Hands out new instances of data sources on every request
 *
 * @author nimanandhar
 */
@NotThreadSafe
public class DataSourceFactory {
    private final ExecutionContext executionContext;
    private static DataSourceFactory INSTANCE;

    public static DataSourceFactory getInstance(ExecutionContext executionContext) {
        if (INSTANCE == null) {
            INSTANCE = new DataSourceFactory(executionContext);
        }
        return INSTANCE;
    }

    private DataSourceFactory(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }


    /**
     * @return a new instance of hedata data source
     * @throws org.testng.SkipException if skipTestThatRequireDatabase is set to true
     */
    public DataSource getNewHeuserDataSource() {
        if (executionContext.skipTestThatRequireDatabase()) {
            throw new SkipException("Skipping tests because it requires database connection");
        }
        DataSourceConfiguration dataSourceConfiguration = executionContext.getHeuserDataSourceConfiguration();
        return new SingleConnectionDataSource(dataSourceConfiguration.getUrl(), dataSourceConfiguration.getUsername(), dataSourceConfiguration.getPassword(), false);
    }

    public DataSource getNewVerticaDataSource() {
        if (executionContext.skipTestThatRequireDatabase()) {
            throw new SkipException("Skipping tests because it requires database connection");
        }
        DataSourceConfiguration dataSourceConfiguration = executionContext.getVerticaDataSourceConfiguration();
        return new SingleConnectionDataSource(dataSourceConfiguration.getUrl(), dataSourceConfiguration.getUsername(), dataSourceConfiguration.getPassword(), false);
    }

}
