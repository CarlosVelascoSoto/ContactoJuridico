<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="A fully featured admin ERP">
	<meta name="author" content="Coderthemes">
	<link rel="shortcut icon" href="resources/assets/images/favicon_1.ico">
	<title>Ubold - Responsive Admin Dashboard Template</title>
	<link rel="stylesheet" type="text/css" href="resources/assets/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="resources/assets/css/core.css" />
	<link rel="stylesheet" type="text/css" href="resources/assets/css/components.css" />
	<link rel="stylesheet" type="text/css" href="resources/assets/css/icons.css" />
	<link rel="stylesheet" type="text/css" href="resources/assets/css/pages.css" />
	<link rel="stylesheet" type="text/css" href="resources/assets/css/responsive.css" />

	<!-- HTML5 Shiv and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->
	<script src="resources/assets/js/modernizr.min.js"></script>
</head>
<body>
	<div class="account-pages"></div>
	<div class="clearfix"></div>
	<div class="wrapper-page">
		<div class=" card-box">
		<div class="panel-heading">
			<h3 class="text-center"> Sign In to <strong class="text-custom">UBold</strong></h3>
		</div>
		<div class="panel-body">
			<form class="form-horizontal m-t-20" action="" method="post">
					<div class="form-group ">
						<div class="col-xs-12">
							<div style="display:none;" id="loginError" class="alert alert-danger fade in">
								<a href="#" class="close"	aria-label="close" onclick="toggle();">&times;</a>
								<p>The user or password are incorrect!</p>
							</div>
						</div>
					</div>
					<div class="form-group ">
						<div class="col-xs-12">
							<input class="form-control" name="email" id="email" type="text" required="" placeholder="Username">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<input class="form-control" name="password" id="password" type="password" required="" placeholder="Password" autocomplete="off">
						</div>
					</div>
					<div class="form-group ">
						<div class="col-xs-12">
							<div class="checkbox checkbox-primary">
							<input id="remMe" type="checkbox" name="remMe"	value="remMe_value">
							<label for="checkbox-signup">Remember me</label>
							</div>
						</div>
					</div>
					<div class="form-group text-center m-t-40">
						<div class="col-xs-12">
							<button class="btn btn-pink btn-block text-uppercase waves-effect waves-light" onclick="javascript:return login();" type="button">Log In</button>
						</div>
					</div>
					<div class="form-group m-t-30 m-b-0">
						<div class="col-sm-12">
							<a href="page-recoverpw.html" class="text-dark"><i class="fa fa-lock m-r-5"></i> Forgot your password?</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 text-center">
				<p>Don't have an account? <a href="page-register.html" class="text-primary m-l-5"><b>Sign Up</b></a></p>
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
	<script src="resources/assets/js/modernizr.min.js"></script>
	<script>
		var resizefunc=[];
		$("#email").keypress(function(event){if(event.which==13)login();});
		$("#password").keypress(function(event){if(event.which==13)login();});

		function login(){
			var email=document.getElementById("email").value,password=document.getElementById("password").value,remMe=$('#remMe').is(":checked");
			$.ajax({
				async:true,
				type:"POST",
				url:"./loginProcess",
				data:"email="+email+"&password="+password+"&remMe="+remMe,
				async:false,
				success:function(data,e){
					if(data==='false'||data==='notexists'){
						if($('#remMe').is(':checked')){
							// save username and password
							localStorage.email=$('#email').val();
							localStorage.password=$('#password').val();
							localStorage.chkbx=$('#remMe').val();
						}else{
							localStorage.email='';
							localStorage.password='';
							localStorage.chkbx='';
						}$('#loginError').css('display','block');
						return false;
					}else{
						if($('#remMe').is(':checked')){
							// save username and password
							localStorage.email=$('#email').val();
							localStorage.password=$('#password').val();
							localStorage.chkbx=$('#remMe').val();
						}else{
							localStorage.email='';
							localStorage.password='';
							localStorage.chkbx='';
						}window.location.href="./home";
					}
				},error:function(e){alert("ERROR: "+ e);}
			});
		}

		function toggle(){$('#loginError').css('display','none');}

		var varOne='${cookie.email.value}',varTwo='${cookie.passWord.value}';

		if(varOne!=null && varOne.trim()!="null" && varOne.trim()!=""){
			if(varTwo!=null && varTwo.trim()!="null" && varTwo.trim()!=""){
				document.getElementById("email").value=varOne;
				document.getElementById("password").value=varTwo;
				$('#remMe').prop("checked", true);
				login();
			}
		}
	
		$(function(){
			if(localStorage.chkbx && localStorage.chkbx != ''){
				$('#remMe').attr('checked', 'checked');
				$('#email').val(localStorage.email);
				$('#password').val(localStorage.password);
			}else{
				$('#remMe').removeAttr('checked');
				$('#email').val('');
				$('#password').val('');
			}$('#remMe').click(function(){
				if($('#remMe').is(':checked')){
					// save username and password
					localStorage.email=$('#email').val();
					localStorage.password=$('#password').val();
					localStorage.chkbx=$('#remMe').val();
				}else{
					localStorage.email='';
					localStorage.password='';
					localStorage.chkbx='';
				}
			}); 
		});
	</script>
</body>
</html>