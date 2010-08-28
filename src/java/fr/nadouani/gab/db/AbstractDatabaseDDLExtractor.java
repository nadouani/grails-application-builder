package fr.nadouani.gab.db;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

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
	 */
	public String getDatabaseDDL(String dbName, String dbHost, String dbPort,
			String dbUsername, String dbPassword)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		DataSource ds = getDatasource(dbName, dbHost, dbPort, dbUsername,
				dbPassword);
		Platform platform = PlatformFactory.createNewPlatformInstance(ds);

		OutputStream baos = new ByteArrayOutputStream();
		Database db = platform.readModelFromDatabase(dbName);
		new DatabaseIO().write(db, System.out);
		//new DatabaseIO().write(db, baos);

		return baos.toString();
	}
}
