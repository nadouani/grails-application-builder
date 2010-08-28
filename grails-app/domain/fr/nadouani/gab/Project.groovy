package fr.nadouani.gab

class Project {
	String name
	
	String dbName
	String dbType
	String dbHost
	String dbUsername
	String dbPassword
	String dbPort
	
	User owner
	
	static belongsTo = [owner: User]
	
	static hasMany = [tables:Table]
	
    static constraints = {
		name (nullable:false, blank:false)
		dbName (nullable:false, blank:false)
		dbType (nullable:false, blank:false, inList:['MYSQL', 'ORACLE', 'MSSQL'])
		dbHost (nullable:false, blank:false)
		dbPort(nullable:false, blank:false)
		dbUsername (nullable:false, blank:false)
		dbPassword (nullable:false, blank:true)
    }
	
	String toString(){
		"$name"
	}
}
