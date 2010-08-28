package fr.nadouani.gab.db;

import fr.nadouani.gab.enums.DatabaseType;

/**
 * @author nadouani
 * 
 */
public class DatabaseDDLExtractorFactory {

	private static DatabaseDDLExtractorFactory instance = new DatabaseDDLExtractorFactory();

	private DatabaseDDLExtractorFactory() {
	}

	public static DatabaseDDLExtractorFactory getInstance() {
		return instance;
	}

	public AbstractDatabaseDDLExtractor getDatabaseExtractor(DatabaseType dbType) {
		switch (dbType) {
		case MYSQL:
			return new MySqlDatabaseDDLExtractor();
		case ORACLE:
			return new OracleDatabaseDDLExtractor();
		case MSSQL:
			return new MSSqlDatabaseDDLExtractor();
		default:
			throw new RuntimeException("Unknown database type");
		}
	}
}
