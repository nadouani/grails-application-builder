package fr.nadouani.gab

class Table {
	static mapping = {
		table "gab_table"
	}
	
	String name
	Project project
	
	static belongsTo = [project: Project]
	static hasMany = [columns: Column, foreignKeys: ForeignKey, indexes: Index]
	
    static constraints = {
    	name (nullable:false, blank:false)
	}
	
	String toString(){
		"$name"
	}
}
