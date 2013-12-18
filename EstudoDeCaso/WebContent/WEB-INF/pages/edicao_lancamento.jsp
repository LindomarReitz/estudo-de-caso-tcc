<div align="center" class="page-header">
	<h1>Edição de lançamento</h1>
</div>
<form class="form" action="editarLancamento" method="post">
	<input type="hidden" name="id" value="${lancamento.id}" />
	<div class="form-group">
		<label>Descrição</label>
		<input id="campoDescricao" type="text" name="descricao" class="form-control" placeholder="Digite uma descrição" value="${lancamento.descricao}"/>
	</div>
	<div class="form-group">
		<label>Data de lançamento</label>
		<input id="dataLancamento" type="date" name="data" class="form-control" value="<fmt:formatDate value="${lancamento.data.time}" pattern="yyyy-MM-dd"/>"/>
	</div>
	<div class="form-group">
		<label>Valor</label>
		<input id="campoValor" type="text" name="valor" class="form-control" placeholder="Digite um valor" value="${lancamento.valor}"/>
	</div>
	<div class="form-group">
		<label>Observação</label>
		<textarea id="campoObservacao" name="observacao" class="form-control" rows="3" placeholder="Digite uma observação">${lancamento.observacao}</textarea>
	</div>
	<div class="form-group">
		<label>Tipo de lançamento</label>
		<select name="tipoLancamento" class="form-control">
		    <option value="SAQUE">Saque</option>
		    <option value="DEPOSITO">Depósito</option>
	    </select>
	</div>
	<div class="form-group">
		<input id="botaoEditar" type="submit" class="btn btn-success" value="Editar" />
	</div>
</form>