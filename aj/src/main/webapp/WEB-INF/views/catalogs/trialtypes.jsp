<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.List"%>
<%@page import="com.aj.model.Materias"%>

<!-- i18n...-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages" />
<html lang="es-MX">
<meta charset="utf-8">
<meta name="language" content="Spanish">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">

<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

<style>
	.dt-buttons.btn-group a{margin-right:10px;}

	.inlineflex{display:-webkit-inline-flex;display:-webkit-inline-box;display:-moz-inline-box;display:-ms-inline-flexbox;display:inline-flex}
	.tac{text-align:center}
	.dnone{display:none}
	.trn2ms{-webkit-transition:0.2s;-moz-transition:0.2s;-ms-transition:0.2s;-o-transition:0.2s;transition:0.2s}
    .c39c{color:#39c}
    .asLink{cursor:pointer}
    .asLink:hover{color:#1B71D4}
    .btn-icon-base{border:1px solid transparent;background-color:transparent;color:#337ab7}
    .btn-icon-base:hover{color:#337AB7}
	.sweet-overlay{z-index:10010 !important}
	.showSweetAlert{z-index:10011 !important}
	.capitalize{text-transform:capitalize}
	#proceedings{
        background-color: #FFFFFF;
	    border: 1px solid #E3E3E3;
	    border-radius: 4px;
	    padding: 7px 12px;
	    height: 38px;
	    max-width: 100%
	}
	.supLlb{position:absolute;font-weight:400;font-size:12px;top:-9px;left:20px;padding:0 5px;background-color:#fff;z-index:4}
	.containTL{
		position:absolute;
		width:100%;
		max-height:160px;
		top:40px;
		overflow:auto;
		background-color:#fff;
		-webkit-box-shadow:0 0 15px #ccc;
		box-shadow:0 0 15px #ccc;
		z-index:5;
	}
	.closecontainTL{
		position:-webkit-sticky !important;position:sticky !important;
		width:30px;height:30px;top:5px !important;right:5px !important;
		-webkit-border-radius:50%;-moz-border-radius:50%;border-radius:50%;
		-webkit-box-shadow:0 0 5px #444;box-shadow:0 0 5px #444;
		background-color:#fff !important;color:#000 !important
	}
	.closecontainTL:hover{-webkit-box-shadow:0 0 5px #000;box-shadow:0 0 5px #000;}
	.tablelist{width:100%;border:1px solid #ddd;border-collapse:collapse;cursor:pointer}
	.tablelist th, .tablelist td{text-align:left;padding:5px}
	.tablelist tr{border-bottom:1px solid #ddd;cursor:pointer}
	.tablelist tr.header, .tablelist tr:hover{background-color:#f1f1f1;}
	.asField{margin-bottom:30px;padding:10px;border-radius:5px;border:1px solid #ccc}
	.table-hover tbody tr:hover td, .table-hover tbody tr:hover th {background-color:#e6f3ff}

	/*DropDown Filter (ini)*/
	.listfiltersel{width:100%;padding-right:20px}
	.iconlistfilter{font-size:25px;position:absolute;max-height:300px;top:5px;right:13px;overflow:auto;
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
	.w100p{width:100%}

	#add-manage-columnsdata,#edit-manage-columnsdata tbody tr:hover{-webkit-box-shadow:0 0 5px #ccc;box-shadow:0 0 5px #ccc;}
	.custombox-modal-container{margin-top:50px !important}
	[data-customcol=add],[data-customcol=edit]{display:none;padding:15px 20px 15px 10px;border:1px solid #aaa}
	#setway{display:none}
	#addgroupsample, #editgroupsample{padding:20px 0 10px 0;-webkit-border-radius:5px;-moz-border-radius:5px;
		border-radius:5px;border:1px solid #444;background-color:#fbfbfb}
	#edit-trialtype-modal{overflow:auto}
	.sub-modal{
		position:absolute;
		width:100%;
		height:100vh;
		max-height:100%;
		top:5.5%;
		bottom:0;
		z-index:10012
	}
	.sub-modal>div{
		background-color:#fff;
		-webkit-box-shadow:0 0 15px #444;
		box-shadow:0 0 15px #444;
	}
</style>
<!--==============================================================-->
<!-- Start right Content here -->
<!--==============================================================-->
<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row"><!-- Page-Title -->
				<div class="col-sm-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
 						<div class="btn-group pull-right m-t-15">
 							<a href="#" id="addNewTrialType" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.trialtypes" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-order='[[1,"desc"]]' class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th><fmt:message key="text.trialtype" /></th>
									<th><fmt:message key="text.derivesfrom" /></th>
									<th><fmt:message key="text.matter" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="d" items="${trialtype}">
								<tr>
									<td>${d.tipojuicio}</td>
									<c:set var="hasTJAcc" scope="page" value="${false}" />
									<c:forEach var="tjacc" items="${tjacc}">
										<c:if test="${tjacc.accionid==d.accionid}">
											<td>${tjacc.descripcion}</td>
											<td>
												<c:forEach var="m" items="${matter}">
													<c:if test="${m.materiaid==tjacc.materiaid}">${m.materia}</c:if>
													<c:set var="hasTJAcc" scope="page" value="${true}" />
												</c:forEach>
											</td>
										</c:if>
									</c:forEach>
									<c:if test="${hasTJAcc==false}">
										<td></td><td></td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${d.tipojuicioid}" data-toggle="modal" data-target="#edit-trialtype-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsToEdit(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" id="${d.tipojuicioid}" onclick="deleteTrialType(id);"><i class="md md-close"></i></a>
									</td>
									</c:if>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="hidden-xs" style="height: 400px;"></div>
	</div>
</div>
<footer class="footer text-right">
	<fmt:message key="text.copyright" />
</footer>

<!-- Modal -->
<div id="add-trialtype-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;color:#444">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key="text.trialtype" /></h4>
	<div class="custom-modal-text text-left p-t-0">
		<div class="panel-body">
			<form id="formaddtrialtype">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display:none;" id="errorOnadd" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnadd').toggle();">&times;</a>
						<p id="putErrorOnadd"></p>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<label for="addmatter" class="supLlb"><fmt:message key='text.matter' /></label>
							<div class="form-group inlineflex w100p">
								<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key='text.select' />" >
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="addmatter" style="top:40px"></ul>
							</div>
						</div>

						<div class="col-xs-12">
							<label for="addAccion" class="supLlb"><fmt:message key="text.action" /> (<fmt:message key='text.trialtype' />)</label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel" id="addAcccionText"
									onfocus="getAccionByMatterId('addAccion', '#addmatter li.selected', 'ul', 'add');"
									placeholder="<fmt:message key="text.select" />">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="addAccion"></ul>
							</div>
						</div>
						<div class="col-xs-12" style="margin-bottom:20px">
							<span class="supLlb"><fmt:message key='text.way' /></span>
							<span class="form-control" id="addactionway" style="display:inherit;background-color:#f3f3f3"></span>
						</div>
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="addtrialtype" class="supLlb"><fmt:message key='text.trialtypename' /></label>
								<input type="text" class="form-control c39c" id="addtrialtype" placeholder="Ejem. Sucesión testamentaria" autocomplete="off">
							</div>
						</div>
<div class="col-xs-12 dnone">
							<div class="form-group inlineflex w100p">
								<label for="addtrialtypeen" class="supLlb"><fmt:message key='text.trialtypename' /> (<fmt:message key='text.english' />)</label>
								<input type="text" class="form-control c39c" id="addtrialtypeen" placeholder="<fmt:message key='text.optional' />" autocomplete="off">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12"><h5 class="m-b-10"><fmt:message key='text.infocapturetypetrial' /></h5></div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6">
							<input type="checkbox" id="addrequiresactor" autocomplete="off">
							<label for="addrequiresactor" style="display:inline;font-weight:normal;margin-left:5px;cursor:pointer">
								<fmt:message key='text.requires' /> <fmt:message key='text.actor' />
							</label>
						</div>
						<div class="col-xs-12 col-sm-6">
							<input type="checkbox" id="addrequiresthird" autocomplete="off">
							<label for="addrequiresthird" style="display:inline;font-weight:normal;margin-left:5px;cursor:pointer">
								<fmt:message key='text.requires' /> <fmt:message key='text.thirdparty' />
							</label>
						</div>
						<div class="col-xs-12 col-sm-6">
							<input type="checkbox" id="addrequiresdefendant" autocomplete="off">
							<label for="addrequiresdefendant"  style="display:inline;font-weight:normal;margin-left:5px;cursor:pointer">
								<fmt:message key='text.requires' /> <fmt:message key='text.defendant' />
							</label>
						</div>
						<div class="col-xs-12 col-sm-6">
							<button type="button" class="btn btn-info btn-md waves-effect waves-light btn-sm m-b-5"
								id="addColumnData" style="padding:5px 15px" title="<fmt:message key='button.additem' />">
								<fmt:message key="button.add.otherinfo" />
							</button>
						</div>
					</div>
					<div class="row text-overflow">
						<div class="col-xs-12">
							<table id="add-manage-columnsdata" data-order='[[0, "desc"]]' class="table table-striped table-bordered">
								<thead>
									<tr>
										<th><fmt:message key="text.infotoenter" /></th>
										<th><fmt:message key="text.description" /></th>
										<th><fmt:message key="text.ismandatorydata" /></th>
										<th><fmt:message key="text.edit" /></th>
										<th><fmt:message key="text.delete" /></th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
					<div class="row" data-customcol="add">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12 col-sm-6">
									<div class="form-group inlineflex w100p">
										<label for="addtitlecolumn" class="supLlb"><fmt:message key='text.title' /></label>
										<input type="text" class="form-control c39c" id="addtitlecolumn" value="<fmt:message key='text.title' />" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<div class="form-group inlineflex w100p">
										<label for="adddescriptioncolumn" class="supLlb"><fmt:message key='text.description' /> (<fmt:message key='text.optional'/>)</label>
										<input type="text" class="form-control c39c" id="adddescriptioncolumn" value="<fmt:message key='text.description' />"
											 placeholder="<fmt:message key='text.optional'/>" autocomplete="off">
									</div>
								</div>
<div class="col-xs-12 col-sm-6 dnone">
									<div class="form-group inlineflex w100p">
										<label for="addtitlecolumnen" class="supLlb"><fmt:message key='text.title' /> (<fmt:message key='text.english' />)</label>
										<input type="text" class="form-control c39c" id="addtitlecolumnen" placeholder="<fmt:message key='text.optional'/>" autocomplete="off">
									</div>
								</div>
<div class="col-xs-12 col-sm-6 dnone">
									<div class="form-group inlineflex w100p">
										<label for="adddescriptioncolumnen" class="supLlb"><fmt:message key='text.description'/> (<fmt:message key="text.english" />)</label>
										<input type="text" class="form-control c39c" id="adddescriptioncolumnen" placeholder="<fmt:message key='text.optional'/>" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<input type="checkbox" id="addrequiredcolumn" style="margin-left:5px" autocomplete="off">
									<label for="addrequiredcolumn"  style="display:inline;font-weight:normal;margin-left:5px;cursor:pointer"><fmt:message key='text.ismandatorydata' /></label>
								</div>
<div class="col-xs-12 col-sm-6" style="display:none !important">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="edittypecolumn" class="supLlb"><fmt:message key='text.datatype' /></label>
										<div class="select-wrapper w100p">
											<select class="form-control ddsencillo c39c" id="edittypecolumn">
												<option selected disabled><fmt:message key="text.select" /></option>
												<option value="text"><fmt:message key="text.entertext" /></option>
												<option value="checkbox"><fmt:message key="text.checkbox" /></option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<input type="checkbox" id="addmoreasneedded" style="margin-left:5px" autocomplete="off">
									<label for="addmoreasneedded" style="display:inline;font-weight:normal;margin-left:5px;cursor:pointer"><fmt:message key='text.addmoreasneedded' /></label>
								</div>
								<div class="col-xs-12 col-sm-6">
									<label for="addlencolumn" style="font-weigth:normal"><fmt:message key='text.long' /></label>
									<input type="number" id="addlencolumn" min="1" max="12" value="12" style="width:70px" autocomplete="off">
								</div>
							</div>
						</div>

						<div class="col-xs-12">
							<div class="col-xs-12"><h4><fmt:message key='text.sample' /></h4></div>
							<div class="col-xs-12" id="addgroupsample">
								<div class="col-xs-12" data-addcolumn="length">
									<div class="form-group inlineflex w100p">
										<label for="addsamplecolumn" class="supLlb" data-addcolumn="title" style="display:inline"><fmt:message key="text.title" /></label>&nbsp;
										<input class="form-control c39c" id="addsamplecolumn" data-addcolumn="input" placeholder="Descripción" autocomplete="off" type="text">
									</div>
								</div>
							</div>
							<div class="col-xs-12 tac">
								<div class="col-xs-6">
									<button type="button" id="addInsertColumn" class="btn btn-default waves-effect waves-light">
										<fmt:message key="button.apply" />
									</button>
								</div>
								<div class="col-xs-6">
									<button type="button" class="btn btn-warning waves-effect waves-light"
										onclick="$('[data-customcol=add]').fadeToggle('fast', function(){});$('#addrowidx').val('');">
										<fmt:message key="button.close" />
									</button>
									<input type="hidden" id="addrowidx">
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<button type="button" onclick="return addTrialType(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addTrialTypeCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-trialtype-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-trialtype-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="trialtype.update" /></h4>
				<input type="hidden" id="edittrialTypeId">
			</div>
			<div class="modal-body">
				<form id="formsnedit">
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnedit" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnedit').toggle();">&times;</a>
								<p id="putErrorOnedit"></p>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<label for="editmatter" class="supLlb"><fmt:message key='text.matter' /></label>
							<div class="form-group inlineflex w100p">
								<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key='text.select' />" >
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editmatter" style="top:40px"></ul>
							</div>
						</div>

						<div class="col-xs-12">
							<label for="editAccion" class="supLlb"><fmt:message key="text.action" /> (<fmt:message key='text.trialtype' />)</label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel" id="editAcccionText"
									onfocus="getAccionByMatterId('editAccion', '#editmatter li.selected', 'ul', 'edit');"
									placeholder="<fmt:message key="text.select" />">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editAccion"></ul>
							</div>
						</div>
						<div class="col-xs-12" style="margin-bottom:20px">
							<span class="supLlb"><fmt:message key='text.way' /></span>
							<span class="form-control" id="editactionway" style="display:inherit;background-color:#f3f3f3"></span>
						</div>
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="edittrialtype" class="supLlb"><fmt:message key='text.trialtypename' /></label>
								<input type="text" class="form-control c39c" id="edittrialtype" placeholder="Ejem. Sucesión testamentaria" autocomplete="off">
							</div>
						</div>
<div class="col-xs-12 dnone">
							<div class="form-group inlineflex w100p">
								<label for="edittrialtypeen" class="supLlb"><fmt:message key='text.trialtypename' /> (<fmt:message key='text.english' />)</label>
								<input type="text" class="form-control c39c" id="edittrialtypeen" placeholder="<fmt:message key='text.optional' />" autocomplete="off">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12"><h5 class="m-b-10"><fmt:message key='text.infocapturetypetrial' /></h5></div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6">
							<input type="checkbox" id="editrequiresactor" autocomplete="off">
							<label for="editrequiresactor" style="display:inline;font-weight:normal;margin-left:5px;cursor:pointer">
								<fmt:message key='text.requires' /> <fmt:message key='text.actor' />
							</label>
						</div>
						<div class="col-xs-12 col-sm-6">
							<input type="checkbox" id="editrequiresthird" autocomplete="off">
							<label for="editrequiresthird" style="display:inline;font-weight:normal;margin-left:5px;cursor:pointer">
								<fmt:message key='text.requires' /> <fmt:message key='text.thirdparty' />
							</label>
						</div>
						<div class="col-xs-12 col-sm-6">
							<input type="checkbox" id="editrequiresdefendant" autocomplete="off">
							<label for="editrequiresdefendant"  style="display:inline;font-weight:normal;margin-left:5px;cursor:pointer">
								<fmt:message key='text.requires' /> <fmt:message key='text.defendant' />
							</label>
						</div>
						<div class="col-xs-12 col-sm-6">
							<button type="button" class="btn btn-info btn-md waves-effect waves-light btn-sm m-b-5"
								id="editColumnData" style="padding:5px 15px" title="<fmt:message key='button.additem' />">
								<fmt:message key="button.add.otherinfo" />
							</button>
						</div>
					</div>
					<div class="row text-overflow">
						<div class="col-xs-12">
							<table id="edit-manage-columnsdata" data-order='[[0, "desc"]]' class="table table-striped table-bordered">
								<thead>
									<tr>
										<th><fmt:message key="text.infotoenter" /></th>
										<th><fmt:message key="text.description" /></th>
										<th><fmt:message key="text.ismandatorydata" /></th>
										<th><fmt:message key="text.edit" /></th>
										<th><fmt:message key="text.delete" /></th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
					<div class="row" data-customcol="edit">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12 col-sm-6">
									<div class="form-group inlineflex w100p">
										<label for="edittitlecolumn" class="supLlb"><fmt:message key='text.title' /></label>
										<input type="text" class="form-control c39c" id="edittitlecolumn" value="<fmt:message key='text.title' />" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<div class="form-group inlineflex w100p">
										<label for="editdescriptioncolumn" class="supLlb"><fmt:message key='text.description' /> (<fmt:message key='text.optional'/>)</label>
										<input type="text" class="form-control c39c" id="editdescriptioncolumn" value="<fmt:message key='text.description' />"
											 placeholder="<fmt:message key='text.optional'/>" autocomplete="off">
									</div>
								</div>
<div class="col-xs-12 col-sm-6 dnone">
									<div class="form-group inlineflex w100p">
										<label for="edittitlecolumnen" class="supLlb"><fmt:message key='text.title' /> (<fmt:message key='text.english' />)</label>
										<input type="text" class="form-control c39c" id="edittitlecolumnen" placeholder="<fmt:message key='text.optional'/>" autocomplete="off">
									</div>
								</div>
<div class="col-xs-12 col-sm-6 dnone">
									<div class="form-group inlineflex w100p">
										<label for="editdescriptioncolumnen" class="supLlb"><fmt:message key='text.description'/> (<fmt:message key="text.english" />)</label>
										<input type="text" class="form-control c39c" id="editdescriptioncolumnen" placeholder="<fmt:message key='text.optional'/>" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<input type="checkbox" id="editrequiredcolumn" style="margin-left:5px" autocomplete="off">
									<label for="editrequiredcolumn"  style="display:inline;font-weight:normal;margin-left:5px;cursor:pointer"><fmt:message key='text.ismandatorydata' /></label>
								</div>
<div class="col-xs-12 col-sm-6" style="display:none !important">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="edittypecolumn" class="supLlb"><fmt:message key='text.datatype' /></label>
										<div class="select-wrapper w100p">
											<select class="form-control ddsencillo c39c" id="edittypecolumn">
												<option selected disabled><fmt:message key="text.select" /></option>
												<option value="text"><fmt:message key="text.entertext" /></option>
												<option value="checkbox"><fmt:message key="text.checkbox" /></option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<input type="checkbox" id="editmoreasneedded" style="margin-left:5px" autocomplete="off">
									<label for="editmoreasneedded" style="display:inline;font-weight:normal;margin-left:5px;cursor:pointer"><fmt:message key='text.addmoreasneedded' /></label>
								</div>
								<div class="col-xs-12 col-sm-6">
									<label for="editlencolumn" style="font-weigth:normal"><fmt:message key='text.long' /></label>
									<input type="number" id="editlencolumn" min="1" max="12" value="12" style="width:70px" autocomplete="off">
								</div>
							</div>
						</div>

						<div class="col-xs-12">
							<div class="col-xs-12"><h4><fmt:message key='text.sample' /></h4></div>
							<div class="col-xs-12" id="editgroupsample">
								<div class="col-xs-12" data-editcolumn="length">
									<div class="form-group inlineflex w100p">
										<label for="editsamplecolumn" class="supLlb" data-editcolumn="title" style="display:inline"><fmt:message key="text.title" /></label>&nbsp;
										<input class="form-control c39c" id="editsamplecolumn" data-editcolumn="input" placeholder="Descripción" autocomplete="off" type="text">
									</div>
								</div>
							</div>
							<div class="col-xs-12 tac">
								<div class="col-xs-6">
									<button type="button" id="editInsertColumn" class="btn btn-default waves-effect waves-light">
										<fmt:message key="button.apply" />
									</button>
								</div>
								<div class="col-xs-6">
									<button type="button" class="btn btn-warning waves-effect waves-light"
										onclick="$('[data-customcol=edit]').fadeToggle('fast', function(){});$('#editrowidx').val('');">
										<fmt:message key="button.close" />
									</button>
									<input type="hidden" id="editrowidx">
								</div>
							</div>
						</div>
					</div>
			</form>
		</div>
		<div class="modal-footer">
			<button type="button" onclick="return updateTrialType();" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
				<fmt:message key="button.close" />
			</button>
		</div>
		</div>
	</div>
</div>

<div id="addFrom-filtersel-modal" class="sub-modal" style="display:none">
	<div class="modal-dialog">
		<button type="button" class="close" onclick="$('#addFrom-filtersel-modal').hide();" style="margin:10px">
			<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
		</button>
		<h4 class="custom-modal-title capitalize"><fmt:message key="text.add" /> <span class="titleFromFSel"></span></h4>
		<div class="custom-modal-text text-left p-t-0">
			<div class="panel-body">
				<form>
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnAddFromFSel" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddFromFSel').toggle();">&times;</a>
								<p id="putErrorOnAddFromFSel"></p>
								<input type="hidden" id="selIdFSel">
								<input type="hidden" id="selTypeFSel">
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="descriptionFSel" class="supLlb"><fmt:message key='text.description' /></label>
									<input type="text" class="form-control c39c" id="descriptionFSel" autocomplete="off">
								</div>
							</div>
<div class="col-xs-12 dnone">
								<div class="form-group inlineflex w100p">
									<label for="descriptionFSelen" class="supLlb"><fmt:message key='text.description' /> (<fmt:message key="text.english" />)</label>
									<input type="text" class="form-control c39c" id="descriptionFSelen" placeholder="<fmt:message key='text.optional'/>" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="col-xs-12" id="setway">
							<label for="addway" class="supLlb"><fmt:message key='text.way' /></label>
							<div class="form-group inlineflex w100p">
								<input type="text" class="form-control listfiltersel"
									onfocus="getWayList('addway', 'add', 'ul');"
									placeholder="<fmt:message key='text.select' />" >
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="addway" style="top:40px"></ul>
							</div>
						</div>
					</div>
				</form>
				<button type="button" onclick="return addFromFilterSel(this);" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" onclick="$('#addFrom-filtersel-modal').modal('hide');" class="btn btn-danger waves-effect waves-light m-l-10">
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->

<!--==============================================================-->
<!-- End Right content here -->
<!--==============================================================-->
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
<script src="${pageContext.request.contextPath}/web-resources/libs/sweet-alert2/sweetalert2.min.js"></script>

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/globalfunctions.js"></script>
<script src="resources/assets/js/trialtypes.js"></script>

<script type="text/javascript">
	TableManageButtons.init();
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
</script>