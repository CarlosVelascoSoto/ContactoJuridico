<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="language" value='es' scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />

<html lang="${language}">
<head>
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="pragma" content="no-cache">
	<meta name="revised" content="January 16th, 2025, 5:44 pm">
	<link rel="shortcut icon" href="resources/assets/images/favicon_1.ico">
	<title>Contacto Jur&iacute;dico</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" type="text/css" href="resources/assets/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="resources/assets/css/core.css">
	<link rel="stylesheet" type="text/css" href="resources/assets/css/components.css">
	<link rel="stylesheet" type="text/css" href="resources/assets/css/icons.css">
	<link rel="stylesheet" type="text/css" href="resources/assets/css/pages.css">
	<link rel="stylesheet" type="text/css" href="resources/assets/css/responsive.css">
	<link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css">

	<style>
		.panel-heading{text-align:center}
		.panel-body{overflow:hidden}
		.contact-logo{max-width:170px}
		.asLink{cursor:pointer}
		.inlineflex{display:-webkit-inline-flex;display:-webkit-inline-box;display:-moz-inline-box;display:-ms-inline-flexbox;display:inline-flex}
		.inlineblock{display:-moz-inline-box;*display:inline;display:inline-block}
		.asLink:hover{color:#1B71D4}
		.fs12{font-size:12px}
		.sweet-overlay{z-index:10010 !important}
		.showSweetAlert{z-index:10011 !important}
		#startpage,#newaccount{position:relative;min-width:100%}
		#newaccount{display:none;left:400px}
		#loginError,#newaccount,#newaccountError{display:none}
		.toggle-input{position:absolute;top:7px;right:30px;color:#444;cursor:pointer}

		.btn_blue2{background-color:#76a0ef;color:#fff}
		.btn_blue2light{background-color:#8585ad;color:#fff}
		.supLlb{position:absolute;font-weight:400;font-size:12px;top:-9px;left:20px;padding:0 5px;background-color:#fff;z-index:4}
		/*DropDown (ini)* /
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
			height:45px;
			padding:10px 40px 10px 10px;
			-webkit-appearance:none;
			-moz-appearance:none;
			-ms-appearance:none;
			appearance:none;
			border:1px solid #bbb;
			background-color:transparent;
			z-index:3;
		}/ *DropDown (fin)*/
		/*DropDown Filter (ini)
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
	    / *DropDown Filter (fin)*/
		.w100p{width:100%}

		.rotate-scale-up{position:absolute;display:inline-block;color:red;animation:rotateScaleUp .65s linear 2 alternate;z-index:10}
		@keyframes rotateScaleUp{0%{transform:scale(1) rotateZ(0);}50%{transform:scale(2) rotateZ(180deg)}100%{transform:scale(1) rotateZ(360deg)}}
	</style>
</head>
<body>
<div class="account-pages"></div>
<div class="clearfix"></div>
<div class="wrapper-page">
	<div class="card-box">
		<div class="panel-heading">
			<img class="contact-logo" src="resources/assets/images/contacto-juridico-logo.jpeg" title="Contacto Jur&iacute;dico" alt="CONTACTO JURIDICO">
		</div>
		<div class="panel-body w100p inlineflex">
			<div id="startpage" class="inlineblock">
				<form class="form-horizontal m-t-20" action="" method="post">
					<div class="form-group ">
						<div class="col-xs-12">
							<div id="loginError" class="alert alert-danger fade in">
								<a href="#" class="close"aria-label="close" onclick="$('#loginError').toggle();">&times;</a>
								<p><fmt:message key="login.text.incorrect" /></p>
							</div>
						</div>
					</div>
					<div class="form-group ">
						<div class="col-xs-12">
						<input class="form-control" name="userLogin" id="userLogin" type="text" placeholder="<fmt:message key='text.user.name' />" required autofocus>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<input class="form-control" name="password" id="password" type="password" placeholder="<fmt:message key='text.password' />" required >
						</div>
					</div>
					<div class="form-group ">
						<div class="col-xs-12">
							<div class="checkbox checkbox-primary">
								 <input id="remMe" type="checkbox" name="remMe"value="remMe_value">
								 <label for="checkbox-signup"><fmt:message key="login.check.remember" /></label>
							</div>
						</div>
					</div>
					<div class="form-group text-center m-t-40">
						<div class="col-xs-12">
						<button class="btn btn-pink btn-block text-uppercase waves-effect waves-light" onclick="javascript:return login();" type="button">
							<fmt:message key="login.button" />
						</button>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 fs12">
							<p class="asLink"
								onclick="$('input').val('');$('#startpage').animate({left:'-400px'},{duration:250});$('#newaccount').animate({left:'-370px'},{duration:250}).toggle();">
							<fmt:message key="text.createaccount" /></p>
						</div>
<!--div class="col-xs-12 col-sm-6 fs12 text-right">
	<p class="asLink"><fmt:message key="text.forgotpassword" /></p>
</div-->
					</div>
				</form>
			</div>

			<div id="newaccount" class="inlineblock">
				<form class="form-horizontal m-t-20" action="" method="post">
					<div class="form-group ">
						<div class="col-xs-12">
							<div style="display:none;" id="newaccountError" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#newaccountError').toggle();">&times;</a>
								<p id="putErrorOnAdd"></p>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group w100p">
									<input type="text" class="form-control" id="username" placeholder="<fmt:message key='text.user.name' />"
									value="<fmt:message key='text.user.name' />" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group w100p">
									<input type="text" class="form-control" id="firstname" placeholder="<fmt:message key='text.firstname' />" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group w100p">
									<input type="text" class="form-control" id="lastname" placeholder="<fmt:message key='text.lastname' />" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group w100p">
									<input type="password" class="form-control" id="createpass" placeholder="<fmt:message key='field.password.msg' />"
									value="<fmt:message key='field.password.msg' />" autocomplete="off">
									<i class="material-icons toggle-input" onclick="toggleEye(this,'createpass');">visibility_off</i>
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group w100p">
									<input type="password" class="form-control" id="repeatcreatepass" placeholder="<fmt:message key='text.repeatpassword' />" autocomplete="off">
									<i class="material-icons toggle-input" onclick="toggleEye(this,'repeatcreatepass');">visibility_off</i>
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group w100p">
									<input type="email" class="form-control" id="email" placeholder="<fmt:message key='text.emailaddress' />" autocomplete="off">
								</div>
							</div>
							
							<div class="col-xs-12">
								<div class="form-group w100p">
									<input type="text" class="form-control" id="firmname" placeholder="<fmt:message key='text.firmlegalofficename' />" autocomplete="off">
								</div>
							</div>
						</div>
	
						<div class="row">
							<div class="col-xs-12 text-center" style="margin-top:15px">
								<button type="button" onclick="createAccount(this);" class="btn btn_blue2 waves-effect waves-light">
									<fmt:message key="button.save" />
								</button>
								<button type="button" class="btn btn_blue2light waves-effect waves-light m-l-10" onclick="returntostart();">
									<fmt:message key="button.cancel" />
								</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		 </div>
	</div>
</div>

<script src="resources/assets/js/jquery.min.js"></script>
<script src="resources/assets/js/bootstrap.min.js"></script>
<script src="resources/assets/js/detect.js"></script>
<script src="resources/assets/js/fastclick.js"></script>
<script src="resources/assets/js/jquery.slimscroll.js"></script>
<script src="resources/assets/js/jquery.blockUI.js"></script>
<script src="resources/assets/js/waves.js"></script>
<script src="resources/assets/js/wow.min.js"></script>
<script src="resources/assets/js/jquery.nicescroll.js"></script>
<script src="resources/assets/js/jquery.scrollTo.min.js"></script>
<script src="resources/assets/js/jquery.core.js"></script>
<script src="resources/assets/js/jquery.app.js"></script>
<script src="resources/assets/plugins/sweet-alert2/sweetalert2.min.js"></script>
<script src="resources/assets/js-aj/ajpage.js"></script>

<script>var ctx="${pageContext.request.contextPath}";</script>
<script>
	polyfill();
	var resizefunc=[];
	$("#userLogin").keypress(function(event){if(event.which==13){login();}});
	$("#password").keypress(function(event){if(event.which==13){login();}});

	function polyfill(){
		if(typeof(Storage)!=="undefined"){
			window.sessionStorage={_data:{},
				setItem:function(id,val){return this._data[id]=String(val);},
				getItem:function(id){return this._data.hasOwnProperty(id)?this._data[id]:undefined;},
				removeItem:function(id){return delete this._data[id];},
				clear:function(){return this._data={};}
			};
			window.localStorage={_data:{},
				setItem:function(id,val){return this._data[id]=String(val);},
				getItem:function(id){return this._data.hasOwnProperty(id)?this._data[id]:undefined;},
				removeItem:function(id){return delete this._data[id];},
				clear:function(){return this._data={};}
			};
		}
	};

	function login(){
		var userLogin=$("#userLogin").val(),password=$("#password").val(),remMe=$('#remMe').is(":checked");
		$.ajax({
			async:true,
			type:"POST",
			url:"./loginProcess",
			data:"userLogin="+userLogin+"&password="+password+"&remMe="+remMe,
			async:false,
			success:function(data,e){
				if((data==='false')||(data==='notexists')){
					if($('#remMe').is(':checked')){//Save username and password
						localStorage.userLogin=$('#userLogin').val();
						localStorage.password=$('#password').val();
						localStorage.chkbx=$('#remMe').val();
					}else{
						localStorage.userLogin='';
						localStorage.password='';
						localStorage.chkbx='';
					}$('#loginError').css('display','block');
					return false;
				}else{
					if($('#remMe').is(':checked')){
						localStorage.userLogin=$('#userLogin').val();
						localStorage.password=$('#password').val();
						localStorage.chkbx=$('#remMe').val();
					}else{
						localStorage.userLogin='';
						localStorage.password='';
						localStorage.chkbx='';
					}
				}var ln=data.language==null?"":"?language="+data.language;
				window.location.href="${pageContext.request.contextPath}/home"+ln;
			},error:function(e){
				console.log("Error "+e.status+'\n'+e);
				alert("La sesi\u00f3n anterior se cerr\u00f3 autom\u00e1ticamente, para liberar la cuenta se volver\u00e1 a cargar la p\u00e1gina.");
				location.href=location.href.replace(/^(.*)\#.*$/, '$1');
			}
		});
	};

	var varOne='${cookie.userLogin.value}',varTwo='${cookie.passWord.value}';
	if(varOne!=null&&varOne.trim()!="null"&&varOne.trim()!=""){
		if(varTwo!=null&&varTwo.trim()!="null"&&varTwo.trim()!=""){
			document.getElementById("userLogin").value=varOne;
			document.getElementById("password").value=varTwo;
			$('#remMe').prop("checked", true);
			login();
		}
	};

	$(function(){
		if(localStorage.chkbx&&localStorage.chkbx!=''){
			$('#remMe').attr('checked', 'checked');
			$('#userLogin').val(localStorage.userLogin);
			$('#password').val(localStorage.password);
		}else{
			$('#remMe').removeAttr('checked');
			$('#userLogin').val('');
			$('#password').val('');
		}$('#remMe').click(function(){
			if($('#remMe').is(':checked')){
				localStorage.userLogin=$('#userLogin').val();
				localStorage.password=$('#password').val();
				localStorage.chkbx=$('#remMe').val();
			}else{
				localStorage.userLogin='';
				localStorage.password='';
				localStorage.chkbx='';
			}
		});
	});
</script>
</body>
</html>