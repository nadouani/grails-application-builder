Tables <br />
<ul>
	<g:each var="table" in="${project?.tables}">
		<li>${table.name}</li>
		<ul>
			<g:each var="column" in="${table?.columns}">
				<li>${column?.name}</li>
			</g:each>
		</ul>         
	</g:each>
</ul>