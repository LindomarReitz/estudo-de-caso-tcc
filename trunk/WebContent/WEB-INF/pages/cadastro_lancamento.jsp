<script src="js/validacoes.js" type="text/javascript"></script>
<script type="text/javascript">
	function adicionar() {
		var descricao = $('#campoDescricao').val();
		var dataLancamento = $('#dataLancamento').val();
		var valor = $('#campoValor').val();
		valor = valor.replace(".", "");
		valor = valor.replace(",", ".");
		var observacao = $('#campoObservacao').val();
		var tipoLancamento = $('#comboTipoLancamento').val();
		var conta = $('#comboConta').val();

		if (validarCampos(dataLancamento, descricao, valor)) {
			$.ajax({  
				type: "POST",  
				url: "salvarLancamento",  
				data: "idConta=" + conta + "&dataLancamento=" + dataLancamento + "&descricao=" + descricao + "&valor=" + valor + "&observacao=" + observacao 
				+ "&tipoLancamento=" + tipoLancamento,
				success: function(response){
					if (response == "sucesso") {
						window.location = "listagemLancamentos";
					    alert("Lançamento salvo com sucesso!");
				    } else if (response == "saldoInsuficiente") {
				    	alert("Não se pode efetuar um saque com o valor maior que o saldo!");
				    }
				}  
			});
		} else {
			$('#dataLancamento').mask('00/00/0000',{reverse: true});	
			$('#campoValor').mask('000.000.000.000.000,00',{reverse: true});			
		}
	}

	$(document).ready(function(){
		$('#dataLancamento').mask('00/00/0000',{reverse: true});	
		$('#campoValor').mask('000.000.000.000.000,00',{reverse: true});
	});
</script>
<div align="center" class="page-header">
	<h1>Cadastro de lançamento</h1>
</div>
<form class="form-horizontal" role="form">
	<div class="form-group">
		<label class="col-sm-2 control-label">Descrição</label>
		<div class="col-sm-6">
			<input id="campoDescricao" type="text" name="descricao" maxlength="50" class="form-control" placeholder="Digite uma descrição" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Data de lançamento</label>
		<div class="col-sm-6">
			<input id="dataLancamento" type="text" name="data" class="form-control" placeholder="Digite uma data"/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Valor</label>
		<div class="col-sm-6">
			<input id="campoValor" type="text" name="valor" class="form-control" placeholder="Digite um valor" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Observação</label>
		<div class="col-sm-6">
			<textarea id="campoObservacao" name="observacao" class="form-control" rows="3" placeholder="Digite uma observação"></textarea>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Tipo de lançamento</label>
		<div class="col-sm-6">
			<select id="comboTipoLancamento" name="tipoLancamento" class="form-control">
				<option value="SAQUE">Saque</option>
			    <option value="DEPOSITO">Depósito</option>
		    </select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Conta</label>
		<div class="col-sm-6">
			<select id="comboConta" class="form-control">
				<c:forEach var="conta" items="${contas}">
					<option value="${conta.id}">${conta.nome}</option>
				</c:forEach>
		    </select>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<input id="botaoSalvar" type="button" class="btn btn-success" onclick="adicionar()" value="Salvar" />
		</div>
	</div>
</form>