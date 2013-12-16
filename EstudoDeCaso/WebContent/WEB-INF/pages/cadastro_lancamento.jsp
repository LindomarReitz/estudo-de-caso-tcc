<div align="center" class="page-header">
	<h1>Cadastro de lan�amento</h1>
</div>
<form class="form" action="salvarLancamento" method="post">
	<div class="form-group">
		<label>Descri��o</label>
		<input type="text" name="descricao" class="form-control" placeholder="Digite uma descri��o" />
	</div>
	<div class="form-group">
		<label>Valor</label>
		<input type="text" name="valor" class="form-control" placeholder="Digite um valor" />
	</div>
	<div class="form-group">
		<label>Observa��o</label>
		<textarea name="observacao" class="form-control" rows="3" placeholder="Digite uma observa��o"></textarea>
	</div>
	<div class="form-group">
		<label>Tipo de lan�amento</label>
		<select name="tipoLancamento" class="form-control">
	    	<option value="SAQUE">Saque</option>
	        <option value="DEPOSITO">Dep�sito</option>
	    </select>
	</div>

	<div class="form-group">
		<input type="submit" class="btn btn-success" value="Cadastrar" />
	</div>
</form>