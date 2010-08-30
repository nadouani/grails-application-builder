package fr.nadouani.gab

class Index {
	static mapping = {
		table "gab_index"
	}
	
	Table table
		
	static belongsTo = [table: Table]
	static hasMany = [columns: Column]
	
    static constraints = {
    }
}
