<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">

<style>
	textarea{min-height:auto !important;margin-top:5px}
	#test,#clear{margin:10px 0 0 10px;background-color:#fff;-webkit-border-radius:3px;-moz-border-radius:3px;color:#39c}
	#test{border:1px solid #39c}
	#test:hover{-webkit-box-shadow:inset 0 0 4px #39c;box-shadow:inset 0 0 4px #39c}
	#clear{border:1px solid #f60}
	#clear:hover{-webkit-box-shadow:inset 0 0 4px #f60;box-shadow:inset 0 0 4px #f60}
	.table-responsive{max-height:70vh}
	
	.testClientes{position:absolute;top:23vh;right:0;width:200px;box-shadow:0 0 15px;padding:10px;background-color:#eee}
	#iniciar{
		display:block;margin:10px auto 0 auto;border:1px solid #39c;border-radius:4px;
		color:#39c;padding:2px 20px;background-color:aliceblue
	}
	#iniciar:hover, #iniciar:focus{box-shadow:0 0 15px #444}
	hr{margin:10px 0}
	
	.right-180{right:-180px}
	.toggleRight{position:relative;left:-20px;border-radius:50%;color:#39c;background-color:#fff;
		box-shadow:0 0 15px #444;top:-17px;border:1px solid transparent}
</style>
<div class="content-page" style="height:95vh;margin-bottom:3px">
	<div class="content" style="height:85vh;margin-bottom:3px;overflow:hidden">
		<form>
			<div class="row">
				<div class="col-xs-10">
					<textarea id="mytext" class="form-control c39c" rows="2" title="%25"
						placeholder="<fmt:message key='text.communicationtype' />"></textarea>
				</div>
				<div class="col-xs-2">
					<button type="button" id="test" class="trn3ms"><fmt:message key="button.ok" /></button>
					<button type="button" id="clear" class="trn3ms"><fmt:message key="text.clear" /></button>
				</div>
			</div>
		</form>
		<div class="card-box table-responsive m-0" style="width:98%;height:50vh;resize:both">
			<table id="datatable-buttons" data-order='[[0,"asc"]]' class="table table-striped table-bordered"></table>
		</div>
	</div>
</div>

<div class="testClientes">
	<button class="toggleRight" onclick="toggleRight()"><i class="material-icons">compare_arrows</i></button>
	<input type="text" id="clid" placeholder="clid">
	<input type="text" id="ccid" placeholder="ccid">
	<hr>
	<input type="text" id="reftype" placeholder="reftype">
	<input type="text" id="refid" placeholder="refid">
	<hr>
	<label>Relacionados:<input type="checkbox" id="retRel"></label>
	<input type="text" id="status" placeholder="a=activo; s=Suspendido; c=concluido" title="a=activo; s=Suspendido; c=concluido">
	<button type="button" id="iniciar" onclick="checkAccessDoc();" class="trn3ms">Iniciar</button>
</div>

<script src="resources/assets/js/jquery.min.js"></script>
<!-- Modal-Effect -->
<script src="resources/assets/plugins/custombox/js/custombox.min.js"></script>
<script src="resources/assets/plugins/custombox/js/legacy.min.js"></script>

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

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/example.js"></script>

<script>
	// ***** TEST (ini) *****
	function checkAccessDoc(){
		var param='',clid=$('#clid').val()||0, ccid=$('#ccid').val()||0,
			reftype=$('#reftype').val()||0, refid=$('#refid').val()||0,
			retRel=$('#retRel').val(), userAsId=$('#userAsId').val();
		param='clientid='+clid + "&companyclientid="+ccid
			+ "&reftype="+reftype + "&refid="+refid + "&retRel="+retRel
			+ '&status='+$('#status').val();
		$.ajax({
			type:'POST',
			url:ctx+"/checkAccessDoc",
			data:param,
			async:false,
			success:function(data){
				if(data==null){
					swal(i18n('msg_warning'),"Sin información","warning");
					return;
				}
				$('#mytext').val(allText);
			},error:function(resp){
				swal(i18n('msg_warning'),'No se pudo obtener la información',"error");
			}
		});
		// ***** TEST (fin) *****
	};
	function toggleRight(){
		$('.testClientes').toggleClass('right-180');
	}
</script>