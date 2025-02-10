<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.aj.utility.UserDTO"%>
<%	UserDTO userDto=(UserDTO) request.getSession().getAttribute("UserDTO");
	int role=userDto.getRole();%>
<!-- i18n...-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages" />
<html lang="es-MX">
<meta charset="utf-8">
<meta name="language" content="Spanish">
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=EDGE;"><![endif]-->
<meta name="viewport" content="width=device-width, initial-scale=1">

<c:set scope="page" var="vis" value="1" />
<c:set scope="page" var="newadd" value="1" />
<c:set scope="page" var="edt" value="1" />
<c:set scope="page" var="del" value="1" />

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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web-resources/libs/sweet-alert2/sweetalert2.min.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<script src="resources/assets/js/complementos.js"></script>
<style>
	.dt-buttons{display:none;}
	.dt-buttons.btn-group a{margin-right:10px;}

	.inlineflex{display:-webkit-inline-flex;display:-webkit-inline-box;display:-moz-inline-box;display:-ms-inline-flexbox;display:inline-flex}
	.tac{text-align:center}
	.dnone{display:none}
	.trn2ms,.ti-layout-width-default,.icon-name{-webkit-transition:0.2s;-moz-transition:0.2s;-ms-transition:0.2s;-o-transition:0.2s;transition:0.2s}
    .c39c{color:#39c}
    .asLink{cursor:pointer}
    .asLink:hover,.az{color:#1B71D4}
	.sweet-overlay{z-index:10010 !important}
	.showSweetAlert{z-index:10011 !important}
	#proceedings{
        background-color:#FFFFFF;
	    border:1px solid #E3E3E3;
	    border-radius:4px;
	    padding:7px 12px;
	    height:38px;
	    max-width:100%
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
		height:47px;
		border-radius:7px;
		background-color:#FFF;
		cursor:pointer;
	}
	.ddsencillo::-ms-expand {display:none;}
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
	.w100p{width:100%}

	/*Draggable ini*/
	.dragDivOpt{display:block;padding:8px 10px;border:1px solid #aaa;overflow:auto;background-color:#EBEFF2}
	.dndBox{margin:2px 0;padding:10px;-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;
		border:1px solid #444;background-color:#fff;color:#000;cursor:move
	}
	.dndBox.over{border:1px dotted red}
    /*Draggable fin*/
    
    /*Icons*/
    #container,#edContainer{
    	position:absolute;
    	display:-moz-inline-grid;
		display:-ms-inline-grid;
    	display:inline-grid;
    	width:100%;
    	max-height:300px;
    	top:180px;
    	border:1px solid #777;
    	overflow:auto;
    	background-color:#fff;
    	z-index:10;
    }
/*    .closeicons{
    	position:fixed;
    	font-size:28px;
    	right:50px;
    	border:1px transparent;
    	color:#000;
    	background-color:transparent;
    }
    .closeicons:hover{color:#39c}*/
    .icon-section{width:100%;}
    .icon-container{
    	position;
    	width:240px;
    	padding:5px 0;
    	float:left;
    	text-align:left;
    	cursor:pointer
    }
    .ti-layout-width-default{
    	position:absolute;
    	font-size:17px;
    	margin-top:5px;
    }
    .icon-name{
    	font-size:14px;
    	margin-left:35px;
    }
    .ti-layout-width-default:hover,.icon-name:hover{
    	color:#39c;
    }
    /**/
</style>
<!--==============================================================-->
<!-- Start right Content here -->
<!--==============================================================-->
<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row"><!-- Page-Title -->
				<div class="col-sm-12">
					<c:if test="${newadd==1}">
 						<div class="btn-group pull-right m-t-15">
 							<a href="#" id="addNewMenu" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" data-onOpen="console.log('test'); " class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title"><fmt:message key="text.menusettings" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-order='[[0,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.menutitle" /></th>
									<th><fmt:message key="text.icon" /></th>
									<th><fmt:message key="text.parentmenu" /></th>
									<c:if test="${edt==1}"><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test="${del==1}"><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="mn" items="${menus}">
								<tr>
									<td>${mn.menu}</td>
									<td>${mn.icon}</td>
									<td>
									<c:forEach var="pnt" items="${menus}">
										<c:if test="${mn.menuparentid==pnt.menuid}">
											${pnt.menu}
										</c:if>
									</c:forEach>
									</td>
									<c:if test="${edt==1}">
									<td class="tac">
										<a href="#" id="${mn.menuid}" data-toggle="modal" data-target="#edit-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDataByMenuId(${mn.menuid});">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test="${del==1}">
									<td class="tac">
										<a href="#" class="table-action-btn" id="${mn.menuid}" onclick="deleteMenu(${mn.menuid});"><i class="md md-close"></i></a>
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
		<div class="hidden-xs" style="height:400px;"></div>
	</div>
</div>
<footer class="footer text-right">
	<fmt:message key="text.copyright" />
</footer>

<!-- Modal -->
<div id="menu-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form>
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display:none;" id="errorOnAdd" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAdd').toggle();">&times;</a>
						<p id="putErrorOnAdd"></p>
						</div>
					</div>

					<div class="row"><div class="col-xs-12 col-sm-12 col-md-12"><h3><fmt:message key='text.menusettings' /></h3></div></div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="menutitle" class="supLlb"><fmt:message key='text.menutitle' /></label>
								<input type="text" class="form-control c39c" id="menutitle" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="menuicon" class="supLlb"><fmt:message key='text.icon' /></label>
								<input type="text" class="form-control c39c" id="menuicon" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="link" class="supLlb"><fmt:message key='text.linkmenu' /></label>
								<input type="text" class="form-control c39c" id="link" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<fieldset>
								<!--<legend>Selecting elements</legend>-->
								<label style="font-weight:inherit;"><fmt:message key='text.tipomenu' /> </label> &emsp;   
								<input type="radio"  name="tipomenu"  id="lat" value="2"  checked="checked" />
								<label style="font-weight:inherit;" for="lat"><fmt:message key='text.tipomenulat' /></label>&emsp;
								<input type="radio"  name="tipomenu"  id="top" value="1" />
								<label style="font-weight:inherit;" for="top"><fmt:message key='text.tipomenusup' /></label>&emsp; 
								<input type="radio"  name="tipomenu"  id="top" value="3" />
								<label style="font-weight:inherit;" for="top"><fmt:message key='text.tipomenusupuser' /></label>
								</fieldset>    
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="parentmenu" class="supLlb"><fmt:message key='text.parentmenu' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="parentmenu"></select>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<h4><fmt:message key='text.selectorden' /></h4>
							<div class="form-group inlineflex w100p">
								<div id="orderOptions" class="dragDivOpt w100p"></div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<div id="container" style="width:90%">
				<div class="icon-section">
					<h3>Arrows &amp; Direction Icons</h3>
		
					<div class="icon-container">
						<span class="ti-arrow-up"></span><span class="icon-name">ti-arrow-up</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrow-right"></span><span class="icon-name">ti-arrow-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrow-left"></span><span class="icon-name">ti-arrow-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrow-down"></span><span class="icon-name">ti-arrow-down</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrows-vertical"></span><span class="icon-name">ti-arrows-vertical</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrows-horizontal"></span><span class="icon-name">ti-arrows-horizontal</span>
					</div>
					<div class="icon-container">
						<span class="ti-angle-up"></span><span class="icon-name">ti-angle-up</span>
					</div>
					<div class="icon-container">
						<span class="ti-angle-right"></span><span class="icon-name">ti-angle-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-angle-left"></span><span class="icon-name">ti-angle-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-angle-down"></span><span class="icon-name">ti-angle-down</span>
					</div>	
					<div class="icon-container">
						<span class="ti-angle-double-up"></span><span class="icon-name">ti-angle-double-up</span>
					</div>
					<div class="icon-container">
						<span class="ti-angle-double-right"></span><span class="icon-name">ti-angle-double-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-angle-double-left"></span><span class="icon-name">ti-angle-double-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-angle-double-down"></span><span class="icon-name">ti-angle-double-down</span>
					</div>					
					<div class="icon-container">
						<span class="ti-move"></span><span class="icon-name">ti-move</span>
					</div>
					<div class="icon-container">
						<span class="ti-fullscreen"></span><span class="icon-name">ti-fullscreen</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrow-top-right"></span><span class="icon-name">ti-arrow-top-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrow-top-left"></span><span class="icon-name">ti-arrow-top-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrow-circle-up"></span><span class="icon-name">ti-arrow-circle-up</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrow-circle-right"></span><span class="icon-name">ti-arrow-circle-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrow-circle-left"></span><span class="icon-name">ti-arrow-circle-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrow-circle-down"></span><span class="icon-name">ti-arrow-circle-down</span>
					</div>
					<div class="icon-container">
						<span class="ti-arrows-corner"></span><span class="icon-name">ti-arrows-corner</span>
					</div>
					<div class="icon-container">
						<span class="ti-split-v"></span><span class="icon-name">ti-split-v</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-split-v-alt"></span><span class="icon-name">ti-split-v-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-split-h"></span><span class="icon-name">ti-split-h</span>
					</div>
					<div class="icon-container">
						<span class="ti-hand-point-up"></span><span class="icon-name">ti-hand-point-up</span>
					</div>
					<div class="icon-container">
						<span class="ti-hand-point-right"></span><span class="icon-name">ti-hand-point-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-hand-point-left"></span><span class="icon-name">ti-hand-point-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-hand-point-down"></span><span class="icon-name">ti-hand-point-down</span>
					</div>
					<div class="icon-container">
						<span class="ti-back-right"></span><span class="icon-name">ti-back-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-back-left"></span><span class="icon-name">ti-back-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-exchange-vertical"></span><span class="icon-name">ti-exchange-vertical</span>
					</div>
		
				</div> <!-- Arrows Icons -->
				
				<div class="icon-section">
				
					<h3>Web App Icons</h3>
		
					<div class="icon-container">
						<span class="ti-wand"></span><span class="icon-name">ti-wand</span>
					</div>
					<div class="icon-container">
						<span class="ti-save"></span><span class="icon-name">ti-save</span>
					</div>
					<div class="icon-container">
						<span class="ti-save-alt"></span><span class="icon-name">ti-save-alt</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-direction"></span><span class="icon-name">ti-direction</span>
					</div>
					<div class="icon-container">
						<span class="ti-direction-alt"></span><span class="icon-name">ti-direction-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-user"></span><span class="icon-name">ti-user</span>
					</div>
					<div class="icon-container">
						<span class="ti-link"></span><span class="icon-name">ti-link</span>
					</div>
					<div class="icon-container">
						<span class="ti-unlink"></span><span class="icon-name">ti-unlink</span>
					</div>
					<div class="icon-container">
						<span class="ti-trash"></span><span class="icon-name">ti-trash</span>
					</div>
					<div class="icon-container">
						<span class="ti-target"></span><span class="icon-name">ti-target</span>
					</div>
					<div class="icon-container">
						<span class="ti-tag"></span><span class="icon-name">ti-tag</span>
					</div>
					<div class="icon-container">
						<span class="ti-desktop"></span><span class="icon-name">ti-desktop</span>
					</div>
					<div class="icon-container">
						<span class="ti-tablet"></span><span class="icon-name">ti-tablet</span>
					</div>
					<div class="icon-container">
						<span class="ti-mobile"></span><span class="icon-name">ti-mobile</span>
					</div>
					<div class="icon-container">
						<span class="ti-email"></span><span class="icon-name">ti-email</span>
					</div>	
					<div class="icon-container">
						<span class="ti-star"></span><span class="icon-name">ti-star</span>
					</div>
					<div class="icon-container">
						<span class="ti-spray"></span><span class="icon-name">ti-spray</span>
					</div>
					<div class="icon-container">
						<span class="ti-signal"></span><span class="icon-name">ti-signal</span>
					</div>
					<div class="icon-container">
						<span class="ti-shopping-cart"></span><span class="icon-name">ti-shopping-cart</span>
					</div>
					<div class="icon-container">
						<span class="ti-shopping-cart-full"></span><span class="icon-name">ti-shopping-cart-full</span>
					</div>
					<div class="icon-container">
						<span class="ti-settings"></span><span class="icon-name">ti-settings</span>
					</div>
					<div class="icon-container">
						<span class="ti-search"></span><span class="icon-name">ti-search</span>
					</div>
					<div class="icon-container">
						<span class="ti-zoom-in"></span><span class="icon-name">ti-zoom-in</span>
					</div>
					<div class="icon-container">
						<span class="ti-zoom-out"></span><span class="icon-name">ti-zoom-out</span>
					</div>
					<div class="icon-container">
						<span class="ti-cut"></span><span class="icon-name">ti-cut</span>
					</div>
					<div class="icon-container">
						<span class="ti-ruler"></span><span class="icon-name">ti-ruler</span>
					</div>
					<div class="icon-container">
						<span class="ti-ruler-alt-2"></span><span class="icon-name">ti-ruler-alt-2</span>
					</div>			
					<div class="icon-container">
						<span class="ti-ruler-pencil"></span><span class="icon-name">ti-ruler-pencil</span>
					</div>
					<div class="icon-container">
						<span class="ti-ruler-alt"></span><span class="icon-name">ti-ruler-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-bookmark"></span><span class="icon-name">ti-bookmark</span>
					</div>
					<div class="icon-container">
						<span class="ti-bookmark-alt"></span><span class="icon-name">ti-bookmark-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-reload"></span><span class="icon-name">ti-reload</span>
					</div>
					<div class="icon-container">
						<span class="ti-plus"></span><span class="icon-name">ti-plus</span>
					</div>
					<div class="icon-container">
						<span class="ti-minus"></span><span class="icon-name">ti-minus</span>
					</div>
					<div class="icon-container">
						<span class="ti-close"></span><span class="icon-name">ti-close</span>
					</div>			
					<div class="icon-container">
						<span class="ti-pin"></span><span class="icon-name">ti-pin</span>
					</div>
					<div class="icon-container">
						<span class="ti-pencil"></span><span class="icon-name">ti-pencil</span>
					</div>
							
				  	<div class="icon-container">
						<span class="ti-pencil-alt"></span><span class="icon-name">ti-pencil-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-paint-roller"></span><span class="icon-name">ti-paint-roller</span>
					</div>
					<div class="icon-container">
						<span class="ti-paint-bucket"></span><span class="icon-name">ti-paint-bucket</span>
					</div>
					<div class="icon-container">
						<span class="ti-na"></span><span class="icon-name">ti-na</span>
					</div>
					<div class="icon-container">
						<span class="ti-medall"></span><span class="icon-name">ti-medall</span>
					</div>
					<div class="icon-container">
						<span class="ti-medall-alt"></span><span class="icon-name">ti-medall-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-marker"></span><span class="icon-name">ti-marker</span>
					</div>
					<div class="icon-container">
						<span class="ti-marker-alt"></span><span class="icon-name">ti-marker-alt</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-lock"></span><span class="icon-name">ti-lock</span>
					</div>
					<div class="icon-container">
						<span class="ti-unlock"></span><span class="icon-name">ti-unlock</span>
					</div>
					<div class="icon-container">
						<span class="ti-location-arrow"></span><span class="icon-name">ti-location-arrow</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout"></span><span class="icon-name">ti-layout</span>
					</div>
					<div class="icon-container">
						<span class="ti-layers"></span><span class="icon-name">ti-layers</span>
					</div>
					<div class="icon-container">
						<span class="ti-layers-alt"></span><span class="icon-name">ti-layers-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-key"></span><span class="icon-name">ti-key</span>
					</div>
					<div class="icon-container">
						<span class="ti-image"></span><span class="icon-name">ti-image</span>
					</div>
					<div class="icon-container">
						<span class="ti-heart"></span><span class="icon-name">ti-heart</span>
					</div>
					<div class="icon-container">
						<span class="ti-heart-broken"></span><span class="icon-name">ti-heart-broken</span>
					</div>
					<div class="icon-container">
						<span class="ti-hand-stop"></span><span class="icon-name">ti-hand-stop</span>
					</div>
					<div class="icon-container">
						<span class="ti-hand-open"></span><span class="icon-name">ti-hand-open</span>
					</div>
					<div class="icon-container">
						<span class="ti-hand-drag"></span><span class="icon-name">ti-hand-drag</span>
					</div>
					<div class="icon-container">
						<span class="ti-flag"></span><span class="icon-name">ti-flag</span>
					</div>
					<div class="icon-container">
						<span class="ti-flag-alt"></span><span class="icon-name">ti-flag-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-flag-alt-2"></span><span class="icon-name">ti-flag-alt-2</span>
					</div>
					<div class="icon-container">
						<span class="ti-eye"></span><span class="icon-name">ti-eye</span>
					</div>
					<div class="icon-container">
						<span class="ti-import"></span><span class="icon-name">ti-import</span>
					</div>			
					<div class="icon-container">
						<span class="ti-export"></span><span class="icon-name">ti-export</span>
					</div>
					<div class="icon-container">
						<span class="ti-cup"></span><span class="icon-name">ti-cup</span>
					</div>
					<div class="icon-container">
						<span class="ti-crown"></span><span class="icon-name">ti-crown</span>
					</div>
					<div class="icon-container">
						<span class="ti-comments"></span><span class="icon-name">ti-comments</span>
					</div>
					<div class="icon-container">
						<span class="ti-comment"></span><span class="icon-name">ti-comment</span>
					</div>
					<div class="icon-container">
						<span class="ti-comment-alt"></span><span class="icon-name">ti-comment-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-thought"></span><span class="icon-name">ti-thought</span>
					</div>			
					<div class="icon-container">
						<span class="ti-clip"></span><span class="icon-name">ti-clip</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-check"></span><span class="icon-name">ti-check</span>
					</div>
					<div class="icon-container">
						<span class="ti-check-box"></span><span class="icon-name">ti-check-box</span>
					</div>
					<div class="icon-container">
						<span class="ti-camera"></span><span class="icon-name">ti-camera</span>
					</div>
					<div class="icon-container">
						<span class="ti-announcement"></span><span class="icon-name">ti-announcement</span>
					</div>
					<div class="icon-container">
						<span class="ti-brush"></span><span class="icon-name">ti-brush</span>
					</div>
					<div class="icon-container">
						<span class="ti-brush-alt"></span><span class="icon-name">ti-brush-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-palette"></span><span class="icon-name">ti-palette</span>
					</div>			
					<div class="icon-container">
						<span class="ti-briefcase"></span><span class="icon-name">ti-briefcase</span>
					</div>
					<div class="icon-container">
						<span class="ti-bolt"></span><span class="icon-name">ti-bolt</span>
					</div>
					<div class="icon-container">
						<span class="ti-bolt-alt"></span><span class="icon-name">ti-bolt-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-blackboard"></span><span class="icon-name">ti-blackboard</span>
					</div>
					<div class="icon-container">
						<span class="ti-bag"></span><span class="icon-name">ti-bag</span>
					</div>
					<div class="icon-container">
						<span class="ti-world"></span><span class="icon-name">ti-world</span>
					</div>
					<div class="icon-container">
						<span class="ti-wheelchair"></span><span class="icon-name">ti-wheelchair</span>
					</div>
					<div class="icon-container">
						<span class="ti-car"></span><span class="icon-name">ti-car</span>
					</div>
					<div class="icon-container">
						<span class="ti-truck"></span><span class="icon-name">ti-truck</span>
					</div>
					<div class="icon-container">
						<span class="ti-timer"></span><span class="icon-name">ti-timer</span>
					</div>
					<div class="icon-container">
						<span class="ti-ticket"></span><span class="icon-name">ti-ticket</span>
					</div>
					<div class="icon-container">
						<span class="ti-thumb-up"></span><span class="icon-name">ti-thumb-up</span>
					</div>
					<div class="icon-container">
						<span class="ti-thumb-down"></span><span class="icon-name">ti-thumb-down</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-stats-up"></span><span class="icon-name">ti-stats-up</span>
					</div>
					<div class="icon-container">
						<span class="ti-stats-down"></span><span class="icon-name">ti-stats-down</span>
					</div>
					<div class="icon-container">
						<span class="ti-shine"></span><span class="icon-name">ti-shine</span>
					</div>
					<div class="icon-container">
						<span class="ti-shift-right"></span><span class="icon-name">ti-shift-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-shift-left"></span><span class="icon-name">ti-shift-left</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-shift-right-alt"></span><span class="icon-name">ti-shift-right-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-shift-left-alt"></span><span class="icon-name">ti-shift-left-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-shield"></span><span class="icon-name">ti-shield</span>
					</div>
					<div class="icon-container">
						<span class="ti-notepad"></span><span class="icon-name">ti-notepad</span>
					</div>
					<div class="icon-container">
						<span class="ti-server"></span><span class="icon-name">ti-server</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-pulse"></span><span class="icon-name">ti-pulse</span>
					</div>
					<div class="icon-container">
						<span class="ti-printer"></span><span class="icon-name">ti-printer</span>
					</div>
					<div class="icon-container">
						<span class="ti-power-off"></span><span class="icon-name">ti-power-off</span>
					</div>
					<div class="icon-container">
						<span class="ti-plug"></span><span class="icon-name">ti-plug</span>
					</div>
					<div class="icon-container">
						<span class="ti-pie-chart"></span><span class="icon-name">ti-pie-chart</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-panel"></span><span class="icon-name">ti-panel</span>
					</div>
					<div class="icon-container">
						<span class="ti-package"></span><span class="icon-name">ti-package</span>
					</div>
					<div class="icon-container">
						<span class="ti-music"></span><span class="icon-name">ti-music</span>
					</div>
					<div class="icon-container">
						<span class="ti-music-alt"></span><span class="icon-name">ti-music-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-mouse"></span><span class="icon-name">ti-mouse</span>
					</div>
					<div class="icon-container">
						<span class="ti-mouse-alt"></span><span class="icon-name">ti-mouse-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-money"></span><span class="icon-name">ti-money</span>
					</div>
					<div class="icon-container">
						<span class="ti-microphone"></span><span class="icon-name">ti-microphone</span>
					</div>
					<div class="icon-container">
						<span class="ti-menu"></span><span class="icon-name">ti-menu</span>
					</div>
					<div class="icon-container">
						<span class="ti-menu-alt"></span><span class="icon-name">ti-menu-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-map"></span><span class="icon-name">ti-map</span>
					</div>
					<div class="icon-container">
						<span class="ti-map-alt"></span><span class="icon-name">ti-map-alt</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-location-pin"></span><span class="icon-name">ti-location-pin</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-light-bulb"></span><span class="icon-name">ti-light-bulb</span>
					</div>
					<div class="icon-container">
						<span class="ti-info"></span><span class="icon-name">ti-info</span>
					</div>
					<div class="icon-container">
						<span class="ti-infinite"></span><span class="icon-name">ti-infinite</span>
					</div>
					<div class="icon-container">
						<span class="ti-id-badge"></span><span class="icon-name">ti-id-badge</span>
					</div>
					<div class="icon-container">
						<span class="ti-hummer"></span><span class="icon-name">ti-hummer</span>
					</div>
					<div class="icon-container">
						<span class="ti-home"></span><span class="icon-name">ti-home</span>
					</div>
					<div class="icon-container">
						<span class="ti-help"></span><span class="icon-name">ti-help</span>
					</div>
					<div class="icon-container">
						<span class="ti-headphone"></span><span class="icon-name">ti-headphone</span>
					</div>
					<div class="icon-container">
						<span class="ti-harddrives"></span><span class="icon-name">ti-harddrives</span>
					</div>
					<div class="icon-container">
						<span class="ti-harddrive"></span><span class="icon-name">ti-harddrive</span>
					</div>
					<div class="icon-container">
						<span class="ti-gift"></span><span class="icon-name">ti-gift</span>
					</div>
					<div class="icon-container">
						<span class="ti-game"></span><span class="icon-name">ti-game</span>
					</div>
					<div class="icon-container">
						<span class="ti-filter"></span><span class="icon-name">ti-filter</span>
					</div>
					<div class="icon-container">
						<span class="ti-files"></span><span class="icon-name">ti-files</span>
					</div>
					<div class="icon-container">
						<span class="ti-file"></span><span class="icon-name">ti-file</span>
					</div>
					<div class="icon-container">
						<span class="ti-zip"></span><span class="icon-name">ti-zip</span>
					</div>
					<div class="icon-container">
						<span class="ti-folder"></span><span class="icon-name">ti-folder</span>
					</div>			
					<div class="icon-container">
						<span class="ti-envelope"></span><span class="icon-name">ti-envelope</span>
					</div>
		
		
					<div class="icon-container">
						<span class="ti-dashboard"></span><span class="icon-name">ti-dashboard</span>
					</div>
					<div class="icon-container">
						<span class="ti-cloud"></span><span class="icon-name">ti-cloud</span>
					</div>
					<div class="icon-container">
						<span class="ti-cloud-up"></span><span class="icon-name">ti-cloud-up</span>
					</div>
					<div class="icon-container">
						<span class="ti-cloud-down"></span><span class="icon-name">ti-cloud-down</span>
					</div>
					<div class="icon-container">
						<span class="ti-clipboard"></span><span class="icon-name">ti-clipboard</span>
					</div>
					<div class="icon-container">
						<span class="ti-calendar"></span><span class="icon-name">ti-calendar</span>
					</div>
					<div class="icon-container">
						<span class="ti-book"></span><span class="icon-name">ti-book</span>
					</div>
					<div class="icon-container">
						<span class="ti-bell"></span><span class="icon-name">ti-bell</span>
					</div>
					<div class="icon-container">
						<span class="ti-basketball"></span><span class="icon-name">ti-basketball</span>
					</div>
					<div class="icon-container">
						<span class="ti-bar-chart"></span><span class="icon-name">ti-bar-chart</span>
					</div>
					<div class="icon-container">
						<span class="ti-bar-chart-alt"></span><span class="icon-name">ti-bar-chart-alt</span>
					</div>
		
		
					<div class="icon-container">
						<span class="ti-archive"></span><span class="icon-name">ti-archive</span>
					</div>
					<div class="icon-container">
						<span class="ti-anchor"></span><span class="icon-name">ti-anchor</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-alert"></span><span class="icon-name">ti-alert</span>
					</div>
					<div class="icon-container">
						<span class="ti-alarm-clock"></span><span class="icon-name">ti-alarm-clock</span>
					</div>
					<div class="icon-container">
						<span class="ti-agenda"></span><span class="icon-name">ti-agenda</span>
					</div>
					<div class="icon-container">
						<span class="ti-write"></span><span class="icon-name">ti-write</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-wallet"></span><span class="icon-name">ti-wallet</span>
					</div>
					<div class="icon-container">
						<span class="ti-video-clapper"></span><span class="icon-name">ti-video-clapper</span>
					</div>
					<div class="icon-container">
						<span class="ti-video-camera"></span><span class="icon-name">ti-video-camera</span>
					</div>
					<div class="icon-container">
						<span class="ti-vector"></span><span class="icon-name">ti-vector</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-support"></span><span class="icon-name">ti-support</span>
					</div>
					<div class="icon-container">
						<span class="ti-stamp"></span><span class="icon-name">ti-stamp</span>
					</div>
					<div class="icon-container">
						<span class="ti-slice"></span><span class="icon-name">ti-slice</span>
					</div>
					<div class="icon-container">
						<span class="ti-shortcode"></span><span class="icon-name">ti-shortcode</span>
					</div>
					<div class="icon-container">
						<span class="ti-receipt"></span><span class="icon-name">ti-receipt</span>
					</div>
					<div class="icon-container">
						<span class="ti-pin2"></span><span class="icon-name">ti-pin2</span>
					</div>
					<div class="icon-container">
						<span class="ti-pin-alt"></span><span class="icon-name">ti-pin-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-pencil-alt2"></span><span class="icon-name">ti-pencil-alt2</span>
					</div>
					<div class="icon-container">
						<span class="ti-eraser"></span><span class="icon-name">ti-eraser</span>
					</div>			
					<div class="icon-container">
						<span class="ti-more"></span><span class="icon-name">ti-more</span>
					</div>
					<div class="icon-container">
						<span class="ti-more-alt"></span><span class="icon-name">ti-more-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-microphone-alt"></span><span class="icon-name">ti-microphone-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-magnet"></span><span class="icon-name">ti-magnet</span>
					</div>
					<div class="icon-container">
						<span class="ti-line-double"></span><span class="icon-name">ti-line-double</span>
					</div>
					<div class="icon-container">
						<span class="ti-line-dotted"></span><span class="icon-name">ti-line-dotted</span>
					</div>
					<div class="icon-container">
						<span class="ti-line-dashed"></span><span class="icon-name">ti-line-dashed</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-ink-pen"></span><span class="icon-name">ti-ink-pen</span>
					</div>
					<div class="icon-container">
						<span class="ti-info-alt"></span><span class="icon-name">ti-info-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-help-alt"></span><span class="icon-name">ti-help-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-headphone-alt"></span><span class="icon-name">ti-headphone-alt</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-gallery"></span><span class="icon-name">ti-gallery</span>
					</div>
					<div class="icon-container">
						<span class="ti-face-smile"></span><span class="icon-name">ti-face-smile</span>
					</div>
					<div class="icon-container">
						<span class="ti-face-sad"></span><span class="icon-name">ti-face-sad</span>
					</div>
					<div class="icon-container">
						<span class="ti-credit-card"></span><span class="icon-name">ti-credit-card</span>
					</div>
					<div class="icon-container">
						<span class="ti-comments-smiley"></span><span class="icon-name">ti-comments-smiley</span>
					</div>
					<div class="icon-container">
						<span class="ti-time"></span><span class="icon-name">ti-time</span>
					</div>
					<div class="icon-container">
						<span class="ti-share"></span><span class="icon-name">ti-share</span>
					</div>
					<div class="icon-container">
						<span class="ti-share-alt"></span><span class="icon-name">ti-share-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-rocket"></span><span class="icon-name">ti-rocket</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-new-window"></span><span class="icon-name">ti-new-window</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-rss"></span><span class="icon-name">ti-rss</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-rss-alt"></span><span class="icon-name">ti-rss-alt</span>
					</div>
					
				</div><!-- Web App Icons -->
		
		
				<div class="icon-section">
				
					<h3>Control Icons</h3>
					
					<div class="icon-container">
						<span class="ti-control-stop"></span><span class="icon-name">ti-control-stop</span>
					</div>
					<div class="icon-container">
						<span class="ti-control-shuffle"></span><span class="icon-name">ti-control-shuffle</span>
					</div>
					<div class="icon-container">
						<span class="ti-control-play"></span><span class="icon-name">ti-control-play</span>
					</div>
					<div class="icon-container">
						<span class="ti-control-pause"></span><span class="icon-name">ti-control-pause</span>
					</div>
					<div class="icon-container">
						<span class="ti-control-forward"></span><span class="icon-name">ti-control-forward</span>
					</div>
					<div class="icon-container">
						<span class="ti-control-backward"></span><span class="icon-name">ti-control-backward</span>
					</div>	
					<div class="icon-container">
						<span class="ti-volume"></span><span class="icon-name">ti-volume</span>
					</div>
					<div class="icon-container">
						<span class="ti-control-skip-forward"></span><span class="icon-name">ti-control-skip-forward</span>
					</div>
					<div class="icon-container">
						<span class="ti-control-skip-backward"></span><span class="icon-name">ti-control-skip-backward</span>
					</div>
					<div class="icon-container">
						<span class="ti-control-record"></span><span class="icon-name">ti-control-record</span>
					</div>
					<div class="icon-container">
						<span class="ti-control-eject"></span><span class="icon-name">ti-control-eject</span>
					</div>			
				</div> <!-- Control Icons -->
		
		
				<div class="icon-section">
				
					<h3>Text Editor</h3>
		
					<div class="icon-container">
						<span class="ti-paragraph"></span><span class="icon-name">ti-paragraph</span>
					</div>
					<div class="icon-container">
						<span class="ti-uppercase"></span><span class="icon-name">ti-uppercase</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-underline"></span><span class="icon-name">ti-underline</span>
					</div>
					<div class="icon-container">
						<span class="ti-text"></span><span class="icon-name">ti-text</span>
					</div>
					<div class="icon-container">
						<span class="ti-Italic"></span><span class="icon-name">ti-Italic</span>
					</div>
					<div class="icon-container">
						<span class="ti-smallcap"></span><span class="icon-name">ti-smallcap</span>
					</div>
					<div class="icon-container">
						<span class="ti-list"></span><span class="icon-name">ti-list</span>
					</div>
					<div class="icon-container">
						<span class="ti-list-ol"></span><span class="icon-name">ti-list-ol</span>
					</div>
					<div class="icon-container">
						<span class="ti-align-right"></span><span class="icon-name">ti-align-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-align-left"></span><span class="icon-name">ti-align-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-align-justify"></span><span class="icon-name">ti-align-justify</span>
					</div>
					<div class="icon-container">
						<span class="ti-align-center"></span><span class="icon-name">ti-align-center</span>
					</div>
					<div class="icon-container">
						<span class="ti-quote-right"></span><span class="icon-name">ti-quote-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-quote-left"></span><span class="icon-name">ti-quote-left</span>
					</div>
		
				</div> <!-- Text Editor -->
		
		
		
				<div class="icon-section">
				
					<h3>Layout Icons</h3>
					
					<div class="icon-container">
						<span class="ti-layout-width-full"></span><span class="icon-name">ti-layout-width-full</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-width-default"></span><span class="icon-name">ti-layout-width-default</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-width-default-alt"></span><span class="icon-name">ti-layout-width-default-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-tab"></span><span class="icon-name">ti-layout-tab</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-tab-window"></span><span class="icon-name">ti-layout-tab-window</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-tab-v"></span><span class="icon-name">ti-layout-tab-v</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-tab-min"></span><span class="icon-name">ti-layout-tab-min</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-slider"></span><span class="icon-name">ti-layout-slider</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-slider-alt"></span><span class="icon-name">ti-layout-slider-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-sidebar-right"></span><span class="icon-name">ti-layout-sidebar-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-sidebar-none"></span><span class="icon-name">ti-layout-sidebar-none</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-sidebar-left"></span><span class="icon-name">ti-layout-sidebar-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-placeholder"></span><span class="icon-name">ti-layout-placeholder</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-menu"></span><span class="icon-name">ti-layout-menu</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-menu-v"></span><span class="icon-name">ti-layout-menu-v</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-menu-separated"></span><span class="icon-name">ti-layout-menu-separated</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-menu-full"></span><span class="icon-name">ti-layout-menu-full</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-media-right"></span><span class="icon-name">ti-layout-media-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-media-right-alt"></span><span class="icon-name">ti-layout-media-right-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-media-overlay"></span><span class="icon-name">ti-layout-media-overlay</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-media-overlay-alt"></span><span class="icon-name">ti-layout-media-overlay-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-media-overlay-alt-2"></span><span class="icon-name">ti-layout-media-overlay-alt-2</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-media-left"></span><span class="icon-name">ti-layout-media-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-media-left-alt"></span><span class="icon-name">ti-layout-media-left-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-media-center"></span><span class="icon-name">ti-layout-media-center</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-media-center-alt"></span><span class="icon-name">ti-layout-media-center-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-list-thumb"></span><span class="icon-name">ti-layout-list-thumb</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-list-thumb-alt"></span><span class="icon-name">ti-layout-list-thumb-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-list-post"></span><span class="icon-name">ti-layout-list-post</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-list-large-image"></span><span class="icon-name">ti-layout-list-large-image</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-line-solid"></span><span class="icon-name">ti-layout-line-solid</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-grid4"></span><span class="icon-name">ti-layout-grid4</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-grid3"></span><span class="icon-name">ti-layout-grid3</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-grid2"></span><span class="icon-name">ti-layout-grid2</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-grid2-thumb"></span><span class="icon-name">ti-layout-grid2-thumb</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-cta-right"></span><span class="icon-name">ti-layout-cta-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-cta-left"></span><span class="icon-name">ti-layout-cta-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-cta-center"></span><span class="icon-name">ti-layout-cta-center</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-cta-btn-right"></span><span class="icon-name">ti-layout-cta-btn-right</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-cta-btn-left"></span><span class="icon-name">ti-layout-cta-btn-left</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-column4"></span><span class="icon-name">ti-layout-column4</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-column3"></span><span class="icon-name">ti-layout-column3</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-column2"></span><span class="icon-name">ti-layout-column2</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-accordion-separated"></span><span class="icon-name">ti-layout-accordion-separated</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-accordion-merged"></span><span class="icon-name">ti-layout-accordion-merged</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-accordion-list"></span><span class="icon-name">ti-layout-accordion-list</span>
					</div>
					<div class="icon-container">
						<span class="ti-widgetized"></span><span class="icon-name">ti-widgetized</span>
					</div>
					<div class="icon-container">
						<span class="ti-widget"></span><span class="icon-name">ti-widget</span>
					</div>
					<div class="icon-container">
						<span class="ti-widget-alt"></span><span class="icon-name">ti-widget-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-view-list"></span><span class="icon-name">ti-view-list</span>
					</div>
					<div class="icon-container">
						<span class="ti-view-list-alt"></span><span class="icon-name">ti-view-list-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-view-grid"></span><span class="icon-name">ti-view-grid</span>
					</div>
					<div class="icon-container">
						<span class="ti-upload"></span><span class="icon-name">ti-upload</span>
					</div>
					<div class="icon-container">
						<span class="ti-download"></span><span class="icon-name">ti-download</span>
					</div>	
					<div class="icon-container">
						<span class="ti-loop"></span><span class="icon-name">ti-loop</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-sidebar-2"></span><span class="icon-name">ti-layout-sidebar-2</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-grid4-alt"></span><span class="icon-name">ti-layout-grid4-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-grid3-alt"></span><span class="icon-name">ti-layout-grid3-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-grid2-alt"></span><span class="icon-name">ti-layout-grid2-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-column4-alt"></span><span class="icon-name">ti-layout-column4-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-column3-alt"></span><span class="icon-name">ti-layout-column3-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-layout-column2-alt"></span><span class="icon-name">ti-layout-column2-alt</span>
					</div>		
		
		
				</div> <!-- Layout Icons -->
		
		
				<div class="icon-section">
				
					<h3>Brand Icons</h3>
		
					<div class="icon-container">
						<span class="ti-flickr"></span><span class="icon-name">ti-flickr</span>
					</div>
					<div class="icon-container">
						<span class="ti-flickr-alt"></span><span class="icon-name">ti-flickr-alt</span>
					</div>			
					<div class="icon-container">
						<span class="ti-instagram"></span><span class="icon-name">ti-instagram</span>
					</div>
					<div class="icon-container">
						<span class="ti-google"></span><span class="icon-name">ti-google</span>
					</div>
					<div class="icon-container">
						<span class="ti-github"></span><span class="icon-name">ti-github</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-facebook"></span><span class="icon-name">ti-facebook</span>
					</div>
					<div class="icon-container">
						<span class="ti-dropbox"></span><span class="icon-name">ti-dropbox</span>
					</div>
					<div class="icon-container">
						<span class="ti-dropbox-alt"></span><span class="icon-name">ti-dropbox-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-dribbble"></span><span class="icon-name">ti-dribbble</span>
					</div>
					<div class="icon-container">
						<span class="ti-apple"></span><span class="icon-name">ti-apple</span>
					</div>
					<div class="icon-container">
						<span class="ti-android"></span><span class="icon-name">ti-android</span>
					</div>
					<div class="icon-container">
						<span class="ti-yahoo"></span><span class="icon-name">ti-yahoo</span>
					</div>
					<div class="icon-container">
						<span class="ti-trello"></span><span class="icon-name">ti-trello</span>
					</div>
					<div class="icon-container">
						<span class="ti-stack-overflow"></span><span class="icon-name">ti-stack-overflow</span>
					</div>
					<div class="icon-container">
						<span class="ti-soundcloud"></span><span class="icon-name">ti-soundcloud</span>
					</div>
					<div class="icon-container">
						<span class="ti-sharethis"></span><span class="icon-name">ti-sharethis</span>
					</div>
					<div class="icon-container">
						<span class="ti-sharethis-alt"></span><span class="icon-name">ti-sharethis-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-reddit"></span><span class="icon-name">ti-reddit</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-microsoft"></span><span class="icon-name">ti-microsoft</span>
					</div>
					<div class="icon-container">
						<span class="ti-microsoft-alt"></span><span class="icon-name">ti-microsoft-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-linux"></span><span class="icon-name">ti-linux</span>
					</div>
					<div class="icon-container">
						<span class="ti-jsfiddle"></span><span class="icon-name">ti-jsfiddle</span>
					</div>
					<div class="icon-container">
						<span class="ti-joomla"></span><span class="icon-name">ti-joomla</span>
					</div>
					<div class="icon-container">
						<span class="ti-html5"></span><span class="icon-name">ti-html5</span>
					</div>
					<div class="icon-container">
						<span class="ti-css3"></span><span class="icon-name">ti-css3</span>
					</div>	
					<div class="icon-container">
						<span class="ti-drupal"></span><span class="icon-name">ti-drupal</span>
					</div>
					<div class="icon-container">
						<span class="ti-wordpress"></span><span class="icon-name">ti-wordpress</span>
					</div>		
					<div class="icon-container">
						<span class="ti-tumblr"></span><span class="icon-name">ti-tumblr</span>
					</div>
					<div class="icon-container">
						<span class="ti-tumblr-alt"></span><span class="icon-name">ti-tumblr-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-skype"></span><span class="icon-name">ti-skype</span>
					</div>
					<div class="icon-container">
						<span class="ti-youtube"></span><span class="icon-name">ti-youtube</span>
					</div>
					<div class="icon-container">
						<span class="ti-vimeo"></span><span class="icon-name">ti-vimeo</span>
					</div>
					<div class="icon-container">
						<span class="ti-vimeo-alt"></span><span class="icon-name">ti-vimeo-alt</span>
					</div>			
					<div class="icon-container">
						<span class="ti-twitter"></span><span class="icon-name">ti-twitter</span>
					</div>
					<div class="icon-container">
						<span class="ti-twitter-alt"></span><span class="icon-name">ti-twitter-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-linkedin"></span><span class="icon-name">ti-linkedin</span>
					</div>
					<div class="icon-container">
						<span class="ti-pinterest"></span><span class="icon-name">ti-pinterest</span>
					</div>
		
					<div class="icon-container">
						<span class="ti-pinterest-alt"></span><span class="icon-name">ti-pinterest-alt</span>
					</div>
					<div class="icon-container">
						<span class="ti-themify-logo"></span><span class="icon-name">ti-themify-logo</span>
					</div>
					<div class="icon-container">
						<span class="ti-themify-favicon"></span><span class="icon-name">ti-themify-favicon</span>
					</div>
					<div class="icon-container">
						<span class="ti-themify-favicon-alt"></span><span class="icon-name">ti-themify-favicon-alt</span>
					</div>
		
				</div> <!-- brand Icons -->
			</div>
			<button type="button" onclick="return addMenu();" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addMenuCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title"><fmt:message key="menu.uptdate" /></h4>
			</div>
			<div class="modal-body">
				<form>
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEdit" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEdit').toggle();">&times;</a>
								<p id="putErrorOnEdit"></p>
								<input type="hidden" id="edMenuId">
							</div>
						</div>
					</div>
	
					<div class="row"><div class="col-xs-12 col-sm-12 col-md-12"><h3><fmt:message key='text.menusettings' /></h3></div></div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="edMenutitle" class="supLlb"><fmt:message key='text.menutitle' /></label>
								<input type="text" class="form-control c39c" id="edMenutitle" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="edMenuicon" class="supLlb"><fmt:message key='text.icon' /></label>
								<input type="text" class="form-control c39c" id="edMenuicon" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="edlink" class="supLlb"><fmt:message key='text.linkmenu' /></label>
								<input type="text" class="form-control c39c" id="edlink" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<fieldset>
								<!--<legend>Selecting elements</legend>-->
								<label style="font-weight:inherit;"><fmt:message key='text.tipomenu' /> </label> &emsp;   
								<input type="radio"  name="edtipomenu"  id="edlat" value="2"  checked="checked" />
								<label style="font-weight:inherit;" for="edlat"><fmt:message key='text.tipomenulat' /></label>&emsp;
								<input type="radio"  name="edtipomenu"  id="edtop" value="1" />
								<label style="font-weight:inherit;" for="edtop"><fmt:message key='text.tipomenusup' /></label>&emsp; 
								<input type="radio"  name="tipomenu"  id="muser" value="3" />
								<label style="font-weight:inherit;" for="muser"><fmt:message key='text.tipomenusupuser' /></label>
								</fieldset>    
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="edParentmenu" class="supLlb"><fmt:message key='text.parentmenu' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="edParentmenu"></select>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<h4><fmt:message key='text.selectorden' /></h4>
							<div class="form-group inlineflex w100p">
								<div id="edOrderOptions" class="dragDivOpt w100p"></div>
							</div>
						</div>
					</div>
				</form>

				<div id="edContainer">
					<div class="icon-section">
						<h3>Arrows &amp; Direction Icons</h3>
			
						<div class="icon-container">
							<span class="ti-arrow-up"></span><span class="icon-name">ti-arrow-up</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrow-right"></span><span class="icon-name">ti-arrow-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrow-left"></span><span class="icon-name">ti-arrow-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrow-down"></span><span class="icon-name">ti-arrow-down</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrows-vertical"></span><span class="icon-name">ti-arrows-vertical</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrows-horizontal"></span><span class="icon-name">ti-arrows-horizontal</span>
						</div>
						<div class="icon-container">
							<span class="ti-angle-up"></span><span class="icon-name">ti-angle-up</span>
						</div>
						<div class="icon-container">
							<span class="ti-angle-right"></span><span class="icon-name">ti-angle-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-angle-left"></span><span class="icon-name">ti-angle-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-angle-down"></span><span class="icon-name">ti-angle-down</span>
						</div>	
						<div class="icon-container">
							<span class="ti-angle-double-up"></span><span class="icon-name">ti-angle-double-up</span>
						</div>
						<div class="icon-container">
							<span class="ti-angle-double-right"></span><span class="icon-name">ti-angle-double-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-angle-double-left"></span><span class="icon-name">ti-angle-double-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-angle-double-down"></span><span class="icon-name">ti-angle-double-down</span>
						</div>					
						<div class="icon-container">
							<span class="ti-move"></span><span class="icon-name">ti-move</span>
						</div>
						<div class="icon-container">
							<span class="ti-fullscreen"></span><span class="icon-name">ti-fullscreen</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrow-top-right"></span><span class="icon-name">ti-arrow-top-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrow-top-left"></span><span class="icon-name">ti-arrow-top-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrow-circle-up"></span><span class="icon-name">ti-arrow-circle-up</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrow-circle-right"></span><span class="icon-name">ti-arrow-circle-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrow-circle-left"></span><span class="icon-name">ti-arrow-circle-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrow-circle-down"></span><span class="icon-name">ti-arrow-circle-down</span>
						</div>
						<div class="icon-container">
							<span class="ti-arrows-corner"></span><span class="icon-name">ti-arrows-corner</span>
						</div>
						<div class="icon-container">
							<span class="ti-split-v"></span><span class="icon-name">ti-split-v</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-split-v-alt"></span><span class="icon-name">ti-split-v-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-split-h"></span><span class="icon-name">ti-split-h</span>
						</div>
						<div class="icon-container">
							<span class="ti-hand-point-up"></span><span class="icon-name">ti-hand-point-up</span>
						</div>
						<div class="icon-container">
							<span class="ti-hand-point-right"></span><span class="icon-name">ti-hand-point-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-hand-point-left"></span><span class="icon-name">ti-hand-point-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-hand-point-down"></span><span class="icon-name">ti-hand-point-down</span>
						</div>
						<div class="icon-container">
							<span class="ti-back-right"></span><span class="icon-name">ti-back-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-back-left"></span><span class="icon-name">ti-back-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-exchange-vertical"></span><span class="icon-name">ti-exchange-vertical</span>
						</div>
			
					</div> <!-- Arrows Icons -->
					
					<div class="icon-section">
					
						<h3>Web App Icons</h3>
			
						<div class="icon-container">
							<span class="ti-wand"></span><span class="icon-name">ti-wand</span>
						</div>
						<div class="icon-container">
							<span class="ti-save"></span><span class="icon-name">ti-save</span>
						</div>
						<div class="icon-container">
							<span class="ti-save-alt"></span><span class="icon-name">ti-save-alt</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-direction"></span><span class="icon-name">ti-direction</span>
						</div>
						<div class="icon-container">
							<span class="ti-direction-alt"></span><span class="icon-name">ti-direction-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-user"></span><span class="icon-name">ti-user</span>
						</div>
						<div class="icon-container">
							<span class="ti-link"></span><span class="icon-name">ti-link</span>
						</div>
						<div class="icon-container">
							<span class="ti-unlink"></span><span class="icon-name">ti-unlink</span>
						</div>
						<div class="icon-container">
							<span class="ti-trash"></span><span class="icon-name">ti-trash</span>
						</div>
						<div class="icon-container">
							<span class="ti-target"></span><span class="icon-name">ti-target</span>
						</div>
						<div class="icon-container">
							<span class="ti-tag"></span><span class="icon-name">ti-tag</span>
						</div>
						<div class="icon-container">
							<span class="ti-desktop"></span><span class="icon-name">ti-desktop</span>
						</div>
						<div class="icon-container">
							<span class="ti-tablet"></span><span class="icon-name">ti-tablet</span>
						</div>
						<div class="icon-container">
							<span class="ti-mobile"></span><span class="icon-name">ti-mobile</span>
						</div>
						<div class="icon-container">
							<span class="ti-email"></span><span class="icon-name">ti-email</span>
						</div>	
						<div class="icon-container">
							<span class="ti-star"></span><span class="icon-name">ti-star</span>
						</div>
						<div class="icon-container">
							<span class="ti-spray"></span><span class="icon-name">ti-spray</span>
						</div>
						<div class="icon-container">
							<span class="ti-signal"></span><span class="icon-name">ti-signal</span>
						</div>
						<div class="icon-container">
							<span class="ti-shopping-cart"></span><span class="icon-name">ti-shopping-cart</span>
						</div>
						<div class="icon-container">
							<span class="ti-shopping-cart-full"></span><span class="icon-name">ti-shopping-cart-full</span>
						</div>
						<div class="icon-container">
							<span class="ti-settings"></span><span class="icon-name">ti-settings</span>
						</div>
						<div class="icon-container">
							<span class="ti-search"></span><span class="icon-name">ti-search</span>
						</div>
						<div class="icon-container">
							<span class="ti-zoom-in"></span><span class="icon-name">ti-zoom-in</span>
						</div>
						<div class="icon-container">
							<span class="ti-zoom-out"></span><span class="icon-name">ti-zoom-out</span>
						</div>
						<div class="icon-container">
							<span class="ti-cut"></span><span class="icon-name">ti-cut</span>
						</div>
						<div class="icon-container">
							<span class="ti-ruler"></span><span class="icon-name">ti-ruler</span>
						</div>
						<div class="icon-container">
							<span class="ti-ruler-alt-2"></span><span class="icon-name">ti-ruler-alt-2</span>
						</div>			
						<div class="icon-container">
							<span class="ti-ruler-pencil"></span><span class="icon-name">ti-ruler-pencil</span>
						</div>
						<div class="icon-container">
							<span class="ti-ruler-alt"></span><span class="icon-name">ti-ruler-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-bookmark"></span><span class="icon-name">ti-bookmark</span>
						</div>
						<div class="icon-container">
							<span class="ti-bookmark-alt"></span><span class="icon-name">ti-bookmark-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-reload"></span><span class="icon-name">ti-reload</span>
						</div>
						<div class="icon-container">
							<span class="ti-plus"></span><span class="icon-name">ti-plus</span>
						</div>
						<div class="icon-container">
							<span class="ti-minus"></span><span class="icon-name">ti-minus</span>
						</div>
						<div class="icon-container">
							<span class="ti-close"></span><span class="icon-name">ti-close</span>
						</div>			
						<div class="icon-container">
							<span class="ti-pin"></span><span class="icon-name">ti-pin</span>
						</div>
						<div class="icon-container">
							<span class="ti-pencil"></span><span class="icon-name">ti-pencil</span>
						</div>
								
					  	<div class="icon-container">
							<span class="ti-pencil-alt"></span><span class="icon-name">ti-pencil-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-paint-roller"></span><span class="icon-name">ti-paint-roller</span>
						</div>
						<div class="icon-container">
							<span class="ti-paint-bucket"></span><span class="icon-name">ti-paint-bucket</span>
						</div>
						<div class="icon-container">
							<span class="ti-na"></span><span class="icon-name">ti-na</span>
						</div>
						<div class="icon-container">
							<span class="ti-medall"></span><span class="icon-name">ti-medall</span>
						</div>
						<div class="icon-container">
							<span class="ti-medall-alt"></span><span class="icon-name">ti-medall-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-marker"></span><span class="icon-name">ti-marker</span>
						</div>
						<div class="icon-container">
							<span class="ti-marker-alt"></span><span class="icon-name">ti-marker-alt</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-lock"></span><span class="icon-name">ti-lock</span>
						</div>
						<div class="icon-container">
							<span class="ti-unlock"></span><span class="icon-name">ti-unlock</span>
						</div>
						<div class="icon-container">
							<span class="ti-location-arrow"></span><span class="icon-name">ti-location-arrow</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout"></span><span class="icon-name">ti-layout</span>
						</div>
						<div class="icon-container">
							<span class="ti-layers"></span><span class="icon-name">ti-layers</span>
						</div>
						<div class="icon-container">
							<span class="ti-layers-alt"></span><span class="icon-name">ti-layers-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-key"></span><span class="icon-name">ti-key</span>
						</div>
						<div class="icon-container">
							<span class="ti-image"></span><span class="icon-name">ti-image</span>
						</div>
						<div class="icon-container">
							<span class="ti-heart"></span><span class="icon-name">ti-heart</span>
						</div>
						<div class="icon-container">
							<span class="ti-heart-broken"></span><span class="icon-name">ti-heart-broken</span>
						</div>
						<div class="icon-container">
							<span class="ti-hand-stop"></span><span class="icon-name">ti-hand-stop</span>
						</div>
						<div class="icon-container">
							<span class="ti-hand-open"></span><span class="icon-name">ti-hand-open</span>
						</div>
						<div class="icon-container">
							<span class="ti-hand-drag"></span><span class="icon-name">ti-hand-drag</span>
						</div>
						<div class="icon-container">
							<span class="ti-flag"></span><span class="icon-name">ti-flag</span>
						</div>
						<div class="icon-container">
							<span class="ti-flag-alt"></span><span class="icon-name">ti-flag-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-flag-alt-2"></span><span class="icon-name">ti-flag-alt-2</span>
						</div>
						<div class="icon-container">
							<span class="ti-eye"></span><span class="icon-name">ti-eye</span>
						</div>
						<div class="icon-container">
							<span class="ti-import"></span><span class="icon-name">ti-import</span>
						</div>			
						<div class="icon-container">
							<span class="ti-export"></span><span class="icon-name">ti-export</span>
						</div>
						<div class="icon-container">
							<span class="ti-cup"></span><span class="icon-name">ti-cup</span>
						</div>
						<div class="icon-container">
							<span class="ti-crown"></span><span class="icon-name">ti-crown</span>
						</div>
						<div class="icon-container">
							<span class="ti-comments"></span><span class="icon-name">ti-comments</span>
						</div>
						<div class="icon-container">
							<span class="ti-comment"></span><span class="icon-name">ti-comment</span>
						</div>
						<div class="icon-container">
							<span class="ti-comment-alt"></span><span class="icon-name">ti-comment-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-thought"></span><span class="icon-name">ti-thought</span>
						</div>			
						<div class="icon-container">
							<span class="ti-clip"></span><span class="icon-name">ti-clip</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-check"></span><span class="icon-name">ti-check</span>
						</div>
						<div class="icon-container">
							<span class="ti-check-box"></span><span class="icon-name">ti-check-box</span>
						</div>
						<div class="icon-container">
							<span class="ti-camera"></span><span class="icon-name">ti-camera</span>
						</div>
						<div class="icon-container">
							<span class="ti-announcement"></span><span class="icon-name">ti-announcement</span>
						</div>
						<div class="icon-container">
							<span class="ti-brush"></span><span class="icon-name">ti-brush</span>
						</div>
						<div class="icon-container">
							<span class="ti-brush-alt"></span><span class="icon-name">ti-brush-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-palette"></span><span class="icon-name">ti-palette</span>
						</div>			
						<div class="icon-container">
							<span class="ti-briefcase"></span><span class="icon-name">ti-briefcase</span>
						</div>
						<div class="icon-container">
							<span class="ti-bolt"></span><span class="icon-name">ti-bolt</span>
						</div>
						<div class="icon-container">
							<span class="ti-bolt-alt"></span><span class="icon-name">ti-bolt-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-blackboard"></span><span class="icon-name">ti-blackboard</span>
						</div>
						<div class="icon-container">
							<span class="ti-bag"></span><span class="icon-name">ti-bag</span>
						</div>
						<div class="icon-container">
							<span class="ti-world"></span><span class="icon-name">ti-world</span>
						</div>
						<div class="icon-container">
							<span class="ti-wheelchair"></span><span class="icon-name">ti-wheelchair</span>
						</div>
						<div class="icon-container">
							<span class="ti-car"></span><span class="icon-name">ti-car</span>
						</div>
						<div class="icon-container">
							<span class="ti-truck"></span><span class="icon-name">ti-truck</span>
						</div>
						<div class="icon-container">
							<span class="ti-timer"></span><span class="icon-name">ti-timer</span>
						</div>
						<div class="icon-container">
							<span class="ti-ticket"></span><span class="icon-name">ti-ticket</span>
						</div>
						<div class="icon-container">
							<span class="ti-thumb-up"></span><span class="icon-name">ti-thumb-up</span>
						</div>
						<div class="icon-container">
							<span class="ti-thumb-down"></span><span class="icon-name">ti-thumb-down</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-stats-up"></span><span class="icon-name">ti-stats-up</span>
						</div>
						<div class="icon-container">
							<span class="ti-stats-down"></span><span class="icon-name">ti-stats-down</span>
						</div>
						<div class="icon-container">
							<span class="ti-shine"></span><span class="icon-name">ti-shine</span>
						</div>
						<div class="icon-container">
							<span class="ti-shift-right"></span><span class="icon-name">ti-shift-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-shift-left"></span><span class="icon-name">ti-shift-left</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-shift-right-alt"></span><span class="icon-name">ti-shift-right-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-shift-left-alt"></span><span class="icon-name">ti-shift-left-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-shield"></span><span class="icon-name">ti-shield</span>
						</div>
						<div class="icon-container">
							<span class="ti-notepad"></span><span class="icon-name">ti-notepad</span>
						</div>
						<div class="icon-container">
							<span class="ti-server"></span><span class="icon-name">ti-server</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-pulse"></span><span class="icon-name">ti-pulse</span>
						</div>
						<div class="icon-container">
							<span class="ti-printer"></span><span class="icon-name">ti-printer</span>
						</div>
						<div class="icon-container">
							<span class="ti-power-off"></span><span class="icon-name">ti-power-off</span>
						</div>
						<div class="icon-container">
							<span class="ti-plug"></span><span class="icon-name">ti-plug</span>
						</div>
						<div class="icon-container">
							<span class="ti-pie-chart"></span><span class="icon-name">ti-pie-chart</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-panel"></span><span class="icon-name">ti-panel</span>
						</div>
						<div class="icon-container">
							<span class="ti-package"></span><span class="icon-name">ti-package</span>
						</div>
						<div class="icon-container">
							<span class="ti-music"></span><span class="icon-name">ti-music</span>
						</div>
						<div class="icon-container">
							<span class="ti-music-alt"></span><span class="icon-name">ti-music-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-mouse"></span><span class="icon-name">ti-mouse</span>
						</div>
						<div class="icon-container">
							<span class="ti-mouse-alt"></span><span class="icon-name">ti-mouse-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-money"></span><span class="icon-name">ti-money</span>
						</div>
						<div class="icon-container">
							<span class="ti-microphone"></span><span class="icon-name">ti-microphone</span>
						</div>
						<div class="icon-container">
							<span class="ti-menu"></span><span class="icon-name">ti-menu</span>
						</div>
						<div class="icon-container">
							<span class="ti-menu-alt"></span><span class="icon-name">ti-menu-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-map"></span><span class="icon-name">ti-map</span>
						</div>
						<div class="icon-container">
							<span class="ti-map-alt"></span><span class="icon-name">ti-map-alt</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-location-pin"></span><span class="icon-name">ti-location-pin</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-light-bulb"></span><span class="icon-name">ti-light-bulb</span>
						</div>
						<div class="icon-container">
							<span class="ti-info"></span><span class="icon-name">ti-info</span>
						</div>
						<div class="icon-container">
							<span class="ti-infinite"></span><span class="icon-name">ti-infinite</span>
						</div>
						<div class="icon-container">
							<span class="ti-id-badge"></span><span class="icon-name">ti-id-badge</span>
						</div>
						<div class="icon-container">
							<span class="ti-hummer"></span><span class="icon-name">ti-hummer</span>
						</div>
						<div class="icon-container">
							<span class="ti-home"></span><span class="icon-name">ti-home</span>
						</div>
						<div class="icon-container">
							<span class="ti-help"></span><span class="icon-name">ti-help</span>
						</div>
						<div class="icon-container">
							<span class="ti-headphone"></span><span class="icon-name">ti-headphone</span>
						</div>
						<div class="icon-container">
							<span class="ti-harddrives"></span><span class="icon-name">ti-harddrives</span>
						</div>
						<div class="icon-container">
							<span class="ti-harddrive"></span><span class="icon-name">ti-harddrive</span>
						</div>
						<div class="icon-container">
							<span class="ti-gift"></span><span class="icon-name">ti-gift</span>
						</div>
						<div class="icon-container">
							<span class="ti-game"></span><span class="icon-name">ti-game</span>
						</div>
						<div class="icon-container">
							<span class="ti-filter"></span><span class="icon-name">ti-filter</span>
						</div>
						<div class="icon-container">
							<span class="ti-files"></span><span class="icon-name">ti-files</span>
						</div>
						<div class="icon-container">
							<span class="ti-file"></span><span class="icon-name">ti-file</span>
						</div>
						<div class="icon-container">
							<span class="ti-zip"></span><span class="icon-name">ti-zip</span>
						</div>
						<div class="icon-container">
							<span class="ti-folder"></span><span class="icon-name">ti-folder</span>
						</div>			
						<div class="icon-container">
							<span class="ti-envelope"></span><span class="icon-name">ti-envelope</span>
						</div>
			
			
						<div class="icon-container">
							<span class="ti-dashboard"></span><span class="icon-name">ti-dashboard</span>
						</div>
						<div class="icon-container">
							<span class="ti-cloud"></span><span class="icon-name">ti-cloud</span>
						</div>
						<div class="icon-container">
							<span class="ti-cloud-up"></span><span class="icon-name">ti-cloud-up</span>
						</div>
						<div class="icon-container">
							<span class="ti-cloud-down"></span><span class="icon-name">ti-cloud-down</span>
						</div>
						<div class="icon-container">
							<span class="ti-clipboard"></span><span class="icon-name">ti-clipboard</span>
						</div>
						<div class="icon-container">
							<span class="ti-calendar"></span><span class="icon-name">ti-calendar</span>
						</div>
						<div class="icon-container">
							<span class="ti-book"></span><span class="icon-name">ti-book</span>
						</div>
						<div class="icon-container">
							<span class="ti-bell"></span><span class="icon-name">ti-bell</span>
						</div>
						<div class="icon-container">
							<span class="ti-basketball"></span><span class="icon-name">ti-basketball</span>
						</div>
						<div class="icon-container">
							<span class="ti-bar-chart"></span><span class="icon-name">ti-bar-chart</span>
						</div>
						<div class="icon-container">
							<span class="ti-bar-chart-alt"></span><span class="icon-name">ti-bar-chart-alt</span>
						</div>
			
			
						<div class="icon-container">
							<span class="ti-archive"></span><span class="icon-name">ti-archive</span>
						</div>
						<div class="icon-container">
							<span class="ti-anchor"></span><span class="icon-name">ti-anchor</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-alert"></span><span class="icon-name">ti-alert</span>
						</div>
						<div class="icon-container">
							<span class="ti-alarm-clock"></span><span class="icon-name">ti-alarm-clock</span>
						</div>
						<div class="icon-container">
							<span class="ti-agenda"></span><span class="icon-name">ti-agenda</span>
						</div>
						<div class="icon-container">
							<span class="ti-write"></span><span class="icon-name">ti-write</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-wallet"></span><span class="icon-name">ti-wallet</span>
						</div>
						<div class="icon-container">
							<span class="ti-video-clapper"></span><span class="icon-name">ti-video-clapper</span>
						</div>
						<div class="icon-container">
							<span class="ti-video-camera"></span><span class="icon-name">ti-video-camera</span>
						</div>
						<div class="icon-container">
							<span class="ti-vector"></span><span class="icon-name">ti-vector</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-support"></span><span class="icon-name">ti-support</span>
						</div>
						<div class="icon-container">
							<span class="ti-stamp"></span><span class="icon-name">ti-stamp</span>
						</div>
						<div class="icon-container">
							<span class="ti-slice"></span><span class="icon-name">ti-slice</span>
						</div>
						<div class="icon-container">
							<span class="ti-shortcode"></span><span class="icon-name">ti-shortcode</span>
						</div>
						<div class="icon-container">
							<span class="ti-receipt"></span><span class="icon-name">ti-receipt</span>
						</div>
						<div class="icon-container">
							<span class="ti-pin2"></span><span class="icon-name">ti-pin2</span>
						</div>
						<div class="icon-container">
							<span class="ti-pin-alt"></span><span class="icon-name">ti-pin-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-pencil-alt2"></span><span class="icon-name">ti-pencil-alt2</span>
						</div>
						<div class="icon-container">
							<span class="ti-eraser"></span><span class="icon-name">ti-eraser</span>
						</div>			
						<div class="icon-container">
							<span class="ti-more"></span><span class="icon-name">ti-more</span>
						</div>
						<div class="icon-container">
							<span class="ti-more-alt"></span><span class="icon-name">ti-more-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-microphone-alt"></span><span class="icon-name">ti-microphone-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-magnet"></span><span class="icon-name">ti-magnet</span>
						</div>
						<div class="icon-container">
							<span class="ti-line-double"></span><span class="icon-name">ti-line-double</span>
						</div>
						<div class="icon-container">
							<span class="ti-line-dotted"></span><span class="icon-name">ti-line-dotted</span>
						</div>
						<div class="icon-container">
							<span class="ti-line-dashed"></span><span class="icon-name">ti-line-dashed</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-ink-pen"></span><span class="icon-name">ti-ink-pen</span>
						</div>
						<div class="icon-container">
							<span class="ti-info-alt"></span><span class="icon-name">ti-info-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-help-alt"></span><span class="icon-name">ti-help-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-headphone-alt"></span><span class="icon-name">ti-headphone-alt</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-gallery"></span><span class="icon-name">ti-gallery</span>
						</div>
						<div class="icon-container">
							<span class="ti-face-smile"></span><span class="icon-name">ti-face-smile</span>
						</div>
						<div class="icon-container">
							<span class="ti-face-sad"></span><span class="icon-name">ti-face-sad</span>
						</div>
						<div class="icon-container">
							<span class="ti-credit-card"></span><span class="icon-name">ti-credit-card</span>
						</div>
						<div class="icon-container">
							<span class="ti-comments-smiley"></span><span class="icon-name">ti-comments-smiley</span>
						</div>
						<div class="icon-container">
							<span class="ti-time"></span><span class="icon-name">ti-time</span>
						</div>
						<div class="icon-container">
							<span class="ti-share"></span><span class="icon-name">ti-share</span>
						</div>
						<div class="icon-container">
							<span class="ti-share-alt"></span><span class="icon-name">ti-share-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-rocket"></span><span class="icon-name">ti-rocket</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-new-window"></span><span class="icon-name">ti-new-window</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-rss"></span><span class="icon-name">ti-rss</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-rss-alt"></span><span class="icon-name">ti-rss-alt</span>
						</div>
						
					</div><!-- Web App Icons -->
			
			
					<div class="icon-section">
					
						<h3>Control Icons</h3>
						
						<div class="icon-container">
							<span class="ti-control-stop"></span><span class="icon-name">ti-control-stop</span>
						</div>
						<div class="icon-container">
							<span class="ti-control-shuffle"></span><span class="icon-name">ti-control-shuffle</span>
						</div>
						<div class="icon-container">
							<span class="ti-control-play"></span><span class="icon-name">ti-control-play</span>
						</div>
						<div class="icon-container">
							<span class="ti-control-pause"></span><span class="icon-name">ti-control-pause</span>
						</div>
						<div class="icon-container">
							<span class="ti-control-forward"></span><span class="icon-name">ti-control-forward</span>
						</div>
						<div class="icon-container">
							<span class="ti-control-backward"></span><span class="icon-name">ti-control-backward</span>
						</div>	
						<div class="icon-container">
							<span class="ti-volume"></span><span class="icon-name">ti-volume</span>
						</div>
						<div class="icon-container">
							<span class="ti-control-skip-forward"></span><span class="icon-name">ti-control-skip-forward</span>
						</div>
						<div class="icon-container">
							<span class="ti-control-skip-backward"></span><span class="icon-name">ti-control-skip-backward</span>
						</div>
						<div class="icon-container">
							<span class="ti-control-record"></span><span class="icon-name">ti-control-record</span>
						</div>
						<div class="icon-container">
							<span class="ti-control-eject"></span><span class="icon-name">ti-control-eject</span>
						</div>			
					</div> <!-- Control Icons -->
			
			
					<div class="icon-section">
					
						<h3>Text Editor</h3>
			
						<div class="icon-container">
							<span class="ti-paragraph"></span><span class="icon-name">ti-paragraph</span>
						</div>
						<div class="icon-container">
							<span class="ti-uppercase"></span><span class="icon-name">ti-uppercase</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-underline"></span><span class="icon-name">ti-underline</span>
						</div>
						<div class="icon-container">
							<span class="ti-text"></span><span class="icon-name">ti-text</span>
						</div>
						<div class="icon-container">
							<span class="ti-Italic"></span><span class="icon-name">ti-Italic</span>
						</div>
						<div class="icon-container">
							<span class="ti-smallcap"></span><span class="icon-name">ti-smallcap</span>
						</div>
						<div class="icon-container">
							<span class="ti-list"></span><span class="icon-name">ti-list</span>
						</div>
						<div class="icon-container">
							<span class="ti-list-ol"></span><span class="icon-name">ti-list-ol</span>
						</div>
						<div class="icon-container">
							<span class="ti-align-right"></span><span class="icon-name">ti-align-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-align-left"></span><span class="icon-name">ti-align-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-align-justify"></span><span class="icon-name">ti-align-justify</span>
						</div>
						<div class="icon-container">
							<span class="ti-align-center"></span><span class="icon-name">ti-align-center</span>
						</div>
						<div class="icon-container">
							<span class="ti-quote-right"></span><span class="icon-name">ti-quote-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-quote-left"></span><span class="icon-name">ti-quote-left</span>
						</div>
			
					</div> <!-- Text Editor -->
			
			
			
					<div class="icon-section">
					
						<h3>Layout Icons</h3>
						
						<div class="icon-container">
							<span class="ti-layout-width-full"></span><span class="icon-name">ti-layout-width-full</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-width-default"></span><span class="icon-name">ti-layout-width-default</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-width-default-alt"></span><span class="icon-name">ti-layout-width-default-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-tab"></span><span class="icon-name">ti-layout-tab</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-tab-window"></span><span class="icon-name">ti-layout-tab-window</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-tab-v"></span><span class="icon-name">ti-layout-tab-v</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-tab-min"></span><span class="icon-name">ti-layout-tab-min</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-slider"></span><span class="icon-name">ti-layout-slider</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-slider-alt"></span><span class="icon-name">ti-layout-slider-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-sidebar-right"></span><span class="icon-name">ti-layout-sidebar-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-sidebar-none"></span><span class="icon-name">ti-layout-sidebar-none</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-sidebar-left"></span><span class="icon-name">ti-layout-sidebar-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-placeholder"></span><span class="icon-name">ti-layout-placeholder</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-menu"></span><span class="icon-name">ti-layout-menu</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-menu-v"></span><span class="icon-name">ti-layout-menu-v</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-menu-separated"></span><span class="icon-name">ti-layout-menu-separated</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-menu-full"></span><span class="icon-name">ti-layout-menu-full</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-media-right"></span><span class="icon-name">ti-layout-media-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-media-right-alt"></span><span class="icon-name">ti-layout-media-right-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-media-overlay"></span><span class="icon-name">ti-layout-media-overlay</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-media-overlay-alt"></span><span class="icon-name">ti-layout-media-overlay-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-media-overlay-alt-2"></span><span class="icon-name">ti-layout-media-overlay-alt-2</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-media-left"></span><span class="icon-name">ti-layout-media-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-media-left-alt"></span><span class="icon-name">ti-layout-media-left-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-media-center"></span><span class="icon-name">ti-layout-media-center</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-media-center-alt"></span><span class="icon-name">ti-layout-media-center-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-list-thumb"></span><span class="icon-name">ti-layout-list-thumb</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-list-thumb-alt"></span><span class="icon-name">ti-layout-list-thumb-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-list-post"></span><span class="icon-name">ti-layout-list-post</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-list-large-image"></span><span class="icon-name">ti-layout-list-large-image</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-line-solid"></span><span class="icon-name">ti-layout-line-solid</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-grid4"></span><span class="icon-name">ti-layout-grid4</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-grid3"></span><span class="icon-name">ti-layout-grid3</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-grid2"></span><span class="icon-name">ti-layout-grid2</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-grid2-thumb"></span><span class="icon-name">ti-layout-grid2-thumb</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-cta-right"></span><span class="icon-name">ti-layout-cta-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-cta-left"></span><span class="icon-name">ti-layout-cta-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-cta-center"></span><span class="icon-name">ti-layout-cta-center</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-cta-btn-right"></span><span class="icon-name">ti-layout-cta-btn-right</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-cta-btn-left"></span><span class="icon-name">ti-layout-cta-btn-left</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-column4"></span><span class="icon-name">ti-layout-column4</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-column3"></span><span class="icon-name">ti-layout-column3</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-column2"></span><span class="icon-name">ti-layout-column2</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-accordion-separated"></span><span class="icon-name">ti-layout-accordion-separated</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-accordion-merged"></span><span class="icon-name">ti-layout-accordion-merged</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-accordion-list"></span><span class="icon-name">ti-layout-accordion-list</span>
						</div>
						<div class="icon-container">
							<span class="ti-widgetized"></span><span class="icon-name">ti-widgetized</span>
						</div>
						<div class="icon-container">
							<span class="ti-widget"></span><span class="icon-name">ti-widget</span>
						</div>
						<div class="icon-container">
							<span class="ti-widget-alt"></span><span class="icon-name">ti-widget-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-view-list"></span><span class="icon-name">ti-view-list</span>
						</div>
						<div class="icon-container">
							<span class="ti-view-list-alt"></span><span class="icon-name">ti-view-list-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-view-grid"></span><span class="icon-name">ti-view-grid</span>
						</div>
						<div class="icon-container">
							<span class="ti-upload"></span><span class="icon-name">ti-upload</span>
						</div>
						<div class="icon-container">
							<span class="ti-download"></span><span class="icon-name">ti-download</span>
						</div>	
						<div class="icon-container">
							<span class="ti-loop"></span><span class="icon-name">ti-loop</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-sidebar-2"></span><span class="icon-name">ti-layout-sidebar-2</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-grid4-alt"></span><span class="icon-name">ti-layout-grid4-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-grid3-alt"></span><span class="icon-name">ti-layout-grid3-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-grid2-alt"></span><span class="icon-name">ti-layout-grid2-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-column4-alt"></span><span class="icon-name">ti-layout-column4-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-column3-alt"></span><span class="icon-name">ti-layout-column3-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-layout-column2-alt"></span><span class="icon-name">ti-layout-column2-alt</span>
						</div>		
			
			
					</div> <!-- Layout Icons -->
			
			
					<div class="icon-section">
					
						<h3>Brand Icons</h3>
			
						<div class="icon-container">
							<span class="ti-flickr"></span><span class="icon-name">ti-flickr</span>
						</div>
						<div class="icon-container">
							<span class="ti-flickr-alt"></span><span class="icon-name">ti-flickr-alt</span>
						</div>			
						<div class="icon-container">
							<span class="ti-instagram"></span><span class="icon-name">ti-instagram</span>
						</div>
						<div class="icon-container">
							<span class="ti-google"></span><span class="icon-name">ti-google</span>
						</div>
						<div class="icon-container">
							<span class="ti-github"></span><span class="icon-name">ti-github</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-facebook"></span><span class="icon-name">ti-facebook</span>
						</div>
						<div class="icon-container">
							<span class="ti-dropbox"></span><span class="icon-name">ti-dropbox</span>
						</div>
						<div class="icon-container">
							<span class="ti-dropbox-alt"></span><span class="icon-name">ti-dropbox-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-dribbble"></span><span class="icon-name">ti-dribbble</span>
						</div>
						<div class="icon-container">
							<span class="ti-apple"></span><span class="icon-name">ti-apple</span>
						</div>
						<div class="icon-container">
							<span class="ti-android"></span><span class="icon-name">ti-android</span>
						</div>
						<div class="icon-container">
							<span class="ti-yahoo"></span><span class="icon-name">ti-yahoo</span>
						</div>
						<div class="icon-container">
							<span class="ti-trello"></span><span class="icon-name">ti-trello</span>
						</div>
						<div class="icon-container">
							<span class="ti-stack-overflow"></span><span class="icon-name">ti-stack-overflow</span>
						</div>
						<div class="icon-container">
							<span class="ti-soundcloud"></span><span class="icon-name">ti-soundcloud</span>
						</div>
						<div class="icon-container">
							<span class="ti-sharethis"></span><span class="icon-name">ti-sharethis</span>
						</div>
						<div class="icon-container">
							<span class="ti-sharethis-alt"></span><span class="icon-name">ti-sharethis-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-reddit"></span><span class="icon-name">ti-reddit</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-microsoft"></span><span class="icon-name">ti-microsoft</span>
						</div>
						<div class="icon-container">
							<span class="ti-microsoft-alt"></span><span class="icon-name">ti-microsoft-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-linux"></span><span class="icon-name">ti-linux</span>
						</div>
						<div class="icon-container">
							<span class="ti-jsfiddle"></span><span class="icon-name">ti-jsfiddle</span>
						</div>
						<div class="icon-container">
							<span class="ti-joomla"></span><span class="icon-name">ti-joomla</span>
						</div>
						<div class="icon-container">
							<span class="ti-html5"></span><span class="icon-name">ti-html5</span>
						</div>
						<div class="icon-container">
							<span class="ti-css3"></span><span class="icon-name">ti-css3</span>
						</div>	
						<div class="icon-container">
							<span class="ti-drupal"></span><span class="icon-name">ti-drupal</span>
						</div>
						<div class="icon-container">
							<span class="ti-wordpress"></span><span class="icon-name">ti-wordpress</span>
						</div>		
						<div class="icon-container">
							<span class="ti-tumblr"></span><span class="icon-name">ti-tumblr</span>
						</div>
						<div class="icon-container">
							<span class="ti-tumblr-alt"></span><span class="icon-name">ti-tumblr-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-skype"></span><span class="icon-name">ti-skype</span>
						</div>
						<div class="icon-container">
							<span class="ti-youtube"></span><span class="icon-name">ti-youtube</span>
						</div>
						<div class="icon-container">
							<span class="ti-vimeo"></span><span class="icon-name">ti-vimeo</span>
						</div>
						<div class="icon-container">
							<span class="ti-vimeo-alt"></span><span class="icon-name">ti-vimeo-alt</span>
						</div>			
						<div class="icon-container">
							<span class="ti-twitter"></span><span class="icon-name">ti-twitter</span>
						</div>
						<div class="icon-container">
							<span class="ti-twitter-alt"></span><span class="icon-name">ti-twitter-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-linkedin"></span><span class="icon-name">ti-linkedin</span>
						</div>
						<div class="icon-container">
							<span class="ti-pinterest"></span><span class="icon-name">ti-pinterest</span>
						</div>
			
						<div class="icon-container">
							<span class="ti-pinterest-alt"></span><span class="icon-name">ti-pinterest-alt</span>
						</div>
						<div class="icon-container">
							<span class="ti-themify-logo"></span><span class="icon-name">ti-themify-logo</span>
						</div>
						<div class="icon-container">
							<span class="ti-themify-favicon"></span><span class="icon-name">ti-themify-favicon</span>
						</div>
						<div class="icon-container">
							<span class="ti-themify-favicon-alt"></span><span class="icon-name">ti-themify-favicon-alt</span>
						</div>
			
					</div> <!-- brand Icons -->
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" onclick="return updateMenu();" class="btn btn-default waves-effect waves-light">
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
<script src="resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/complementos.js"></script>
<script src="resources/assets/js/setupmenu.js"></script>
<script type="text/javascript">
	TableManageButtons.init();
	var dateOp={weekStart:1,daysOfWeekHighlighted:"0",language:lang,autoclose:true,
		todayBtn:true,clearBtn:true,calendarWeeks:true,todayHighlight:true};
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
</script>