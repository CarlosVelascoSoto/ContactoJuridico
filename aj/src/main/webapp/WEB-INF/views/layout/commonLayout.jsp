<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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

		<link rel="stylesheet" type="text/css" href="resources/assets/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<link rel="stylesheet" type="text/css" href="resources/assets/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css">
		<link rel="stylesheet" type="text/css" href="resources/assets/css/core.css">
		<link rel="stylesheet" type="text/css" href="resources/assets/css/components.css">
		<link rel="stylesheet" type="text/css" href="resources/assets/css/icons.css">
		<link rel="stylesheet" type="text/css" href="resources/assets/css/pages.css">
		<link rel="stylesheet" type="text/css" href="resources/assets/css/responsive.css">
		<link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css">
	</head>
	<body class="fixed-left">
		<div id="wrapper" class="enlarged forced">
			<tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="menu" />
			<tiles:insertAttribute name="page" />
			<tiles:insertAttribute name="quicky" />
		</div>
		<div id="popup-notification""></div>
		<script>
			var resizefunc=[],ctx="${pageContext.request.contextPath}";
		</script>
		<script src="resources/assets/js/jquery.min.js"></script>
		<script src="resources/assets/js/bootstrap.min.js"></script>
		<script src="resources/assets/js/detect.js"></script>
		<script src="resources/assets/js/fastclick.js"></script>
		<script src="resources/assets/js/jquery.slimscroll.js"></script>
		<script src="resources/assets/js/jquery.blockUI.js"></script>
		<script src="resources/assets/js/waves.js"></script>
		<script src="resources/assets/js/wow.min.js"></script>
		<script src="resources/assets/js/jquery.core.js"></script>
		<script src="resources/assets/js/jquery.app.js"></script>
		<script src="resources/assets/plugins/sweet-alert2/sweetalert2.min.js"></script>
		<script src="resources/assets/js/globalfunctions.js"></script>

		<div id="notification-area" class="flex"></div>

		<footer class="footer tac">
			<fmt:message key="text.copyright" />
		</footer>
</body>
</html>