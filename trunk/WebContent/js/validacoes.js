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
		
		if (valor < 0) {
			mensagem += "O campo valor n�o pode ser negativo!\n";
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
	     
		 var dataAtual = new Date();
		 var dataInformada = new Date(ano, mes-1, dia);	     
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
	           } else if (dataAtual < dataInformada) {
	        	   mensagem += "Data n�o pode ser no futuro!\n";
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