<script type="text/javascript">
	function adicionar() {
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
				url: "salvarLancamento",  
				data: "idConta=" + conta + "&dataLancamento=" + dataLancamento + "&descricao=" + descricao + "&valor=" + valor + "&observacao=" + observacao 
				+ "&tipoLancamento=" + tipoLancamento,
				success: function(response){
					if (response == "sucesso") {
						window.location = "listagemLancamentos";
					    alert("Lan�amento salvo com sucesso!");
				    } else if (response == "saldoInsuficiente") {
				    	alert("N�o se pode efetuar um saque com o valor maior que o saldo!");
				    }
				}  
			});
		} else {
			$('#dataLancamento').mask('00/00/0000',{reverse: true});	
			$('#campoValor').mask('000.000.000.000.000,00',{reverse: true});			
		}
	}
	
	function validarCampos(dataLancamento, descricao, valor) {
		var mensagem = "";
		var data="";
		
		if ((data=validarData(dataLancamento)) != ""){
		    mensagem += data;
		}
		
		if (dataLancamento == "") {
			mensagem += "O campo data de lan�amento n�o pode estar em branco!\n";
		}
		
		if (descricao == "") {
			mensagem += "O campo descri��o n�o pode estar em branco!\n";
		}

		if (valor == 0) {
			mensagem += "O campo valor n�o pode ser zero!\n";
		}
		
		if (mensagem != "") {
			alert("Os seguintes erros foram encontrados:\n" + mensagem);
			return false;
		}
		
		return true;
	};
	
	
	function validarData(data) {
	    var msgErro = 'Data inv�lida.\n';
	    var mensagem = "";
	    if ((data.length == 10) && (data !='')){
	     var dia = data.substring(0,2);
	     var mes = data.substring(3,5);
	     var ano = data.substring(6,10);
	     if(mes > 12) mensagem ="M�s inv�lido.\n";
	     if(dia > 31) mensagem +="Dia inv�lido.\n";
	     if((mes==04 && dia > 30) || (mes==06 && dia > 30) || (mes==09 && dia > 30) || (mes==11 && dia > 30)){
	       mensagem +="Dia incorreto !!! O m�s especificado cont�m no m�ximo 30 dias.\n";
	       return mensagem;
	     }else{ //1
	       if(ano%4!=0 && mes==2 && dia>28){
	    	 mensagem +="Data incorreta!! O m�s especificado cont�m no m�ximo 28 dias.\n";
	         return mensagem;
	         } else{ //2
	          if(ano%4==0 && mes==2 && dia>29){
	           mensagem +="Data incorreta!! O m�s especificado cont�m no m�ximo 29 dias.\n";
	           return mensagem;
	           } else{ //3
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
	<h1>Cadastro de lan�amento</h1>
</div>
<form class="form-horizontal" role="form">
	<div class="form-group">
		<label class="col-sm-2 control-label">Descri��o</label>
		<div class="col-sm-6">
			<input id="campoDescricao" type="text" name="descricao" maxlength="50" class="form-control" placeholder="Digite uma descri��o" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Data de lan�amento</label>
		<div class="col-sm-6">
			<input id="dataLancamento" type="text" name="data" class="form-control"/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Valor</label>
		<div class="col-sm-6">
			<input id="campoValor" type="text" name="valor" class="form-control" placeholder="Digite um valor" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Observa��o</label>
		<div class="col-sm-6">
			<textarea id="campoObservacao" name="observacao" class="form-control" rows="3" placeholder="Digite uma observa��o"></textarea>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Tipo de lan�amento</label>
		<div class="col-sm-6">
			<select id="comboTipoLancamento" name="tipoLancamento" class="form-control">
				<option value="SAQUE">Saque</option>
			    <option value="DEPOSITO">Dep�sito</option>
		    </select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Conta</label>
		<div class="col-sm-6">
			<select id="idConta" class="form-control">
				<c:forEach var="conta" items="${contas}">
					<option value="${conta.id}">${conta.nome}</option>
				</c:forEach>
		    </select>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<input id="botaoCadastrar" type="button" class="btn btn-success" onclick="adicionar()" value="Cadastrar" />
		</div>
	</div>
</form>