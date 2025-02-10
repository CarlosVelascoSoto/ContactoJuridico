<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<fmt:setBundle basename="messages" />
<title><fmt:message key="edit.profile.title" /> - Contacto jur&iacute;dico</title>

<link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/basic.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/dropzone.css">
<link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css">

<style>
	hr{margin:0 10px 20px 10px;border-color:#bbb}
	.unuse{background-color:#F4F4F2;}
	.imgflag{max-width:100%; max-height:34px;}
	/* Rd-btn(ini) */
	input[type="radio"]{display:none;}
	.check-box{
	  position:relative;
	  height:15px;
	  width:15px;
	  top:8px;
	  border:1px solid #000;
	  border-radius:3px;
	  background-color:transparent;
	  box-sizing:border-box;
	  -moz-box-sizing:border-box;
	  -webkit-box-sizing:border-box;
	  transition:border-color ease 0.2s;
	  -o-transition:border-color ease 0.2s;
	  -moz-transition:border-color ease 0.2s;
	  -webkit-transition:border-color ease 0.2s;
	  cursor:pointer;
	}
	.check-box::before, .check-box::after{
	  content:'';
	  position:absolute;
	  height:0;
	  width:3px;
	  display:inline-block;
	  border-radius:3px;
	  background-color:#34b93d;
	  box-sizing:border-box;
	  -moz-box-sizing:border-box;
	  -webkit-box-sizing:border-box;
	  -webkit-transform-origin:left top;
	  -moz-transform-origin:left top;
	  -ms-transform-origin:left top;
	  -o-transform-origin:left top;
	  transform-origin:left top;
	  -webkit-transition:opacity ease .5;
	  -moz-transition:opacity ease .5;
	  transition:opacity ease .5;
	}
	.check-box::before{
	  top:11px;
	  left:8px;
	  -webkit-transform:rotate(-135deg);
	  -moz-transform:rotate(-135deg);
	  -ms-transform:rotate(-135deg);
	  -o-transform:rotate(-135deg);
	  transform:rotate(-135deg);
	}
	.check-box::after{
	  top:5px;
	  -webkit-transform:rotate(-45deg);
	  -moz-transform:rotate(-45deg);
	  -ms-transform:rotate(-45deg);
	  -o-transform:rotate(-45deg);
	  transform:rotate(-45deg);
	}
	input[type="radio"]:checked + .check-box,
	.check-box.checked{border-color:#34b93d;}
	input[type="radio"]:checked + .check-box::after,
	.check-box.checked::after{
	  height:50px;
	  -webkit-animation:dothabottomcheck 0.2s ease 0s forwards;
	  -moz-animation:dothabottomcheck 0.2s ease 0s forwards;
	  -o-animation:dothabottomcheck 0.2s ease 0s forwards;
	  animation:dothabottomcheck 0.2s ease 0s forwards;
	}
	input[type="radio"]:checked + .check-box::before,
	.check-box.checked::before{
	  height:120px;
	  -webkit-animation:dothatopcheck 0.4s ease 0s forwards;
	  -moz-animation:dothatopcheck 0.4s ease 0s forwards;
	  -o-animation:dothatopcheck 0.4s ease 0s forwards;
	  animation:dothatopcheck 0.4s ease 0s forwards;
	}
	@-webkit-keyframes dothabottomcheck{
	  0%{height:0;}
	  100%{height:20px;}
	}
	@-moz-keyframes dothabottomcheck{
	  0%{height:0;}
	  100%{height:50px;}
	}
	@keyframes dothabottomcheck{
	  0%{height:0;}
	  100%{height:10px;}
	}
	@-webkit-keyframes dothatopcheck{
	  0%{height:0;}
	  50%{height:0;}
	  100%{height:20px;}
	}
	@-moz-keyframes dothatopcheck{
	  0%{height:0;}
	  50%{height:0;}
	  100%{height:20px;}
	}
	@keyframes dothatopcheck{
	  0%{height:0;}
	  50%{height:0;}
	  100%{height:20px;}
	}/* Rd-btn (end) */
	#uploadXEdituser{width:50%;min-width:230px;min-height:280px;left:25%;border:1px dotted #000 !important}
	#areaEditUserUpload >span{position:absolute;
		top:35%;
		z-index:1;
		max-width:200px;
		margin:auto;
		color:#bcbcbc;
		right:0;
		left:0;
		-webkit-touch-callout:none;
    -webkit-user-select:none;
     -khtml-user-select:none;
       -moz-user-select:none;
        -ms-user-select:none;
            user-select:none;
	}
</style>

<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<h4 class="page-title capitalize"><fmt:message key="edit.profile.title" /></h4>
				</div>
			</div>
			<div class="card-box">
				<form class="form-horizontal" id="formuseredit">
					<div class="form-group">
						<div class="row">
							<div class="col-xs-12 col-md-6">
								<div class="col-xs-12 form-group tac m-0" style="min-height:300px">
									<label class="control-label"><fmt:message key="text.updateprofilepicture" />:</label>
									<div id="areaEditUserUpload">
										<span class="textContent" onclick="$('#uploadXEdituser').trigger('click');" style="cursor:pointer">
											<fmt:message key='label.dropzone' /></span>
										<div id='uploadXEdituser' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
									</div>
									<input type="hidden" id="photo" value="${profile.photo_name}">
									<input type="hidden" id="id" value="${profile.id}">
								</div>
							</div>
							<div class="col-xs-12 col-md-6">
								<div class="col-xs-12 form-group">
									<label for="first_name" class="col-sm-3 control-label"><em>* </em><fmt:message key="text.firstname" /></label>
									<div class="col-sm-7">
										<input type="text" id="first_name" name="first_name" class="form-control"
											placeholder="<fmt:message key='text.firstname' />" value="${profile.first_name}" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label for="last_name" class="col-sm-3 control-label"><em>* </em><fmt:message key="text.lastname" /></label>
									<div class="col-sm-7">
										<input type="text" id="last_name" name="last_name" class="form-control"
											placeholder="<fmt:message key='text.lastname' />" value="${profile.last_name}" autocomplete="off">
									</div>
								</div>
						<!--		<div class="col-xs-12 form-group">
									<label for="firm" class="col-sm-3 control-label"><fmt:message key="text.firm" /></label>
									<div class="col-sm-7">
										<input type="text" id="firm" name="firm" class="form-control"
											placeholder="<fmt:message key='text.firmlegalofficename' />" value="" autocomplete="off">
										</div>
								  </div>   -->
								<div class="col-xs-12 form-group">
									<label for="phone" class="col-sm-3 control-label"><fmt:message key="text.phone" /></label>
									<div class="col-sm-7">
										<input type="tel" id="phone" name="phone" class="form-control"
											placeholder="<fmt:message key='text.phone' />" value="${profile.phone}" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label for="cellphone" class="col-sm-3 control-label"><fmt:message key="text.cellphone" /></label>
									<div class="col-sm-7">
										<input type="tel" id="cellphone" name="cellphone" class="form-control"
											placeholder="<fmt:message key='text.cellphone' />" value="${profile.cellphone}" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-3 control-label"><fmt:message key="text.user.name" /></label>
									<div class="col-sm-7">
										<span id="username" class="form-control unuse">${profile.username}</span>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-3 control-label"><fmt:message key="text.email" /></label>
									<div class="col-sm-7">
										<span id="email" class="form-control unuse" style="overflow:hidden">${profile.email}</span>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-3 control-label"><fmt:message key="language" /></label>
									<div class="col-sm-7">
										<img class="imgflag" src="resources/assets/images/Flag-Mex.png" alt="(ES)" title="Cambiar a idioma espa�ol"
											onclick="$('#flag-es').prop('checked',true);"/>&nbsp;
										<input type="radio"  id="flag-es" class="flag-lang" value="es" 
											name="flaglang" <c:if test="${language!='en'}">checked</c:if> />&nbsp;
										<label for="flag-es" class="check-box inlineflex"></label>&nbsp;
										<label for="lang-es">Espa&ntilde;ol</label><br>
										<img class="imgflag" src="resources/assets/images/Flag-USA.png" alt="(EN)" title="Change to english language"
											onclick="$('#flag-en').prop('checked',true);"/>&nbsp;
										<input type="radio"  id="flag-en" class="flag-lang"  value="en"
											name="flaglang" <c:if test="${language=='en'}">checked</c:if> />&nbsp;
										<label for="flag-en" class="check-box inlineflex"></label>&nbsp;
										<label for="lang-en">English</label>
									</div>
								</div>
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="col-xs-12 col-md-6" style="border-right:1px solid #bbb;padding:0 0 0 20px">
								<div class="col-xs-12 form-group">
									<div id="ErrorSelectSomething">
										<a href="#" class="close" style="color:#000;display: flex;position: absolute;right: 20px;top: 10px;" aria-label="close"
										onclick="$('#ErrorSelectSomething').hide()">&times;</a>
										<p id="putErrorSelectSomething"
										 style="display: block;background-color: #FCDCDC;border: 1px solid #F05050;color: #F05252;border-radius: 5px;padding: 10px 7px;">
										 </p>
									</div>
									<label for="address" class="col-sm-4 control-label"><fmt:message key="text.address" /></label>
									<div class="col-sm-8">
										<input type="text" id="address" name="address" class="form-control"
											placeholder="<fmt:message key='text.address' />" value="${profile.address}" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label for="zipcode" class="col-sm-4 control-label"><fmt:message key="text.zipcode" /></label>
									<div class="col-sm-8">
										<input type="text" id="zipcode" name="zipcode" class="form-control" value="${profile.zipcode}"
											placeholder="<fmt:message key='text.zipcode' />" onblur="value=value.replace(/[^0-9]+/g,'');" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label for="profcountry" class="col-sm-4 control-label"><fmt:message key="text.country" /></label>
									<div class="col-sm-8">
										<input type="text" class="form-control listfiltersel" id="profcountry" value="${country}"
											placeholder="<fmt:message key="text.select" />" autocomplete="off">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="country"></ul>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label for="profstate" class="col-sm-4 control-label"><fmt:message key="text.state" /></label>
									<div class="col-sm-8">
										<input type="text" class="form-control listfiltersel" id="profstate" value="${state}"
											placeholder="<fmt:message key="text.select" />" autocomplete="off">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="state"></ul>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label for="profcity" class="col-sm-4 control-label"><fmt:message key="text.city" /></label>
									<div class="col-sm-8">
										<input type="text" class="form-control listfiltersel" value="${city}"
											id="profcity" placeholder="<fmt:message key="text.select" />" autocomplete="off">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="city"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-md-6">
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.status" /></label>
									<div class="col-sm-8">
										<span class="form-control unuse">
										<c:if test="${profile.status==0}">
											<fmt:message key="status.desactive" />
										</c:if>
										<c:if test="${profile.status==1}">
											<fmt:message key="status.active" />
										</c:if>
										</span>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.role" /></label>
									<div class="col-sm-8"><span class="form-control unuse">${rolename}</span></div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.created" /></label>
									<div class="col-sm-8">
										<span class="form-control unuse">
											<c:choose>
											<c:when test="${language=='en'}">
												<fmt:formatDate value="${profile.created}" pattern="MM/dd/yyyy HH:mm" />
											</c:when>
											<c:otherwise>
												<fmt:formatDate value="${profile.created}" pattern="dd/MM/yyyy HH:mm" />
											</c:otherwise>
										</c:choose>
										</span>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="edit.profile.lastupdate" /></label>
									<div class="col-sm-8">
										<span class="form-control unuse">
											<c:choose>
												<c:when test="${language=='en'}">
													<fmt:formatDate value="${profile.updated}" pattern="MM/dd/yyyy HH:mm" />
												</c:when>
												<c:otherwise>
													<fmt:formatDate value="${profile.updated}" pattern="dd/MM/yyyy HH:mm" />
												</c:otherwise>
											</c:choose>
										</span>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<button type="button" onclick="history.back();" class="btn btn-info waves-effect" data-dismiss="modal">
									<fmt:message key="button.back" />
								</button>
								<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
								<button type="button" id="savechanges" class="btn btn-default waves-effect waves-light">
									<fmt:message key="button.savechanges" />
								</button>
								</c:if>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<footer class="footer">
	<fmt:message key="text.copyright" />
</footer>

<script src="resources/assets/js/jquery.min.js"></script>
<script src="resources/assets/plugins/dropzone/dropzone.js"></script>
<script src="resources/assets/js/customDropZoneImg.js"></script>
<script src="resources/local/general/profile/userprofile.js"></script>

<script>
	try{
		createDropZoneImg('uploadXEdituser', 'formuseredit',$('#id').val(),'-10',1);
	}catch (e){
		$('#areaEditUserUpload').html('');
		$('#areaEditUserUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'
			+ '<div id="uploadXEdituser" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZoneImg('uploadXEdituser', 'formuseredit',$('#id').val(),'-10',1);
	}
	$("#uploadXEdituser").addClass("dropzone");
	var profileDropzone=Dropzone.forElement('#uploadXEdituser');
	Dropzone.options.profileDropzone = {
		maxFiles: 1,
		accept: function(file, done){
			done();
		},init: function(){
			this.on("addedfile", function(){
				if (this.files[1]!=null)
					this.removeFile(this.files[0]);
			});
		}
	};
</script>