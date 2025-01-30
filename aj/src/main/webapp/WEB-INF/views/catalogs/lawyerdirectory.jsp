<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.aj.utility.UserDTO"%>
<%	UserDTO userDto=(UserDTO) request.getSession().getAttribute("UserDTO");
	int role=userDto.getRole();
	pageContext.setAttribute("userrole",role);%>

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
<c:set var="userrole" scope="page" value="${userrole}" />

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css">

<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/basic.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/dropzone.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/lc-select-main/css/light.css">

<style>
	.lcslt-wrap div, .lcslt-wrap input{font-size: 14px;border: 1px solid #ccc;
		-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
		-webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
		-moz-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
		-ms-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
		-o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
		transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
	}
	.lcslt{height:38px;max-width:100%;padding:7px 12px;border:1px solid #E3E3E3;border-radius:4px;
		background-color:#FFFFFF;color:#565656;-webkit-box-shadow:none;box-shadow:none;
		-webkit-transition:all 300ms linear;-moz-transition:all 300ms linear;
		-ms-transition:all 300ms linear;-o-transition:all 300ms linear;transition:all 300ms linear
	}
	.lcslt-multi-selected, .lcslt-placeholder, .lc-select-dd-scroll li, .lc-select-dd-scroll li span,
	.lcslt-search, .lcslt-multi-callout, .lcslt-search-li input{font-size: 14px !important}
	.lcslt-selected, .li.lcslt-selected{background-color:#39f !important}
	.lcslt-multiple{height:auto}
	.btnaddCircle{font-size:24px;position:absolute;width:40px;height:40px;top:0;right:30px;
		-webkit-border-radius:25px;-moz-border-radius:25px;border-radius:25px;
		-webkit-justify-content:center;justify-content:center;-webkit-align-items:center;align-items:center;
		-webkit-box-shadow:0px 0px 10px 3px #ddd;box-shadow:0px 0px 10px 3px #ddd;
		background-color:yellowgreen;color:#fff;cursor:pointer}
	.capturelab, #socnt{display:none}
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
 							<a href="#" id="addNewLawyerDir" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" data-onOpen="console.log('test'); " class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.lawyerdirectory" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-order='[[0,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.lawyer" /></th>
									<th><fmt:message key="text.company" /></th>
									<th><fmt:message key="text.jobposition" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="d" items="${data}">
								<tr>
									<td>${d.first_name} ${d.last_name}</td>
									<td>${d.company_name}</td>
									<td>${d.jobposition}</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${d.lawyerid}" data-toggle="modal" data-target="#edit-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsToEdit(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" id="${d.lawyerid}" onclick="deleteLawyerDir(id);"><i class="md md-close"></i></a>
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

<script src="resources/assets/js/jquery.min.js"></script>

<!-- Modal -->
<div id="lawyerdir-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();forceclose('#lawyerdir-modal');" style="position:relative;color:#aaa">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key='text.lawyer' /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form id="formLawyerDir" enctype="multipart/form-data">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display:none;" id="errorOnAdd" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAdd').toggle();">&times;</a>
							<p id="putErrorOnAdd"></p>
						</div>
					</div>
					<div class="row container-tabsmodal">
						<ul id="addtabsmodal" class="tabsmodal">
							<li class="selectedtab trn2ms" onclick="togglemodtab(this,'#addstandarddata');"><fmt:message key="text.generaldata" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#addaddresdata');"><fmt:message key="text.address" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#addcontactdata');"><fmt:message key="text.contactways" /></li>
						</ul>
					</div>

					<div id="addstandarddata" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12 col-md-6">
								<div class="col-xs-12 form-group tac m-0" style="min-height:200px">
									<label class="control-label"><fmt:message key="text.updatepicture" />:</label>
									<div id="areaLawyerDirUpload">
										<span class="textContent" onclick="$('#uploadXLawyerDir').trigger('click');" style="cursor:pointer">
											<fmt:message key='label.dropzone' /></span>
										<div id='uploadXLawyerDir' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
									</div>
<input type="hidden" id="photo" value="${profile.photo_name}">
								</div>
							</div>
							<div class="col-xs-12 col-md-6">
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<label for="jobposition" class="supLlb"><fmt:message key='text.jobposition' /></label>
										<input type="text" class="form-control c39c" id="jobposition" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
						                <label class="supLlb"><fmt:message key='text.speciality' /></label>
						                <select name="speciality" id="speciality" class="c39c"
						                	data-placeholder="<fmt:message key='text.select' />" multiple></select>
					                </div>
				                </div>
			                </div>
						</div>

						<div class="row">
							<div class="col-xs-12">
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<label for="firstname" class="supLlb"><em>*</em> <fmt:message key='text.firstname' /></label>
										<input type="text" class="form-control c39c" id="firstname" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<label for="lastname" class="supLlb"><fmt:message key='text.lastname' /></label>
										<input type="text" class="form-control c39c" id="lastname" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<label for="firmname" class="supLlb"><fmt:message key='text.firmlegalofficename' /></label>
										<input type="text" class="form-control c39c" id="firmname" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<label for="notes" class="supLlb"><fmt:message key='text.notes' /></label>
										<textarea class="form-control c39c" id="notes"></textarea>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<c:choose>
											<c:when test="${coid>=2}">
												<input type="hidden" id="coid_a" value="0">
											</c:when>
											<c:otherwise>
												<label for="coid_a" class="supLlb"><em>*</em> <fmt:message key="text.firm" /></label>
												<div class="form-group fieldinfo inlineblock w100p">
													<input type="text" id="coid_a2" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
													<i class="material-icons iconlistfilter">arrow_drop_down</i>
													<ul class="ddListImg noimgOnList" id="coid_a"></ul>
												</div>
												<script>
													var ctx='${pageContext.request.contextPath}';
													$.ajax({
														type:'POST',
														url:ctx+"/getCompanies",
														async:false,
														success:function(data){
															var info=data[0];
															if(info.length>0){
																for(i=0;i<info.length;i++)
																	$('#coid_a').append('<li value="'+info[i].companyid+'" title="'+info[i].address1+'">'+info[i].company+'</li>');
																$('#coid_a').append('<li value=""></li>');
															}
														},error:function(e){
															console.log(i18n('err_unable_get_firm')+'. '+e);
														}
													});
													$('#coid_a1').on('keyup input propertychange paste change clear blur', function(){
//isSN(this.id.replace(/inputLAB/,''),$(this).val());
														console.log('Normal:' + $('#coid_a1 li.selected').text());
													});
													$('#coid_a').on('DOMNodeInserted click blur', 'li', function(){
//isSN(($(this).parent().attr('id')).replace(/commtypeid/,''),$(this).text());
														console.log('DomNode:' + $('#coid_a1 li.selected').text());
													});
												</script>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
		                </div>
					</div>

					<div id="addaddresdata" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="address1" class="supLlb"><em>*</em> <fmt:message key='text.address' /></label>
									<input type="text" class="form-control c39c" id="address1" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="address2" class="supLlb"><fmt:message key="text.colonydelegationtown" /></label>
									<input type="text" class="form-control c39c" id="address2" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="address3" class="supLlb"><fmt:message key='text.address' /> (<fmt:message key="text.additionalinfo" />)</label>
									<input type="text" class="form-control c39c" id="address3" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="col-xs-12 col-sm-6">
									<div class="form-group inlineflex w100p">
										<label for="zipcode" class="supLlb"><fmt:message key='text.zipcode' /></label>
										<input type="text" class="form-control c39c" id="zipcode" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<label for="country" class="supLlb"><fmt:message key="text.country" /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="country"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12">
								<div class="col-xs-12 col-sm-6">
									<div class="form-group fieldinfo inlineblock">
										<label for="state" class="supLlb"><fmt:message key="text.state" /></label>
										<div class="form-group fieldinfo inlineblock w100p">
											<input type="text" class="form-control listfiltersel" onfocus="getStatesByCountry('country','state','ul')" placeholder="<fmt:message key="text.select" />">
											<i class="material-icons iconlistfilter">arrow_drop_down</i>
											<ul class="ddListImg noimgOnList" id="state"></ul>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<label for="city" class="supLlb"><fmt:message key="text.city" /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" onfocus="getCitiesByState('state', 'city', 'ul');" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="city"></ul>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div id="addcontactdata" class="firmdatatabs">
						<div class="row capturelab" id="capturelab" style="background-color:#fff">
							<div class="col-xs-12 col-sm-6">
								<label for="commtypeid" class="supLlb"><fmt:message key="text.communicationtype" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" id="inputLAB"
										placeholder="<fmt:message key='text.select' />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="commtypeid"></ul>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="commlabelid" class="supLlb"><fmt:message key="text.label" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key='text.select' />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="commlabelid"></ul>
								</div>
							</div>
							<div class="col-xs-12">
								<fmt:message key='text.website' var='website' /> <fmt:message key='text.account' var='account'/>
								<div class="form-group inlineflex w100p">
									<label class="supLlb" for="contactinfo"><fmt:message key='text.information' /></label>
									<input type="text" class="form-control c39c" id="contactinfo" autocomplete="off"
										placeholder="<fmt:message key='text.number' />, ${fn:toLowerCase(website)}, ${fn:toLowerCase(account)}...">
								</div>
							</div>
							<div class="col-xs-12" id="adtinfo">
								<div class="form-group inlineflex w100p">
									<label class="supLlb" for="addtionalinfo"><fmt:message key='text.additionalinfo' /></label>
									<input type="text" class="form-control c39c" id="addtionalinfo" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12" id="socnt">
								<label for="socialnetwork" class="supLlb"><em>*</em> <fmt:message key="text.socialnetwork" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key='text.select' />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="socialnetwork"></ul>
								</div>
							</div>
							<div class="row tar p-20">
								<input type="hidden" id="recid">
								<button type="button" onclick="addlabTo('');" class="btn btn-primary waves-effect waves-light">
									<fmt:message key="button.ok" />
								</button>
								<button type="button" onclick="$('#lablist, #capturelab').toggle();$('#recid').val('');" class="btn btn-warning waves-effect waves-light m-l-10">
									<fmt:message key="button.cancel" />
								</button>
							</div> 
						</div>

						<div class="row" id="lablist">
							<div class="col-xs-12">
								<div class="inlineflex btnaddCircle" style="top:-15px;right:15px" onclick="modallab('');">+</div>
								<div class="card-box table-responsive" style="padding:0">
									<table id="tablecomm" data-order='[[0,"desc"]]' class="table table-striped table-bordered table-responsive">
										<thead>
											<tr>
												<th class="dnone"></th>
												<th><fmt:message key="text.communicationtype" /></th>
												<th><fmt:message key="text.commlabel" /></th>
												<th><fmt:message key="text.information" /></th>
												<th class="tac"><fmt:message key="text.edit" /></th>
												<th class="tac"><fmt:message key="text.delete" /></th>
											</tr>
										</thead>
										<tbody></tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<button type="button" onclick="return addLawyerDir(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addLawyerDirCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-modal" aria-hidden="true" style="display:none;overflow:auto">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="lawyer.update" /></h4>
				<input type="hidden" id="edlawyerid" value="">
			</div>
			<div class="modal-body">
				<form id="formEditLawyerDir">
					<div class="form-group">
					<div class="col-xs-12">
						<div style="display:none;" id="errorOnEdit" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEdit').toggle();">&times;</a>
							<p id="putErrorOnEdit"></p>
						</div>
					</div>
					<div class="row container-tabsmodal">
						<ul id="edittabsmodal" class="tabsmodal">
							<li class="selectedtab trn2ms" onclick="togglemodtab(this,'#editstandarddata');"><fmt:message key="text.generaldata" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#editaddresdata');"><fmt:message key="text.address" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#editcontactdata');"><fmt:message key="text.contactways" /></li>
						</ul>
					</div>

					<div id="editstandarddata" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12 col-md-6">
								<div class="col-xs-12 form-group tac m-0" style="min-height:200px">
									<label class="control-label"><fmt:message key="text.updatepicture" />:</label>
									<div id="areaEditLawyerDirUpload">
										<span class="textContent" onclick="$('#uploadXEditLawyerDir').trigger('click');" style="cursor:pointer">
											<fmt:message key='label.dropzone' /></span>
										<div id='uploadXEditLawyerDir' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
									</div>
<!--input type="hidden" id="editphoto" value="${profile.photo_name}"-->
								</div>
							</div>
							<div class="col-xs-12 col-md-6">
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<label for="editjobposition" class="supLlb"><fmt:message key='text.jobposition' /></label>
										<input type="text" class="form-control c39c" id="editjobposition" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<label for="editstatus" class="supLlb"><fmt:message key='text.status' /></label>
										<select class="form-control ddsencillo c39c" id="editstatus">
											<option value="1"><fmt:message key='status.active' /></option>
											<option value="0"><fmt:message key='status.desactive' /></option>
										</select>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
						                <label class="supLlb"><fmt:message key='text.speciality' /></label>
						                <select name="editspeciality" id="editspeciality" class="c39c"
						                	data-placeholder="<fmt:message key='text.select' />" multiple></select>
					                </div>
				                </div>
			                </div>
						</div>

						<div class="row">
							<div class="col-xs-12">
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<label for="editfirstname" class="supLlb"><em>*</em> <fmt:message key='text.firstname' /></label>
										<input type="text" class="form-control c39c" id="editfirstname" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<label for="editlastname" class="supLlb"><fmt:message key='text.lastname' /></label>
										<input type="text" class="form-control c39c" id="editlastname" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<label for="editfirmname" class="supLlb"><fmt:message key='text.firmlegalofficename' /></label>
										<input type="text" class="form-control c39c" id="editfirmname" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<label for="editnotes" class="supLlb"><fmt:message key='text.notes' /></label>
										<textarea class="form-control c39c" id="editnotes"></textarea>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<div class="form-group inlineflex w100p">
										<c:choose>
											<c:when test="${coid>=2}">
												<input type="hidden" id="coid_e" value="0">
											</c:when>
											<c:otherwise>
												<label for="coid_e" class="supLlb"><em>*</em> <fmt:message key="text.firm" /></label>
												<div class="form-group fieldinfo inlineblock w100p">
													<input type="text" id="coid_e2" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
													<i class="material-icons iconlistfilter">arrow_drop_down</i>
													<ul class="ddListImg noimgOnList" id="coid_e"></ul>
												</div>
												<script>
													var ctx='${pageContext.request.contextPath}';
													$.ajax({
														type:'POST',
														url:ctx+"/getCompanies",
														async:false,
														success:function(data){
															var info=data[0];
															if(info.length>0){
																for(i=0;i<info.length;i++)
																	$('#coid_e').append('<li value="'+info[i].companyid+'" title="'+info[i].address1+'">'+info[i].company+'</li>');
																$('#coid_e').append('<li value=""></li>');
															}
														},error:function(e){
															console.log(i18n('err_unable_get_firm')+'. '+e);
														}
													});
													$('#coid_e1').on('keyup input propertychange paste change clear blur', function(){
//isSN(this.id.replace(/inputLAB/,''),$(this).val());
														console.log('Normal:' + $('#coid_e1 li.selected').text());
													});
													$('#coid_e').on('DOMNodeInserted click blur', 'li', function(){
//isSN(($(this).parent().attr('id')).replace(/commtypeid/,''),$(this).text());
														console.log('DomNode:' + $('#coid_e1 li.selected').text());
													});
												</script>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
		                </div>
					</div>

					<div id="editaddresdata" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editaddress1" class="supLlb"><em>*</em> <fmt:message key='text.address' /></label>
									<input type="text" class="form-control c39c" id="editaddress1" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editaddress2" class="supLlb"><fmt:message key="text.colonydelegationtown" /></label>
									<input type="text" class="form-control c39c" id="editaddress2" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editaddress3" class="supLlb"><fmt:message key='text.address' /> (<fmt:message key="text.additionalinfo" />)</label>
									<input type="text" class="form-control c39c" id="editaddress3" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="col-xs-12 col-sm-6">
									<div class="form-group inlineflex w100p">
										<label for="editzipcode" class="supLlb"><fmt:message key='text.zipcode' /></label>
										<input type="text" class="form-control c39c" id="editzipcode" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<label for="editcountry" class="supLlb"><fmt:message key="text.country" /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="editcountry"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12">
								<div class="col-xs-12 col-sm-6">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="editstate" class="supLlb"><fmt:message key="text.state" /></label>
										<div class="form-group fieldinfo inlineblock w100p">
											<input type="text" class="form-control listfiltersel" onfocus="getStatesByCountry('editcountry','editstate','ul')" placeholder="<fmt:message key="text.select" />">
											<i class="material-icons iconlistfilter">arrow_drop_down</i>
											<ul class="ddListImg noimgOnList" id="editstate"></ul>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<label for="editcity" class="supLlb"><fmt:message key="text.city" /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" onfocus="getCitiesByState('editstate', 'editcity', 'ul');" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="editcity"></ul>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div id="editcontactdata" class="firmdatatabs">
						<div class="row capturelab" id="editcapturelab" style="background-color:#fff">
							<div class="col-xs-12 col-sm-6">
								<label for="editcommtypeid" class="supLlb"><fmt:message key="text.communicationtype" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" id="editinputLAB"
										placeholder="<fmt:message key='text.select' />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="editcommtypeid"></ul>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="editcommlabelid" class="supLlb"><fmt:message key="text.label" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key='text.select' />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="editcommlabelid"></ul>
								</div>
							</div>
							<div class="col-xs-12">
								<fmt:message key='text.website' var='website' /> <fmt:message key='text.account' var='account'/>
								<div class="form-group inlineflex w100p">
									<label class="supLlb" for="editcontactinfo"><fmt:message key='text.information' /></label>
									<input type="text" class="form-control c39c" id="editcontactinfo" autocomplete="off"
										placeholder="<fmt:message key='text.number' />, ${fn:toLowerCase(website)}, ${fn:toLowerCase(account)}...">
								</div>
							</div>
							<div class="col-xs-12" id="editadtinfo">
								<div class="form-group inlineflex w100p">
									<label class="supLlb" for="editaddtionalinfo"><fmt:message key='text.additionalinfo' /></label>
									<input type="text" class="form-control c39c" id="editaddtionalinfo" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12" id="editsocnt">
								<label for="editsocialnetwork" class="supLlb"><em>*</em> <fmt:message key="text.socialnetwork" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key='text.select' />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="editsocialnetwork"></ul>
								</div>
							</div>
							<div class="row tar p-20">
								<input type="hidden" id="editrecid">
								<button type="button" onclick="addlabTo('edit');" class="btn btn-primary waves-effect waves-light">
									<fmt:message key="button.ok" />
								</button>
								<button type="button" onclick="$('#editlablist, #editcapturelab').toggle();$('#editrecid').val('');" class="btn btn-warning waves-effect waves-light m-l-10">
									<fmt:message key="button.cancel" />
								</button>
							</div> 
						</div>

						<div class="row" id="editlablist">
							<div class="col-xs-12">
								<div class="inlineflex btnaddCircle" style="top:-15px;right:15px" onclick="modallab('');">+</div>
								<div class="card-box table-responsive" style="padding:0">
									<table id="edittablecomm" data-order='[[0,"desc"]]' class="table table-striped table-bordered table-responsive">
										<thead>
											<tr>
												<th class="dnone"></th>
												<th><fmt:message key="text.communicationtype" /></th>
												<th><fmt:message key="text.commlabel" /></th>
												<th><fmt:message key="text.information" /></th>
												<th class="tac"><fmt:message key="text.edit" /></th>
												<th class="tac"><fmt:message key="text.delete" /></th>
											</tr>
										</thead>
										<tbody></tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return updateData();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->

<!--==============================================================-->
<!-- End Right content here -->
<!--==============================================================-->
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
<script src="resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="resources/assets/plugins/dropzone/dropzoneImg.js"></script>
<script src="resources/assets/plugins/lc-select-main/js/lc_select.min.js"></script>

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/customDropZoneImg.js"></script>
<script src="resources/assets/js/globalfunctions.js"></script>
<script src="resources/assets/js/lawyerdirectory.js"></script>

<script type="text/javascript">
	TableManageButtons.init();
	var lang=getLanguageURL();
	var dateOp={weekStart:1,daysOfWeekHighlighted:"0",language:lang,autoclose:true,
		todayBtn:true,clearBtn:true,calendarWeeks:true,todayHighlight:true};
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
</script>