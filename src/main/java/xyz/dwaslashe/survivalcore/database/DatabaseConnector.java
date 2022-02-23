package xyz.dwaslashe.survivalcore.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import xyz.dwaslashe.survivalcore.configs.PluginConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {

    private final HikariDataSource dataSource;

    public DatabaseConnector(PluginConfig pluginConfig) {
        this.dataSource = new HikariDataSource(this.getHikariConfig(pluginConfig));
    }

    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            this.connection = dataSource.getConnection();

        }
        return connection;
    }

    private HikariConfig getHikariConfig(PluginConfig pluginConfig) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s",
                pluginConfig.getDatabase().getHost(),
                pluginConfig.getDatabase().getPort(),
                pluginConfig.getDatabase().getTable()));
        hikariConfig.setUsername(pluginConfig.getDatabase().getUsername());
        hikariConfig.setPassword(pluginConfig.getDatabase().getPassword());
        hikariConfig.addDataSourceProperty("maxLifetime", "30000");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");
        return hikariConfig;
    }
}
