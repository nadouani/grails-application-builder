package fr.nadouani.gab

class Column {
	static mapping = {
		table "gab_column"
	}
	
	String name
	String type
	Integer size
	Boolean isPrimaryKey
	Boolean isRequired
	Boolean isAutoIncrement = false
	
	Table table
	
	static belongsTo = [table: Table]

    static constraints = {
		name (nullable:false, blank:false)
    }
	
	String toString(){
		"$name " + (isPrimaryKey ? "(key)" : "")
	} 
}
