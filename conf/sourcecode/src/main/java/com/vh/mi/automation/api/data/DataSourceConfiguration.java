package com.vh.mi.automation.api.data;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.annotations.documentation.Immutable;
import org.apache.commons.lang3.StringUtils;
import org.fest.assertions.Assertions;

/**
 * @author nimanandhar
 */
@Immutable
public class DataSourceConfiguration {
    private final String url;
    private final String username;
    private final String password;
    private final String databaseType;

    private DataSourceConfiguration(String url, String username, String password, String databaseType) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.databaseType = databaseType;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public static class Builder {
        String url;
        String username;
        String password;
        String databaseType = "oracle";

        public Builder withDatabaseType(String databaseType) {
            Preconditions.checkArgument(databaseType.equals("oracle") || databaseType.equals("vertica"), "DatabaseType can be oracle or vertica");
            return this;
        }

        public Builder withUrl(String url) {
            Preconditions.checkArgument(StringUtils.isNotBlank(url), "Data source url cannot be null or empty");
            this.url = url;
            return this;
        }

        public Builder withUsername(String username) {
            Preconditions.checkArgument(StringUtils.isNotBlank(username), "Username cannot be null or empty");
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            Preconditions.checkArgument(StringUtils.isNotBlank(password), "Username cannot be null or empty");
            this.password = password;
            return this;
        }

        public DataSourceConfiguration build() {
            Preconditions.checkState(StringUtils.isNotBlank(url), "Url Blank");
            Preconditions.checkState(StringUtils.isNotBlank(username), "Username Blank");
            Preconditions.checkState(StringUtils.isNotBlank(password), "Passowrd Blank");
            Preconditions.checkState(StringUtils.isNotBlank(databaseType), "DatabaseType Blank");
            Preconditions.checkState(databaseType.equals("oracle") || databaseType.equals("vertical"), "Database Type must be either oracle or vertica");

            return new DataSourceConfiguration(url, username, password, databaseType);
        }
    }
}
