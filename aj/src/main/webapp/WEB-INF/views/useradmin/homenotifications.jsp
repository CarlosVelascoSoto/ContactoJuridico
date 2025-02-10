<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css">

<style>
	#dashboard-notifications th{text-align:center}
	#dashtotnotif{font-size:18px;font-weight:400}
	.odd{
		background-color: beige !important;
	}

	#background-contain {
		position: relative;
		top: -10%;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 990;
		background-color: #EBEFF2;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

  .cargaGif {
    margin-top: 30px;
    min-width: 500px;
    min-height:500px;
    z-index: 2000000000000000;
  }

  .loading-text {
    text-align: center;
    color: black;
    font-size: 400%;
    z-index: 100;
  }
.row{
  height:100%;
}
</style>

<div class="row">

  
  <div class="col-lg-12">
    <!-- Contenido del col-lg-12 -->
    <div class="card-box table-responsive" id="divTable">
      <h3><fmt:message key="home.notification.title" /> <span id="dashtotnotif">(${notifications.size()})</span></h3>
      <table id="dashboard-notifications" data-order='[[4,"desc"]]' class="table table-striped table-bordered">
        <thead>
          <tr>
            <th><fmt:message key="text.document"/></th>
            <th><fmt:message key="text.court" /></th>
            <th><fmt:message key="text.client" /></th>
            <th><fmt:message key="text.date" /> del ultimo movimiento</th>
            <th><fmt:message key="text.seedetails" /></th>
            <th style="min-width: max-content;">Marcar todos como leidos</th>
          </tr>
        </thead>
        <tbody><!-- Mensajes --></tbody>
<div id="background-contain"> 
  <div class="loading-text">Cargando notificaciones</div> 
  <img src="resources/assets/images/loading-logo.gif" alt="Carga animada" class="cargaGif">
</div>
      </table>
    </div>
  </div>
 
    
</div>

<script src="resources/assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.bootstrap.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.buttons.min.js"></script>
<script src="resources/assets/plugins/datatables/buttons.bootstrap.min.js"></script>
<script src="resources/assets/plugins/datatables/jszip.min.js"></script>
<script src="resources/assets/plugins/datatables/pdfmake.min.js"></script>
<script src="resources/assets/plugins/datatables/vfs_fonts.js"></script>
<script src="resources/assets/plugins/datatables/buttons.html5.min.js"></script>
<script src="resources/assets/plugins/datatables/buttons.print.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.fixedHeader.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.keyTable.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.responsive.min.js"></script>
<script src="resources/assets/plugins/datatables/responsive.bootstrap.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.scroller.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.colVis.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.fixedColumns.min.js"></script>
<script src="resources/assets/pages/datatables.init.js"></script>
<script src="resources/assets/js/jquery.min.js"></script>
<script src="https://kit.fontawesome.com/447df55f6c.js" crossorigin="anonymous"></script>
<script src="resources/local/useradmin/homenotifications.js"></script>