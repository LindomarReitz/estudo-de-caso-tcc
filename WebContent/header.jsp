<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script src="bootstrap/js/jquery.mask.min.js" type="text/javascript"></script>
<!-- Bootstrap -->
<script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<title>Listagem de lan�amentos</title>
</head>
	<a style="float: right;" class="btn btn-primary" href="#">Sair</a>
	<br/><br/>
	<div class="navbar" align="center" role="navigation">
		<div class="btn-group">
    		<button class="btn">Bancos</button>
		</div>
		<div class="btn-group">
    		<button id="botaoContas" class="btn" onclick="location.href='listagemContas'">Contas</button>
		</div>
		<div class="btn-group">
    		<button id="botaoLancamentos" class="btn" onclick="location.href='listagemLancamentos'">Lan�amentos</button>
		</div>
		<div class="btn-group">
    		<button class="btn" onclick="location.href='#'">Transfer�ncias</button>
		</div>
	</div>
<body>