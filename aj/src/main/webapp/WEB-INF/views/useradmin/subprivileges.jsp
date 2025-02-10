<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!--i18n-->
<fmt:setBundle basename="messages" />
<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">

<link rel="stylesheet" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">

<link rel="stylesheet" href="resources/css/privileges.css">

<style>
	.dt-buttons.btn-group a{margin-right:10px;}
	.unuse{background-color:#eee;}
	.tac, th{text-align:center;}
	.capitalize{text-transform:capitalize}
	/*DropDown (ini)*/
	select{-webkit-appearance:none;moz-appearance:none;appearance:none}
	.select-wrapper{display:table-cell; background-color:#FFF;}
	.select-wrapper:after{
		content:"\25bc";
		position:absolute;
		top:13px;
		right:10px;
		font-size:14px;
		color:#39c;
		z-index:2;
	}
	.ddsencillo, .select-wrapper{
		position:relative;
		display:inline-block;
		height:42px;
		border-radius:7px;
		background-color:#FFF;
		cursor:pointer;
	}
	.ddsencillo::-ms-expand {display: none;}
	.ddsencillo{
		width:100%;
		padding:10px 40px 10px 10px;
		-webkit-appearance:none;
		-moz-appearance:none;
		-ms-appearance:none;
		appearance:none;
		border:1px solid #bbb;
		background-color:transparent;
		z-index:3;
	}/*DropDown (fin)*/
	.b-chk{position:relative; width:100%; top:-10px; margin:10px;}
	.b-chkAll{
		position:relative;
		width:210px;
		height:25px;
		top:9px;
		margin:10px;
		display:inline-block;
	}
	.switch{
		position:relative;
		display:inline-block;
		width:40px;
		height:20px;
	}
	.slider{
		position:absolute;
		cursor:pointer;
		top:0;
		left:0;
		right:-5px;
		bottom:0;
		background-color:#ccc;
		-webkit-transition:.4s;
		transition:.4s;
	}
	.slider:before{
		position:absolute;
		content:"";
		height:12px;
		width:12px;
		left:3px;
		bottom:4px;
		background-color:white;
		-webkit-transition:.4s;
		transition:.4s;
	}
	.switch input{display:none;}
	.slider.round{border-radius:34px;}
	.slider.round:before{border-radius:50%;}
	input:checked ~ .slider{background-color:#9acd32;}
	input:focus ~ .slider{box-shadow:0 0 1px #9acd32;}
	input:checked ~ .slider:before{
		-webkit-transform:translateX(26px);
		-ms-transform:translateX(26px);
		transform:translateX(26px);
	}
	/*DropDown Filter (ini)*/
	.listfiltersel{width:100%;padding-right:20px}
	.iconlistfilter{font-size:25px;position:absolute;max-height:300px;top:31px;right:13px;overflow:auto;
		background-color:#fff;color:#39c;cursor:pointer}
	.iconlistfilter:focus{background-color:lavender}
    .ddListImg{position:absolute;display:none;width:100%;max-height:30vh;min-height:100px;overflow-y:auto;
    	-webkit-box-shadow:0 0 15px #aaa;box-shadow:0 0 15px #aaa;background-color:#fff;z-index:21}
    .ddListImg li{display:-webkit-box;display:-moz-box;display:-ms-flexbox;display:-webkit-flex;display:flex;
      border-bottom:1px solid #dedede;-webkit-align-items:center;align-items:center;min-height:54px;
      background-color:#fff;cursor:pointer}
    .ddListImg li:hover,.ddListImg li.selected{background-color:#cce6ff}
    .ddListImg img{width:34px;margin:5px 10px;vertical-align:middle}
    .noimgOnList{padding:0;min-height:34px}
    .noimgOnList li{min-height:34px;padding:0 10px}
    .addnewoplist{-webkit-justify-content:center;justify-content:center}
    /*DropDown Filter (fin)*/
</style>
 <!--==============================================================-->
 <!--Start right Content here-->
 <!--==============================================================-->
 <div class="content-page">
	<div class="content"><!--Start content-->
		<div class="container">
			<div class="row"><!--Page-Title-->
				<div class="col-sm-12">
					<div class="btn-group pull-right m-t-15">
							<a href="#" id="addNewSubPrivilege" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
								data-overlayColor="#36404a" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
							<fmt:message key="button.new" />
						</a>
					</div>
					<h4 class="page-title capitalize"><fmt:message key="text.subprivileges" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-order='[[0, "asc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th style="text-align:left"><fmt:message key="text.modulename" /></th>
									<th style="text-align:left"><fmt:message key="text.actionbelogspage" /></th>
									<th><fmt:message key="text.edit"/></th>
									<th><fmt:message key="text.delete"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="sp" items="${subp}">
									<tr>
										<td>${sp.menu}</td>
										<td>${sp.link}</td>
										<td class="tac">
											<a href="#" data-toggle="modal" data-target="#edit-subprivilege-modal" 
												class="table-action-btn" title="<fmt:message key='text.edit' />" onclick="getSubprivToEdit('${sp.menuid}');">
												<i class="md md-edit"></i>
											</a>
										</td>
										<td class="tac">
											<a href="#" data-toggle="modal" class="table-action-btn" title="<fmt:message key='text.delete' /> ${sp.menu}" 
												onclick="deleteSubPrivilege('${sp.menuid}','${sp.menu}');"><i class="md md-delete"></i>
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>						
					</div>
				</div>
			</div>
			<div class="hidden-xs" style="height:300px;"></div>
		</div>
	</div>
	<footer class="footer text-right">
		<fmt:message key="text.copyright" />
	</footer>
</div>

<div id="subprivileges-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key='text.subprivileges' /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form id="formsubprivilege">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display:none" id="errorOnAddSubPriv" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddSubPriv').toggle();">&times;</a>
						<p id="putErrorOnAddSubPriv"></p>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="spDescription" class="supLlb"><fmt:message key='text.description' /></label>
								<input type="text" class="form-control c39c" id="spDescription" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="spUrl" class="supLlb"><fmt:message key="text.actionbelogspage" /></label>
								<input type="text" class="form-control c39c" id="spUrl" autocomplete="off">
							</div>
						</div>
					</div>
				</div>
			</form>
			<button type="button" onclick="return addSubPrivilege(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addSubPrivilegeCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-subprivilege-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-subprivilege-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="subprivilege.update" /></h4> 
			</div>
			<div class="modal-body">
				<form id="formeditpermisos">
					<div class="form-group row ">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEditSubPriv" class="alert alert-danger fade in">
								<a href="#" class="close" aria-label="close" onclick="$('#errorOnEditSubPriv').toggle();">&times;</a>
								<p id="puterrorOnEditSubPriv"></p>
								<input type="hidden" id="edSpId">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="edSpDescription" class="supLlb"><fmt:message key='text.description' /></label>
								<input type="text" class="form-control c39c" id="edSpDescription" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="edSpUrl" class="supLlb"><fmt:message key="text.actionbelogspage" /></label>
								<input type="text" class="form-control c39c" id="edSpUrl" autocomplete="off">
							</div>
						</div>
					</div>

				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" onclick="updateEditPrivilege();" class="btn btn-info waves-effect waves-light">
					<fmt:message key="button.savechanges" />
				</button>
			</div>
		</div>
	</div>
</div><!--/.modal-->
<!--==============================================================-->
<!--End Right content here-->
<!--==============================================================-->
<script src="resources/assets/js/jquery.min.js"></script>
<!-- Modal-Effect -->
<script src="resources/assets/plugins/custombox/js/custombox.min.js"></script>
<script src="resources/assets/plugins/custombox/js/legacy.min.js"></script>
<script src="resources/assets/js/jetaccess.js"></script>

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
<script src="resources/assets/js/subprivileges.js"></script>
<script src="resources/assets/js/globalfunctions.js"></script>

<script type="text/javascript">
	TableManageButtons.init();

	function checks(id){
		var chk=$("#"+id).is(':checked'),priv=$("input[name^='mp_']");
		for(o=0; o<priv.length; o++)
			if(priv[o].checked==!1){
				chk=!1; 
				break;
			}
		$('#editallPriv').prop("checked", chk);
	}

	function allChecks(){
		$("input[name^='mp_']").prop("checked", $('#editallPriv').is(':checked'));
	}
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});

</script>