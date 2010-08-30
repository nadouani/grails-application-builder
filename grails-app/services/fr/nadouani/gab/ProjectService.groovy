package fr.nadouani.gab

import fr.nadouani.gab.db.AbstractDatabaseDDLExtractor 
import fr.nadouani.gab.db.DatabaseDDLExtractorFactory 
import fr.nadouani.gab.enums.DatabaseType 
import groovy.util.slurpersupport.GPathResult;

class ProjectService {
	
	static transactional = true
	
	private String getDatabaseDDL(Project project){
		DatabaseType type = DatabaseType.valueOf(project.dbType)
		
		AbstractDatabaseDDLExtractor extractor =
				DatabaseDDLExtractorFactory.getInstance().getDatabaseExtractor (type)
		
		def ddl = extractor.getDatabaseDDL (project.dbName, project.dbHost, project.dbPort, project.dbUsername, project.dbPassword)
	}
	
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
	
	private ForeignKey createForeignKey (Table table, GPathResult fk){
		//<foreign-key foreignTable="webapp" name="webapp_url_ibfk_1">
		//	<reference local="webapp_id" foreign="id"/>
		//</foreign-key>
		
		return new ForeignKey(
			'table' : table,
			'name' : fk.@name.text(),
			'foreignTable' : fk.@foreignTable.text(),
			'local' : fk.reference.@local?.text(),
			'forign' : fk.reference.@foreign?.text()
		)
	}
	
	private Index createIndex(Table table, GPathResult idx){
		//<index name="comment_approved_date_gmt">
		//	<index-column name="comment_approved"/>
		//	<index-column name="comment_date_gmt"/>
		//</index>
		
		Index index = new Index (
			'table' : table,
			'name' : idx.@name.text() 
		)
		
		// Add index columns
		idx."index-column".each { ic ->
			Column column = Column.find("from Column as c where c.table=:table and c.name=:name",[table:table, name: ic.@name.text()])
			
			index.addToColumns(column)
		}
		
		return index
	}
	
	private Table createTable(Project project, GPathResult table){
		Table t = new Table('project': project, 'name' : table.@name.text())
		
		return t
	}
	
	boolean saveProject (Project project){
		def projectSaved = true
		
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
			
			projectSaved = project.save(flush:true) 
			if(projectSaved){
				
				// Create the indexes
				tables.each{ t ->
					Table table = Table.find("from Table t where t.project=:project and name=:name", [project: project, name: t.@name.text()])
					
					def fks = t."foreign-key" 
					if(fks){
						fks.each{ fk ->
							ForeignKey foreignKey = createForeignKey(table, fk)
							table.addToForeignKeys(foreignKey)
						}
					}
					
					def indexes = t.index
					if(indexes){
						indexes.each{ idx ->
						Index index = createIndex(table, idx)
						
						table.addToIndexes(index)
						}
					}
				}

				// Flag the project as imported
				project.imported = true
				
				projectSaved = project.save(flush:true)
			}
		}
		
		return projectSaved
	}
}
