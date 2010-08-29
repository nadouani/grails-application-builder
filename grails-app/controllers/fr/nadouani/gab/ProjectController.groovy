package fr.nadouani.gab

class ProjectController {

	def ddlService
	
    def scaffold = Project
	
	def ddl = {
		def ddl = ddlService.getDatabaseDDL (Project.get(params.id)) 
		[ddl: ddl]
	}
}
