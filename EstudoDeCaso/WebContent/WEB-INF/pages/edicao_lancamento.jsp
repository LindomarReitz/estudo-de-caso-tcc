<script type="text/javascript">
	function editar() {
		var idLancamento = $('#idLancamento').val();
		var descricao = $('#campoDescricao').val();
		var dataLancamento = $('#dataLancamento').val();
		var valor = $('#campoValor').val();
		valor = valor.replace(".", "");
		valor = valor.replace(",", ".");
		var observacao = $('#campoObservacao').val();
		var tipoLancamento = $('#comboTipoLancamento').val();
		var conta = $('#idConta').val();

		if (validarCampos(dataLancamento, descricao, valor)) {
			$.ajax({  
				type: "POST",  
				url: "editarLancamento",  
				data: "idConta=" + conta + "&dataLancamento=" + dataLancamento + "&id=" + idLancamento +  "&descricao=" + descricao
				+ "&valor=" + valor + "&observacao=" + observacao + "&tipoLancamento=" + tipoLancamento,
				success: function(response){
					if (response == "sucesso") {
						window.location = "listagemLancamentos";
					    alert("Lançamento editado com sucesso!");
				    } else if (response == "saldoInsuficiente") {
				    	alert("Não se pode efetuar um saque com o valor maior que o saldo!");
				    }
				}  
			});
		} else {
			$('#dataLancamento').mask('00/00/0000',{reverse: true});	
			$('#campoValor').mask('000.000.000.000.000,00',{negative: true, reverse: true});			
		}
	}
	
	function validarCampos(dataLancamento, descricao, valor) {
		var mensagem = "";
		var data="";
		
		if ((data=validarData(dataLancamento)) != ""){
		    mensagem += data;
		}
		
		if (dataLancamento == "") {
			mensagem += "O campo data de lançamento não pode estar em branco!\n";
		}
		
		if (descricao == "") {
			mensagem += "O campo descrição não pode estar em branco!\n";
		}

		if (valor == 0) {
			mensagem += "O campo valor não pode ser zero!\n";
		}
		
		if (mensagem != "") {
			alert("Os seguintes erros foram encontrados:\n" + mensagem);
			return false;
		}
		
		return true;
	};
	
	function validarData(data) {
	    var msgErro = 'Data inválida.\n';
	    var mensagem = "";
	    if ((data.length == 10) && (data !='')){
	     var dia = data.substring(0,2);
	     var mes = data.substring(3,5);
	     var ano = data.substring(6,10);
	     
		 var dataAtual = new Date();
		 var dataInformada = new Date(ano, mes-1, dia);	     
	     if(mes > 12) mensagem ="Mês inválido.\n";
	     if(dia > 31) mensagem +="Dia inválido.\n";
	     if((mes==04 && dia > 30) || (mes==06 && dia > 30) || (mes==09 && dia > 30) || (mes==11 && dia > 30)){
	       mensagem +="Dia incorreto !!! O mês especificado contém no máximo 30 dias.\n";
	       return mensagem;
	     }else{ //1
	       if(ano%4!=0 && mes==2 && dia>28){
	    	 mensagem +="Data incorreta!! O mês especificado contém no máximo 28 dias.\n";
	         return mensagem;
	         } else{ //2
	          if(ano%4==0 && mes==2 && dia>29){
	           mensagem +="Data incorreta!! O mês especificado contém no máximo 29 dias.\n";
	           return mensagem;
	           } else if (dataAtual < dataInformada) {
	        	   mensagem += "Data não pode ser no futuro!\n";
	        	   return mensagem;
	           } else { //3
	               return mensagem;
	           } //3-else
	         }//2-else
	       }//1-else
	   }else { //5
		 mensagem =msgErro;
	     return mensagem;
	  }//5-else
	}
	
	$(document).ready(function(){
		$('#dataLancamento').mask('00/00/0000',{reverse: true});	
		$('#campoValor').mask('000.000.000.000.000,00',{reverse: true});
	});
</script>
<div align="center" class="page-header">
	<h1>Edição de lançamento</h1>
</div>
<form class="form-horizontal" role="form">
	<input id="idLancamento"  type="hidden" name="id" value="${lancamento.id}" />
	<div class="form-group">
		<label class="col-sm-2 control-label">Descrição</label>
		<div class="col-sm-6">
			<input id="campoDescricao" type="text" name="descricao" maxlength="50" class="form-control" placeholder="Digite uma descrição" value="${lancamento.descricao}" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Data de lançamento</label>
		<div class="col-sm-6">
			<input id="dataLancamento" type="text" name="data" class="form-control" value="<fmt:formatDate value="${lancamento.data.time}" pattern="dd/MM/yyyy"/>"/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Valor</label>
		<div class="col-sm-6">
			<input id="campoValor" type="text" name="valor" class="form-control" placeholder="Digite um valor" value="<fmt:formatNumber currencyCode="BRL" minFractionDigits="2" value="${lancamento.valor}"></fmt:formatNumber>" />
			
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Observação</label>
		<div class="col-sm-6">
			<textarea id="campoObservacao" name="observacao" class="form-control" rows="3" placeholder="Digite uma observação">${lancamento.observacao}</textarea>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Tipo de lançamento</label>
		<div class="col-sm-6">
			<select id="comboTipoLancamento" name="tipoLancamento" class="form-control">
				<option value="SAQUE" <c:if test="${lancamento.tipoLancamento eq 'SAQUE'}">selected="selected"</c:if>>Saque</option>
			    <option value="DEPOSITO" <c:if test="${lancamento.tipoLancamento eq 'DEPOSITO'}">selected="selected"</c:if>>Depósito</option>
		    </select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Conta</label>
		<div class="col-sm-6">
			<select id="idConta" class="form-control" disabled="disabled">
					<option value="${lancamento.conta.id}">${lancamento.conta.nome}</option>
		    </select>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<input id="botaoEditar" type="button" class="btn btn-success" onclick="editar()" value="Editar" />
		</div>
	</div>
</form>