<script type="text/javascript">
function excluir(id) {
	if (confirm("Realmente deseja excluir?")) {
	  $.ajax({  
	    type: "POST",  
	    url: "excluirLancamento",  
	    data: "idLancamento=" + id,
	    success: function(response) {  
			if (response == "sucesso") {
		    	window.location = "listagemLancamentos";
				alert("Lançamento excluído com sucesso!");
			}
	    }  
	  });
	}
};
</script>
<div align="center" class="page-header">
	<h1>Listagem de lançamentos</h1>
</div>
<table class="table table-hover">
	<thead>
		<tr>
			<th>ID</th>
			<th>Descrição</th>
			<th>Data</th>
			<th>Valor (R$)</th>
			<th>Observação</th>
			<th>Conta</th>
			<th>Operações</th>
		</tr>
	</thead>
	<c:forEach var="lancamento" items="${lancamentos}">
		<tr <c:if test="${lancamento.tipoLancamento eq 'SAQUE'}">class="danger"</c:if>
			<c:if test="${lancamento.tipoLancamento eq 'DEPOSITO'}">class="success"</c:if>>
			<td>${lancamento.id}</td>
			<td>${lancamento.descricao}</td>
			<td><fmt:formatDate value="${lancamento.data.time}" pattern="dd/MM/yyyy" /></td>
			<td><fmt:formatNumber currencyCode="BRL" minFractionDigits="2" value="${lancamento.valor}"></fmt:formatNumber></td>
			<td>${lancamento.observacao}</td>
			<td>${lancamento.conta.nome}</td>
			<td>
				<a class="btn btn-primary" href="editar?id=${lancamento.id}">Editar</a>
				<a class="btn btn-danger" onclick="excluir(${lancamento.id})">Excluir</a>
			</td>
		</tr>
	</c:forEach>
</table>
<div align="center">
	<form action="cadastrarLancamento" method="post">
		<input id="botaoCadastrar" type="submit" class="btn btn-success" value="Cadastrar" />
	</form>
</div>