package fr.nadouani.gab.db;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class MySqlDatabaseDDLExtractor extends AbstractDatabaseDDLExtractor {

	private static final String MYSQL_DRIVER_NAME = "com.mysql.jdbc.Driver";

	@Override
	protected DataSource getDatasource(String dbName, String dbHost,
			String dbPort, String dbUsername, String dbPassword)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		Class.forName(MYSQL_DRIVER_NAME).newInstance();

		MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
		ds.setServerName(dbHost);
		ds.setPortNumber(Integer.valueOf(dbPort));
		ds.setDatabaseName(dbName);
		ds.setUser(dbUsername);
		ds.setPassword(dbPassword);

		return ds;
	}
}
