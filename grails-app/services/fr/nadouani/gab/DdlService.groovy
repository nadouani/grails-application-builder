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
				
		extractor.getDatabaseDDL (project.dbName, project.dbHost, project.dbPort, project.dbUsername, project.dbPassword)
	}
}
