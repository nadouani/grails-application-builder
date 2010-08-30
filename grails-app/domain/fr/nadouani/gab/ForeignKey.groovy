package fr.nadouani.gab

class ForeignKey {
	static mapping = {
		table "gab_foreign_key"
	}
	
	Table table
	
	String name
	String foreignTable
	
	static belongsTo = [table: Table]
	

    static constraints = {
    }
	
	String toString(){
		"$name ref($foreignTable)"
	}
}
