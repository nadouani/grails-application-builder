package fr.nadouani.gab

class Table {
	static mapping = {
		table "gab_table"
		
		columns cascade:'delete, all-delete-orphan'
		
	}
	
	String name
	Project project
	
	static belongsTo = [project: Project]
	static hasMany = [columns: Column]
	
    static constraints = {
    	name (nullable:false, blank:false)
	}
	
	String toString(){
		"$name"
	}
}
