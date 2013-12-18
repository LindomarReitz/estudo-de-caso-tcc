<div align="center" class="page-header">
	<h1>Listagem de lançamentos</h1>
</div>
<table class="table table-hover">
	<thead>
		<tr>
			<th>ID</th>
			<th>Descrição</th>
			<th>Data</th>
			<th>Valor</th>
			<th>Observação</th>
			<th>Conta</th>
			<th>Operações</th>
		</tr>
	</thead>
	<c:forEach var="lancamento" items="${lancamentos}">
		<tr>
			<td>${lancamento.id}</td>
			<td>${lancamento.descricao}</td>
			<td><fmt:formatDate value="${lancamento.data.time}" pattern="dd/MM/yyyy" /></td>
			<td>R$ ${lancamento.valor}</td>
			<td>${lancamento.observacao}</td>
			<td>Conta</td>
			<td>
				<a class="btn btn-primary" href="editar?id=${lancamento.id}">Editar</a>
				<a class="btn btn-danger" href="excluirLancamento?id=${lancamento.id}">Excluir</a>
			</td>
		</tr>
	</c:forEach>
</table>
<div align="center">
	<form action="cadastrarLancamento" method="post">
		<input type="submit" class="btn btn-success" value="Cadastrar" />
	</form>
</div>