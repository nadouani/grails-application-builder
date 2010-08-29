package fr.nadouani.gab.db;

import java.io.IOException;
import java.io.StringWriter;

import javax.sql.DataSource;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;

/**
 * @author nadouani
 */
public abstract class AbstractDatabaseDDLExtractor {

	/**
	 * @param dbName
	 * @param dbHost
	 * @param dbPort
	 * @param dbUsername
	 * @param dbPassword
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	protected abstract DataSource getDatasource(String dbName, String dbHost,
			String dbPort, String dbUsername, String dbPassword)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException;

	/**
	 * @param dbName
	 * @param dbHost
	 * @param dbPort
	 * @param dbUsername
	 * @param dbPassword
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 */
	public String getDatabaseDDL(String dbName, String dbHost, String dbPort,
			String dbUsername, String dbPassword)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException {

		DataSource ds = getDatasource(dbName, dbHost, dbPort, dbUsername,
				dbPassword);
		Platform platform = PlatformFactory.createNewPlatformInstance(ds);

		StringWriter sw = new StringWriter();
		Database db = platform.readModelFromDatabase(dbName);
		new DatabaseIO().write(db, sw);
		
		sw.flush();
		return sw.toString();
	}
}
