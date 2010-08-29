package fr.nadouani.gab

import fr.nadouani.gab.db.AbstractDatabaseDDLExtractor 
import fr.nadouani.gab.db.DatabaseDDLExtractorFactory 
import fr.nadouani.gab.enums.DatabaseType 
import groovy.util.slurpersupport.GPathResult;

class ProjectService {
	
	static transactional = true
	
	private Column createColumn(GPathResult table, GPathResult column){
		//<column name="id" primaryKey="true" required="true" type="INTEGER" size="10" autoIncrement="true"/>

		return new Column('table':table, 
			'name': column.@name.text(),
			'type': column.@type.text(),
			'size' : column.@size.text(),
			'isPrimaryKey' : column.@primaryKey.text(),
			'isRequired' : column.@required.text(),
			'isAutoIncrement' : column.@autoIncrement.text()
		)
	}
	
	private Table createTable(Project project, GPathResult table){
		Table t = new Table('project': project, 'name' : table.@name.text())
		
		return t
	}
	
	private String getDatabaseDDL(Project project){
		DatabaseType type = DatabaseType.valueOf(project.dbType)
		
		AbstractDatabaseDDLExtractor extractor =
				DatabaseDDLExtractorFactory.getInstance().getDatabaseExtractor (type)
		
		def ddl = extractor.getDatabaseDDL (project.dbName, project.dbHost, project.dbPort, project.dbUsername, project.dbPassword)
	}
	
	boolean saveProject (Project project){
		if(!project.imported){
			
			// Read the datasource ddl
			def ddl = getDatabaseDDL (project)
			project.ddl = ddl
			project.save(flush:true)
			
			// Remove existant tables
			Project.executeUpdate("delete Table t where t.project=?", [project])
			
			// Create the new database structure
			def database = new XmlSlurper().parseText(ddl)
			def tables = database.table
			
			tables.each{ t ->
				Table table = createTable(project, t)
				project.addToTables (table)
				
				t.column.each{ c ->
					Column column = createColumn(t, c)
					table.addToColumns (column)
				}
			}
			
			// Flag the project as imported
			project.imported = true
		}
		
		return project.save(flush:true)
	}
}
