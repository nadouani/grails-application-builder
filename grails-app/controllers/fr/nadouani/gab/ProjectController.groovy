package fr.nadouani.gab

class ProjectController {

	def ddlService
	
    def scaffold = Project
	
	def ddl = {
		[ddl: ddlService.getDatabaseDDL (Project.get(params.id)) ]
	}
}
