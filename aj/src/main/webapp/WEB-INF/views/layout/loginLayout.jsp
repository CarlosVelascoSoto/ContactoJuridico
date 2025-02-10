<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html lang="es-MX">
	<head>
		<meta charset="utf-8">
		<meta name="language" content="Spanish">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" lang="es" content="Orden, disponibilidad y seguimiento de escritos y documentos.">
		<meta name="author" lang="es" content="Contacto Jurídico">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="pragma" content="no-cache">
		<link rel="icon" href="resources/assets/images/contacto-juridico-32x32.png" type="image/png" sizes="32x32">
		<link rel="apple-touch-icon" href="resources/assets/images/contacto-juridico-180x180.png" type="image/png" sizes="180x180">
		<!--[if IE]>
			<link rel="shortcut icon" href="resources/assets/images/favicon.ico">
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>
	<body class="widescreen fixed-left-void">
		<div id="wrapper" class="enlarged forced">
			<tiles:insertAttribute name="page" />
		</div>
	</body>
</html>