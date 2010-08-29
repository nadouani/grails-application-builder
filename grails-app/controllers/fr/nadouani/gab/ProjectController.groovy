package fr.nadouani.gab

class ProjectController {

	def ddlService
	
    def scaffold = Project
	
	def ddl = {
		def project = Project.get(params.id)
		def ddl = ddlService.getDatabaseDDL (project) 
				
		ddlService.importProjectStructure (project, ddl)
		
		[ddl: ddl, project: project]
	}
}
