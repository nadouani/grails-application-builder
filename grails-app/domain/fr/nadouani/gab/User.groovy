package fr.nadouani.gab

class User {	
	String username
	String password
	String email
	
	static hasMany = [projects: Project]
	
    static constraints = {
		username(nullable:false, blank:false)
		password(nullable:false, blank:false, password:true)
		email(email:true, nullable:false, blank:false)
    }
	
	String toString(){
		"$username"
	}
}
