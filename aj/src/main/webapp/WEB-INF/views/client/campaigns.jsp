<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.aj.utility.UserDTO"%>
<!-- i18n...-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages" />
<html lang="es-MX">
<meta charset="UTF-8">
<meta name="language" content="Spanish">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

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
<link rel="stylesheet" type="text/css" href="resources/css/campaigns.css">

<script src="resources/assets/js/complementos.js"></script>
<!-- inicio de ckeditor -->

<!-- script src="resources/assets/plugins/ckeditor/ckeditor.js"></script-->

<!-- Fin de ckeditor -->        
<style>
	.dt-buttons.btn-group a{margin-right:10px;}
</style>
<!--==============================================================-->
<!--Start right Content here-->
<!--==============================================================-->
<div class="content-page">
	<div class="content"><!-- Start content -->
		<div class="container">
			<div class="row"><!-- Page-Title -->
				<div class="col-sm-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
 						<div class="btn-group pull-right m-t-15">
 							<a href="#" id="addcampaign" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" data-onOpen="console.log('test'); " class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="campaign.add" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title"><fmt:message key="text.campaigns" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class=""></div>
					<div id="baseDateControl">
						<form class="form-inline" action="${pageContext.request.contextPath}/campaigns.jet?language=${language}" method="POST">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="<fmt:message key='text.fromdate' />" id="fromDateToFix" autocomplete="off" />
								<input type="hidden" name="fromDate" id="fromDate" />
							</div>
							<div class="form-group">
								<input type="text" class="form-control" placeholder="<fmt:message key='text.todate' />" id="toDateToFix" autocomplete="off" />
								<input type="hidden" name="toDate" id="toDate" />
							</div><br>
							<div class="form-group">
								<span><fmt:message key="text.filterby" /></span>&nbsp;
								<select id="filterdateby" name="filterdateby">
									<option value="" selected disabled><fmt:message key="text.selectoption" />...</option>
									<option value="c"><fmt:message key="text.created" /></option>
									<option value="e"><fmt:message key="text.sent" /></option>
								</select>
							</div>
							<button type="submit" class="btn btn-default waves-effect waves-light"><fmt:message key="button.ok" /></button>
						</form>
					</div>
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-page-length="20" data-order='[[1, "desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.campaigns" /></th>
									<th><fmt:message key="text.created" /></th>
									<th><fmt:message key="text.sent" /></th>
									<th><fmt:message key="campaign.send" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:set var="i" value="0" />
								<c:forEach var="item" items="${campData}" step="4">
								<tr>
									<td>${campData[i]}</td>
									<td>${campData[i+2]}</td>
									<td>${campData[i+3]}</td>
									<td class="tac">
										<a href="#" class="table-action-btn" data-toggle="modal" data-target="#send-campaign-modal" onclick="loadDefSend('${campData[i+1]}','${campData[i]}');">
											<i class="md md-send"></i>
										</a>
									</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" data-toggle="modal" data-target="#edit-campaign-modal" onclick="editCampaignDetails('${campData[i+1]}','${campData[i]}');">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" data-toggle="modal" data-target="#delete-campaign-modal" onclick="loadDeleteCampaign('${campData[i+1]}','${campData[i]}');">
											<i class="md md-close"></i>
										</a>
									</td>
									</c:if>
								</tr>
								<c:set var="i" value="${i+4}" />
								</c:forEach>
								<c:remove var="i" />
							</tbody>
						</table>						
					</div>
				</div>
			</div>
			<div class="hidden-xs" style="height: 300px;"></div><!--Demo only-->
		</div> <!--container-->
	</div> <!--content-->
</div>
<footer class="footer text-right">
	<fmt:message key="text.copyright" />
</footer>

<!--Modal-->
<div id="addcampaign-modal" class="sub modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" title="<fmt:message key='button.close' />">
		<span>&times;</span>
	</button>
	<h4 class="custom-modal-title"><span class="sr-only" style="position:relative;"><fmt:message key="campaign.add" /></span></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form method="post">
				<div class="form-group ">
					<div class="col-xs-12">
						<div style="display:none;" id="addCampaignError" class="alert alert-danger fade in">
							<a href="#" class="close --fc000" aria-label="close" onclick="$('#addCampaignError').toggle();">&times;</a>
							<p id="putError"></p>
						</div>
					</div>
				</div>
				<div class="form-group">
					<em>* </em><label for="campaignname"><fmt:message key="campaign.name" /></label>
					<input type="text" class="form-control" id="campaignname" placeholder="<fmt:message key='text.name' />" required />
				</div>
				
				<div class="form-group">
					<em>* </em><label for="subject"><fmt:message key="text.subject" /></label>
					<input type="text" class="form-control" id="subject" placeholder="<fmt:message key='text.subject' />" required 
						onmouseover="$('#tipsubject').css('display','block');" onmouseout="$('#tipsubject').css('display','none');"
						onblur="$('#tipsubject').css('display','none');" />
					<div id="tipsubject" style="display:none;" class="chimptip" onclick="this.display='none';">
						<fmt:message key='campaign.subject.tootip' />
					</div>
				</div>
				<div class="form-group">
					<em>* </em><label for="from_name"><fmt:message key='text.sender' /></label>
					<input type="text" class="form-control" id="from_name" placeholder="<fmt:message key='text.name' />" required 
						onmouseover="$('#tipCampfrom_name').css('display','block');" onmouseout="$('#tipCampfrom_name').css('display','none');"
						onblur="$('#tipCampfrom_name').css('display','none');" />
					<div id="tipCampfrom_name" style="display:none;" class="chimptip" onclick="this.display='none';">
						<fmt:message key='text.nameinfosend' />
					</div>
				</div>
				<div class="form-group">
					<em>* </em><label for="reply_to"><fmt:message key='text.email' /></label>
					<input type="text" class="form-control" id="reply_to" placeholder="<fmt:message key='text.responseemail' />" required 
						onmouseover="$('#tipCampreply_to').css('display','block');" onmouseout="$('#tipCampreply_to').css('display','none');"
						onblur="$('#tipCampreply_to').css('display','none');" required
						pattern="^\w+[\w._-]*@\w+[\w._-]*\.+[\w._-]{2,}[^.,@\s]([,]\s?\w+[\w._-]*@\w+[\w._-]*\.+[\w._-]{2,}[^.,@\s]){0,19}$"/>
					<div id="tipCampreply_to" style="display:none;" class="chimptip" onclick="this.display='none';">
						<fmt:message key='text.responseemail' />
					</div>
				</div>
				<div class="form-group divWrap">
					<div>
						<label for="selEmailList"><fmt:message key='text.distributionlist' /></label>
						<select id="selEmailList"></select>
					</div>
					<div>
						<button type="button" class="btn btn-primary w-lg" onclick="fillSelect('selEditList','from=lists&query=name,id'); fillLists();" data-toggle="modal">
							<fmt:message key="button.newdistlist" />
						</button>
					</div>
				</div>
				<div class="form-group divWrap">
					<div>
						<label for="name"><fmt:message key="text.template" /></label>
						<select id="seltemplateList"></select>
					</div>
					<div>
						<button type="button" class="btn btn-primary w-lg" id="uploadTemplate" data-toggle="modal">
							<fmt:message key="text.newtemplate" />
						</button>
					</div>
				</div>
				<button type="button" onclick="createCampaign();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" onclick="Custombox.close();" id="addNewCampaignCancel" class="btn btn-danger waves-effect waves-light m-l-10">
					<fmt:message key="button.cancel" />
				</button>
			</form>
		</div>
	</div>
</div>

<div id="edit-campaign-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-campaign-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" title="<fmt:message key='button.close' />">&times;</button>
				<h4 class="modal-title"><fmt:message key='campaign.update' /></h4> 
			</div>
			<div class="modal-body">
				<div class="form-group row ">
					<div class="col-xs-12">
						<div style="display:none;" id="EditCampaignError" class="alert alert-danger fade in">
							<a href="#" class="close --fc000" aria-label="close" onclick="$('#EditCampaignError').toggle();">&times;</a>
							<p id="putEditCampaignError"></p>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label for="editCampaignName" class="control-label"><fmt:message key='campaign.name' /></label>
							<p id="editCampaignName" class="form-control unuse"></p>
							<input id="editCampaignId" type="hidden" class="unuse" readonly="readonly" onfocus="this.blur();" disabled />
						</div>
						
						<div class="form-group">
							<em>* </em><label for="editSubject"><fmt:message key='text.subject' /></label>
							<input type="text" class="form-control" id="editSubject" placeholder="Asunto del correo" required 
								onmouseover="$('#tipCampSubject').css('display','block');" onmouseout="$('#tipCampSubject').css('display','none');"
								onblur="$('#tipCampSubject').css('display','none');" />
							<div id="tipCampSubject" style="display:none;" class="chimptip" onclick="this.display='none';">
								<fmt:message key='campaign.subject.tootip' />
							</div>
						</div>
						<div class="form-group">
							<em>* </em><label for="editFrom_name"><fmt:message key='text.name' /></label>
							<input type="text" class="form-control" id="editFrom_name" placeholder="<fmt:message key='text.nameinfosend' />" required 
								onmouseover="$('#tipEdCampfrom_name').css('display','block');" onmouseout="$('#tipEdCampfrom_name').css('display','none');"
								onblur="$('#tipEdCampfrom_name').css('display','none');" />
							<div id="tipEdCampfrom_name" style="display:none;" class="chimptip" onclick="this.display='none';">
								<fmt:message key='text.nameinfosend' />
							</div>
						</div>
						<div class="form-group">
							<em>* </em><label for="editReply_to"><fmt:message key='text.responseemail' /></label>
							<input type="text" class="form-control" id="editReply_to" placeholder="<fmt:message key='field.emailaddress.msg' />" 
								onmouseover="$('#tipEdCampreply_to').css('display','block');" onmouseout="$('#tipEdCampreply_to').css('display','none');"
								onblur="$('#tipEdCampreply_to').css('display','none');" required pattern="^\w+[\w._-]*@\w+[\w._-]*\.+[\w._-]{2,}$"/>
							<div id="tipEdCampreply_to" style="display:none;" class="chimptip" onclick="this.display='none';">
								<fmt:message key='text.responseemail' />
							</div>
						</div>
						<div class="form-group">
							<label for="selEditEmailList"><fmt:message key='text.distributionlist' /></label>
							<select id="selEditEmailList"></select>
							<button type="button" class="btn btn-primary w-lg" onclick="fillSelect('selEditList','from=lists&amp;query=name,id'); fillLists();" data-toggle="modal">
								<fmt:message key='button.newdistlist' />
							</button>
						</div>
						<div class="form-group">
							<label for="name"><fmt:message key="text.template" /></label>
							<select id="selEdittemplateList"></select><br>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" onclick="updateEditCampaign();" class="btn btn-info waves-effect waves-light">
					<fmt:message key="button.savechanges" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="delete-campaign-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="custom-width-modalLabel" aria-hidden="true" style="display: none;">
	<div class="modal-dialog" style="width:35%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" title="<fmt:message key='button.close' />">&times;</button>
				<h4 class="modal-title"><fmt:message key='campaign.delete' /></h4> 
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label for="delCampaignName" class="control-label"><fmt:message key='campaign.name' /></label>
							<p id="delCampaignName" class="form-control"></p>
							<input id="delCampaignId" type="hidden" class="unuse" readonly="readonly" onfocus="this.blur();" disabled />
						</div>
						
						<div class="form-group">
							<label for="delSubject"><fmt:message key='campaign.subject' /></label>
							<p class="form-control" id="delSubject" placeholder="<fmt:message key='campaign.subject' />"></p>
						</div>
						<div class="form-group">
							<label for="delFrom_name"><fmt:message key='campaign.nameinfosend' /></label>
							<p class="form-control" id="delFrom_name"></p>
						</div>
						<div class="form-group">
							<label for="delReply_to"><fmt:message key='text.responseemail' /></label>
							<p class="form-control" id="delReply_to"></p>
						</div>
						<!-- div class="form-group">
							<label for="seldelEmailList">Lista de destinatarios</label>
							<select id="seldelEmailList"></select><br>
							<button type="button" class="btn btn-primary w-lg" id="createdelList" data-toggle="modal">
								Ver, crear o modificar listas
							</button>
						</div>
						<div class="form-group">
							<label for="name"><fmt:message key="text.template" /></label>
							<select id="seldeltemplateList"></select><br>
						</div> -->
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" onclick="deleteCampaign();" class="btn btn-primary waves-effect waves-light">
					<fmt:message key="button.delete" />
				</button>
			</div>
		</div><!--/.modal-content-->
	</div><!--/.modal-dialog-->
</div>

<div id="email-list-modal" class="modal demo" style="overflow:auto; z-index:50000;" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="sub modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" style="float: left;"><fmt:message key="button.add.subscriber" /></h5>
				<button type="button" class="close" onclick="$('#addcampaign-modal').css('display','block'); $('#email-list-modal').css('display','none');" 
					title="<fmt:message key='button.close' />"><span>&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<div class="form-group ">
						<div class="col-xs-12">
							<div style="display:none;" id="addMemberError" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" style="color:#000" aria-label="close" onclick="$('#addMemberError').toggle();">&times;</a>
								<p id="putErrorMember"></p>
							</div>
							<div style="display:none;" id="addMemberMessages" class="alert alert-success fade in">
								<a href="#" class="close" style="color:#000" style="color:#000" aria-label="close" onclick="$('#addMemberMessages').toggle();">&times;</a>
								<p id="putMessageMember"></p>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="selEditList"><fmt:message key="text.selectoption" /></label>
						<select id="selEditList"></select>
						<button type="button" class="btn btn-primary w-lg" id="createNewList" data-toggle="modal">
							<fmt:message key="text.createnewlist" />
						</button><br>
					</div>
					<div class="form-group" id="allmem"></div>

					<div class="form-group" id="editmem">
						<em>* </em><label for="memberEmail"><fmt:message key="text.email" /></label>
						<input type="text" class="form-control" id="memberEmail" required pattern="^[\w._-]+@[\w._-]+\.+[\w._-]{2,}$" 
							placeholder="<fmt:message key='field.emailaddress.msg' />" autocomplete="off" />
						<em>* </em><label for="memberName"><fmt:message key="text.name" /></label>
						<input type="text" class="form-control" id="memberName" maxlenght="255" required pattern="^\w{3,255}$"
							placeholder="<fmt:message key='text.name' />" autocomplete="off" />
						<label for="memberLastName"><fmt:message key="text.lastname" /></label>
						<input type="text" class="form-control" id="memberLastName"	maxlenght="255" pattern="^\w{3,255}$"
							placeholder="<fmt:message key='text.lastname' />" autocomplete="off" />
						<label for="memberPhone"><fmt:message key="text.phone" /></label>
						<input type="text" class="form-control" id="memberPhone" maxlenght="255" pattern="^\w{10,255}$"
							placeholder="<fmt:message key='text.phone' />" autocomplete="off" />
						<label for="memberAddress"><fmt:message key="text.address" /></label>
						<input type="text" class="form-control" id="memberAddress"	maxlenght="255" pattern="^\w{3,255}$"
							placeholder="<fmt:message key='text.address' />" autocomplete="off" />
						<label for="memberBirthday"><fmt:message key="text.birthday" /></label>
						<input type="text" class="form-control" id="memberBirthday" pattern="^([1][0-2]|[0]\d)/([3][0-1]|[0-2]\d))$"
							placeholder="<fmt:message key='text.birthday' /> (MM/dd)" autocomplete="off" />
						<input type="hidden" id="fixBirthday"/>
						<label for="memberStatus"><fmt:message key="text.status" /></label>
						<select id="memberStatus"></select><br>
						<label for="memberWebsite"><fmt:message key="text.website" /></label>
						<input type="text" class="form-control" id="memberWebsite" patthern="^(https?://)?([^/:]+(:(\d+))?)(/.*)?$"
							placeholder="<fmt:message key='text.website' />" autocomplete="off" />
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<div id="buttonsNewSubscriber">
					<button type="button" class="btn btn-info waves-effect waves-light" onclick="editmemberdata($('#selEditList').val(),'','','','','','','','');">
						<fmt:message key="button.add.subscriber" />
					</button>
					<button type="button" id="addNewMemberCancel" class="btn btn-default waves-effect waves-light m-l-10">
						<fmt:message key="button.back" />
					</button>
				</div>
				<div id="buttonsSaveSubscriber">
					<button type="button" class="btn btn-info waves-effect waves-light" onclick="suscribeMember();">
						<fmt:message key="button.save" />
					</button>
					<button type="button" id="editMemberCancel" class="btn btn-danger waves-effect waves-light m-l-10">
						<fmt:message key="button.cancel" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="create-list-modal" class="modal demo" style="overflow:auto; z-index:50001" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="sub modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header" style="padding:0px;">
				<h5 class="modal-title" style="float: left;"><fmt:message key="text.createnewlist" /></h5>
				<button type="button" class="close" onclick="$('#email-list-modal').css('display','block'); $('#create-list-modal').css('display','none');" title="<fmt:message key='button.close' />">
					<span>&times;</span>
				</button>
			</div>
			<div class="modal-body" style="padding:0px;">
				<form class="form-horizontal">
					<div class="form-group ">
						<div class="col-xs-12">
							<div style="display:none;" id="newListError" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" style="color:#000" aria-label="close" onclick="$('#newListError').toggle();">&times;</a>
								<p id="putErrorNewList"></p>
							</div>
							<div style="display:none;" id="newListMessages" class="alert alert-success fade in">
								<a href="#" class="close" style="color:#000" style="color:#000" aria-label="close" onclick="$('#newListMessages').toggle();">&times;</a>
								<p id="putMessagesNewList"></p>
							</div>
						</div>
					</div>
					<div class="form-group">
						<input type="radio" name="tabs" id="toggle-newListTab1" checked="checked" />
						<label class="labelTab" for="toggle-newListTab1"><fmt:message key="text.list" /></label>

						<input type="radio" name="tabs" id="toggle-newListTab2" />
						<label class="labelTab" for="toggle-newListTab2"><fmt:message key="text.company" /></label>

						<input type="radio" name="tabs" id="toggle-newListTab3" />
						<label class="labelTab" for="toggle-newListTab3"><fmt:message key="text.sender" /></label>
	
						<div id="newListTab1" class="tab">
							<em>* </em><label for="newlistName"><fmt:message key="text.name" /></label>
							<input type="text" class="form-control" id="newlistName" maxlenght="255" required pattern="^\w{3,255}$" 
								pattern="^\w{3,255}$" placeholder="<fmt:message key='text.listname' />" autocomplete="off" />
							<em>* </em><label for="newPermissionReminder"><fmt:message key='campaign.permissionreminder' />
								(<fmt:message key='text.footnote' />)</label>
							<input type="text" class="form-control" id="newPermissionReminder" required pattern="^\w{3,255}$" 
								placeholder="<fmt:message key='campaign.permissioninfo' />" title="<fmt:message key='campaign.permissioninfo' />" 
								onblur="$('#toggle-newListTab2').prop('checked',true);" autocomplete="off" />
						</div>
						<div id="newListTab2" class="tab">
							<em>* </em><label for="companyName"><fmt:message key="text.firmname" /></label>
							<input type="text" class="form-control" id="companyName" maxlenght="255" required pattern="^\w{3,255}$" 
								placeholder="<fmt:message key='campaign.infocompanysend' />" title="<fmt:message key='campaign.infocompanysend' />" 
								autocomplete="off" />
							<em>* </em><label for="companyAddress"><fmt:message key="text.address" /></label>
							<input type="text" class="form-control" id="companyAddress" maxlenght="255" required pattern="^\w{10,255}$" 
								placeholder="<fmt:message key='text.address' />" autocomplete="off" />
							<em>* </em><label for="companyCityName"><fmt:message key="text.city" /></label>
							<input type="text" class="form-control" id="companyCityName" maxlenght="255" required pattern="^\w{3,255}$" 
								placeholder="<fmt:message key='text.city' />" autocomplete="off" />
							<em>* </em><label for="companyZip"><fmt:message key="text.zipcode" /></label>
							<input type="text" class="form-control" id="companyZip"	maxlenght="255" required pattern="^\w{3,255}$" 
								placeholder="<fmt:message key='text.zipcode' />" autocomplete="off" />
							<em>* </em><label for="companyState"><fmt:message key="text.state" /></label>
							<input type="text" class="form-control" id="companyState" maxlenght="255" required pattern="^\w{3,255}$"
								placeholder="<fmt:message key='text.state' />" autocomplete="off" />
							<em>* </em><label for="companyCountry"><fmt:message key="text.country" /></label>
							<input type="text" class="form-control" id="companyCountry"	maxlenght="255" required pattern="^\w{3,255}$" 
								placeholder="<fmt:message key='text.country' />" onblur="$('#toggle-newListTab3').prop('checked',true);" autocomplete="off" />
						</div>
						<div id="newListTab3" class="tab">
							<em>* </em><label for="newListFromName"><fmt:message key="text.name" /></label>
							<input type="text" class="form-control" id="newListFromName" maxlenght="255" required pattern="^\w{3,255}$"
								placeholder="<fmt:message key='text.nameinfosend' />" autocomplete="off" />
							<em>* </em><label for="newListFromEmail"><fmt:message key="text.email" /></label>
							<input type="text" class="form-control" id="newListFromEmail" maxlenght="255" required pattern="^[\w._-]+@[\w._-]+\.+[\w._-]{2,}$"
								placeholder="<fmt:message key='field.emailaddress.msg' />" autocomplete="off" />
							<em>* </em><label for="newListSubject"><fmt:message key="text.subject" /></label>
							<input type="text" class="form-control" id="newListSubject" maxlenght="255" required pattern="^\w{3,255}$"
								placeholder="<fmt:message key='campaign.subjectinfo1' />" autocomplete="off" />
						</div>
					</div>
				</form>
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect waves-light" onclick="createList();">
					<fmt:message key="button.save" />
				</button>
				<button type="button" id="newListCancel" onclick="$('#email-list-modal').css('display','block'); $('#create-list-modal').css('display','none');" 
					class="btn btn-default waves-effect waves-light m-l-10"><fmt:message key="button.back" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="send-campaign-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="send-campaign-modal" aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" title="<fmt:message key='button.close' />">&times;</button>
				<h4 class="modal-title"><fmt:message key="campaign.send" /></h4> 
			</div>
			<div class="modal-body">
				<div class="form-group row ">
					<div class="col-xs-12">
						<div style="display:none;" id="SendCampaignError" class="alert alert-danger fade in">
							<a href="#" class="close --fc000" aria-label="close" onclick="$('#SendCampaignError').toggle();">&times;</a>
							<p id="putSendCampaignError"></p>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label for="sendCampaignName" class="control-label"><fmt:message key="text.name" /></label>
							<span id="sendCampaignName" class="form-control unuse"></span>
							<input id="sendCampaignId" type="hidden" />
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.back" />
				</button>
				<button type="button" onclick="sendTestModal();" class="btn btn-warning waves-effect waves-light">
					<fmt:message key="campaign.sendtest" />...
				</button>
				<button type="button" onclick="sendCampaign('send');" class="btn btn-info waves-effect waves-light">
					<fmt:message key="button.submit" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="test-campaign-modal" class="modal testModal" tabindex="-1" role="dialog" aria-labelledby="test-campaign-modal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" title="<fmt:message key='button.close' />" onclick="sendTestModal();">&times;</button>
				<h4 class="modal-title"><fmt:message key="campaign.sendtest" /></h4> 
			</div>
			<div class="modal-body">
				<div class="form-group row ">
					<div class="col-xs-12">
						<div style="display:none;" id="testCampaignError" class="alert alert-danger fade in">
							<a href="#" class="close --fc000" aria-label="close" onclick="$('#testCampaignError').toggle();">&times;</a>
							<p id="putTestCampaignError"></p>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label for="testCampaignName" class="control-label"><fmt:message key="text.name" /></label>
							<span id="testCampaignName" class="form-control unuse"></span>
							<input id="testCampaignId" type="hidden" />
						</div>
						<div class="form-group">
							<p><b>A)</b> Ingrese uno o más correos electrónicos separados por coma.</p>
							<input type="text" id="extraTestEmail" placeholder="email-1@test.com, email-2@test.com,..." style="width:55%;min-width:180px;" pattern="^(\w+[\w._-]*@\w+[\w._-]*\.+[\w._-]{2,})?(,\s\w+[\w._-]*@\w+[\w._-]*\.+[\w._-]{2,}){1,19}$" /><br><br>
							<p><b>B)</b> Además de los correos ingresados arriba, puede seleccionar otros de una de sus listas de direcciones.</p>
							<label for="selTestEmailList"><fmt:message key='text.distributionlist' /></label>
							<select id="selTestEmailList"></select><br><br>
							<div class="form-group" id="tosendtest"></div>
						</div>
						<div class="form-group">
							<input type="checkbox" id="cbTestRules" class="control-label" />
							<label for="cbTestRules">Puntos importantes para el envío de pruebas &emsp; &or; &emsp;</label>
							<div class="control-label" id="testRules" style="text-align:justify;">
								Estos límites fueron establecidos para evitar que personas abusen de este medio.<br>
								<ul>
									<li>Límites de prueba de correo.<table class="tableOut tTest"><thead>
									<li>Los envios de prueba son por campaña y por períodos de 24 horas.<br>Si envías un mismo correo de prueba a cuatro direcciones, dicha prueba cuenta como <u>cuatro correos de prueba</u> y deberás restar al número máximo permitido.</li>
										<tr>
											<th class="tac">Plan contratado Mail Chimp</th>
											<th class="tac"><strong>Máximo envio de correos de prueba cada 24 horas</strong></th>
											<th class="tac"><strong>Máximo número de direcciones por prueba</strong></th>
										</tr>
										</thead><tbody>
											<tr><td class="tac"><strong>Forever Free</strong></td><td>24</td><td>6</td></tr>
											<tr><td class="tac"><strong>Monthly</strong></td><td>200</td><td>20</td></tr>
											<tr><td class="tac"><strong>Pay As You Go</strong></td><td>200</td><td>20</td></tr>
										</tbody></table>
										Ejemplos con los usuarios del plan <strong>Free</strong> en períodos de 24 horas:
										<ul>
											<li>Se pueden enviar hasta 4 correos de prueba con 6 direcciones cada uno, esto da el total máximo permitido de 24 pruebas.</li>
											<li>Se pueden enviar hasta 12 correos de prueba con 2 direcciones cada uno, esto da el total máximo permitido de 24 pruebas.</li>
										</ul>
									</li>
									<li>Límites de plantillas.<table class="tableOut tTest"><thead>
									<li>Una misma plantilla también esta limitada a enviarse en períodos de 24 horas.<br>Si envías una misma plantilla de prueba en tres campañas distintas, dicha prueba cuenta como <u>tres plantillas de prueba</u> y deberás restar al número máximo permitido.</li>
										<tr><th>Plan contratado Mail Chimp</th><th><strong>Pruebas de plantilla cada 24 horas </strong></th><th><strong>Direcciones por prueba</strong></th></tr>
										</thead>
										<tbody>
										<tr><td><strong>Forever Free</strong></td><td>24</td><td>5</td></tr>
										<tr><td><strong>Monthly</strong></td><td>100</td><td>5</td></tr>
										<tr><td><strong>Pay as You Go</strong></td><td>100</td><td>5</td></tr>
										</tbody></table>					
									</li>
									<li>Otra forma de enviar pruebas es copiando la misma campaña.</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal" onclick="sendTestModal();">
					<fmt:message key="button.back" />
				</button>
				<button type="button" onclick="sendCampaign('test');" class="btn btn-info waves-effect waves-light">
					<fmt:message key="button.sendtest" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="upload-template-modal" class="modal fade" style="overflow:auto; z-index:50000;" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="sub modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" style="float: left;">Cargar Template</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="form-group row ">
					<div class="col-xs-12">
						<div style="display:none;" id="templateError" class="alert alert-danger fade in">
							<a href="#" class="close --fc000" aria-label="close" onclick="$('#templateError').toggle();">&times;</a>
							<p id="putTemplateError"></p>
						</div>
					</div>
				</div>
				<form class="form-horizontal">
					<div class="form-group">
						<div class="form-group">
							<em>* </em><label for="newTemplateName"><fmt:message key='text.name' /></label>
							<input type="text" class="form-control" id="newTemplateName" placeholder="<fmt:message key='text.templatename' />" required />
						</div>
						<div class="col-md-10">
							<label class="col-md-2 control-label"><fmt:message key='text.loadfile' /></label>
							<input type="file" id="templateFile" name="templateFile" /><br>
						</div>
						<div class="form-group">
							<em>* </em><label class="col-md-2 control-label">Código</label>

<!-- Area de pruebas (ini) -->


							<div class="col-md-10">
								<div id="templateCode" name="templateCode" class="fr-view templateCode"></div>
<!--
								<script>
									//CKEDITOR.replace('templateCode');
								</script>
-->
							</div>


<!-- Fin de área de pruebas -->



						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="createTemplate();">
					<fmt:message key="button.save" />
				</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>

<!--==============================================================-->
<!--End Right content here-->
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
<script src="resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="resources/assets/plugins/moment/moment.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
<script src="resources/assets/js/campaigns.js"></script>

<!--script src="http://code.jquery.com/jquery-1.11.1.min.js"></script RTE-->
<!--script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script RTE-->
<script>
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
	TableManageButtons.init();
	var dateOp={weekStart:1,
		daysOfWeekHighlighted:"0",
		language:lang,
		autoclose:true,
		todayBtn:true,
		clearBtn:true,
		calendarWeeks:true,
		todayHighlight:true};
	$('#fromDateToFix').datepicker(dateOp);
	$('#toDateToFix').datepicker(dateOp);
	$('#memberBirthday').datepicker(dateOp);
	$("#fromDateToFix").datepicker().on('changeDate',function(e){
		$('#fromDate').val(dp(e.date));
	});
	$("#fromDateToFix").datepicker().on('blur change',function(e){
		$('#fromDate').val(simpleChangePatt("yyyy-MM-dd",$("#fromDateToFix").val()));
	});
	$("#toDateToFix").datepicker().on('changeDate',function(e){
		$('#toDate').val(dp(e.date));
	});
	$("#toDateToFix").datepicker().on('blur change',function(e){
		$('#toDate').val(simpleChangePatt("yyyy-MM-dd",$("#toDateToFix").val()));
	});
	$("#fromDateToFix").datepicker().on('changeDate',function(e){
		$('#fromDate').val(dp(e.date));
	});
	$("#fromDateToFix").datepicker().on('blur change',function(e){
		$('#fromDate').val(simpleChangePatt("yyyy-MM-dd",$("#fromDateToFix").val()));
	});
	$("#memberBirthday").datepicker().on('changeDate',function(e){
		$('#fixBirthday').val(dp(e.date));
	});
	$("#memberBirthday").datepicker().on('blur change',function(e){
		$('#fixBirthday').val(simpleChangePatt("yyyy-MM-dd",$("#memberBirthday").val()));
	});

	$(document).ready(function (){
		$('#datatable').dataTable();
		$('#datatable-keytable').DataTable({keys:true});
		$('#datatable-responsive').DataTable();
		$('#datatable-colvid').DataTable({
			"dom":'C<"clear">lfrtip',
			"colVis":{"buttonText":"Change columns"}
		});
		$('#datatable-scroller').DataTable({
			deferRender:true,
			scrollY:380,
			scrollCollapse:true,
			scroller:true
		});
		var table=$('#datatable-fixed-header').DataTable({fixedHeader:true});
		var table=$('#datatable-fixed-col').DataTable({
			scrollY:"300px",
			scrollX:true,
			scrollCollapse:true,
			paging:false,
			fixedColumns:{
				leftColumns:1,
				rightColumns:1
			}
		});
		
		$(document).on('focus',".inDate",function(){
			$(this).datepicker
			var dateOp={weekStart:1,
				daysOfWeekHighlighted:"0",
				language: "${language}",
				autoclose:true,
				todayBtn: true,
				clearBtn: true,
				calendarWeeks: true,
				todayHighlight:true};
			$('#'+this.id).datepicker(dateOp);
		});
	});

/*	//The final solution code for all bugs ckeditor bootstrap3 modal
	
	//Solución 1 - https://dev.ckeditor.com/attachment/ticket/12525/solution-for-all-bugs-ckeditor-in-bootstrap-modal-test.html
	$.fn.modal.Constructor.prototype.enforceFocus=function(){
		var $modalElement=this.$element;
		$(document).on('focusin.modal',function(e){
			var $parent=$(e.target.parentNode);
			if($modalElement[0] !== e.target
				&& !$modalElement.has(e.target).length
				&& $(e.target).parentsUntil('*[role="dialog"]').length === 0){
				$modalElement.focus();}
		});
	};
*/
	
/*	//Solución 2
	$.fn.modal.Constructor.prototype.enforceFocus=function(){
		var modal_this=this;
		$(document).on('focusin.modal', function (e){
			if(modal_this.$element[0] !== e.target
				&& !modal_this.$element.has(e.target).length
				&& !$(e.target.parentNode).hasClass('cke_dialog_ui_input_select')
				&& !$(e.target.parentNode).hasClass('cke_dialog_ui_input_text')){
				modal_this.$element.focus()}
		})
	};*/
	
	//Solución 3
	
</script>