<div align="center" class="page-header">
	<h1>Listagem de lan�amentos</h1>
</div>
<table class="table table-hover">
	<thead>
		<tr>
			<th>ID</th>
			<th>Descri��o</th>
			<th>Data</th>
			<th>Valor</th>
			<th>Observa��o</th>
			<th>Conta</th>
			<th>Opera��es</th>
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
				<button class="btn btn-primary">Editar</button>
				<button type="button" class="btn btn-danger">Excluir</button>
			</td>
		</tr>
	</c:forEach>
</table>
<div align="center">
	<form action="cadastrarLancamento">
		<input type="submit" class="btn btn-success" value="Cadastrar" />
	</form>
</div>