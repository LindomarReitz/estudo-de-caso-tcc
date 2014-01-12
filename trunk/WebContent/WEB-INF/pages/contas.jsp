<div align="center" class="page-header">
	<h1>Listagem de contas</h1>
</div>
<table class="table table-hover">
	<thead>
		<tr>
			<th>ID</th>
			<th>Nome</th>
			<th>Número Agência</th>
			<th>Número Conta</th>
			<th>Banco</th>
			<th>Saldo atual (R$)</th>
			<th>Usuário</th>
			<th>Operações</th>
		</tr>
	</thead>
	<c:forEach var="conta" items="${contas}">
		<tr>
			<td>${conta.id}</td>
			<td>${conta.nome}</td>
			<td>${conta.numeroAgencia}</td>
			<td>${conta.numeroConta}</td>
			<td>${conta.banco.nome}</td>
			<td><fmt:formatNumber currencyCode="BRL" minFractionDigits="2" value="${conta.saldoAtual}"></fmt:formatNumber></td>
			<td>${conta.usuario.nome}</td>
			<td>
				<a class="btn btn-primary" href="#">Editar</a>
				<a class="btn btn-danger" href="#">Excluir</a>
			</td>
		</tr>
	</c:forEach>
</table>
<div align="center">
	<form action="#" method="post">
		<input type="submit" class="btn btn-success" value="Cadastrar" />
	</form>
</div>