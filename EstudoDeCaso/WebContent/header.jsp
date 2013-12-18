<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="bootstrap/js/jquery.mask.min.js" type="text/javascript"></script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<title>Listagem de lançamentos</title>
</head>
	<a style="float: right;" class="btn btn-primary" href="#">Sair</a>
	<br/><br/>
	<div class="navbar" align="center" role="navigation">
		<div class="btn-group">
    		<button class="btn">Bancos</button>
		</div>
		<div class="btn-group">
    		<button class="btn">Contas</button>
		</div>
		<div class="btn-group">
    		<button class="btn" onclick="location.href='listagemLancamentos'">Lançamentos</button>
		</div>
		<div class="btn-group">
    		<button class="btn" onclick="location.href='listagemTransferencias'">Transferências</button>
		</div>
	</div>
<body>