package fr.nadouani.gab

import fr.nadouani.gab.db.AbstractDatabaseDDLExtractor;
import fr.nadouani.gab.db.DatabaseDDLExtractorFactory;
import fr.nadouani.gab.enums.DatabaseType

class DdlService {
	
	static transactional = true
	
	String getDatabaseDDL(Project project) {
		
		DatabaseType type = DatabaseType.valueOf(project.dbType)
		
		AbstractDatabaseDDLExtractor extractor = 
				DatabaseDDLExtractorFactory.getInstance().getDatabaseExtractor (type)
				
		return extractor.getDatabaseDDL (project.dbName, project.dbHost, project.dbPort, project.dbUsername, project.dbPassword)
	}
	
	void importProjectStructure (Project project, String ddl){
		def database = new XmlSlurper().parseText(ddl)
		def tables = database.table
		
		project.tables = []
		
		tables.each{ t ->
			Table table = new Table('project': project, 'name' : t.@name.text())
			project.addToTables (table)
			log.info ("add table $table.name")
			
			t.column.each{ c ->
				Column column = new Column('table':t, 'name': c.@name.text() )
				table.addToColumns (column)
			}
		}
	}
}
