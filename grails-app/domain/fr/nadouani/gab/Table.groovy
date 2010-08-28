package fr.nadouani.gab

class Table {
	String name
	Project project
	
	static belongsTo = [project: Project]
	
    static constraints = {
    	name (nullable:false, blank:false)
	}
	
	String toString(){
		"$name"
	}
}