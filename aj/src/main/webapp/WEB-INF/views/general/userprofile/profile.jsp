<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<fmt:setBundle basename="messages" />
<title><fmt:message key="text.profile" /> - Contacto jur&iacute;dico</title>

<style>
	hr{margin:0 10px 20px 10px;border-color:#bbb}
	.box-image-profile{top:-20px;-webkit-justify-content:center;justify-content:center;
		-webkit-align-self:center;align-self:center;
	}
	.preview-profile{max-width:320px;max-height:480px;border:1px solid #aaa;
		-webkit-border-radius:7px;-moz-border-radius:7px;border-radius:7px;
		-webkit-box-shadow:0 0 15px #777;box-shadow:0 0 15px #777
	}
	.imgflag{max-width:100%; max-height:34px}
</style>
<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<div class="btn-group pull-right m-t-15">
						<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
						<a href="./editprofile?language=${language}" class="btn btn-default btn-md waves-effect waves-light m-b-30"
							data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200" data-overlayColor="#36404a">
							<fmt:message key="update.profile.title" />
						</a>
						</c:if>
					</div> 
					<h4 class="page-title capitalize"><fmt:message key="text.profile" /></h4>
					<ol class="breadcrumb"></ol>
				</div>
			</div>
			<div class="card-box">
				<form class="form-horizontal">
					<div class="form-group">
						<div class="row flex">
							<div class="col-xs-12 col-md-6 box-image-profile tac">
								<img src="${picprofile}" alt="${firstname.substring(0,1).toUpperCase()}${lastname.substring(0,1).toUpperCase()}"
									onerror="this.src='resources/assets/images/users/user-profile.png'" class="preview-profile" title="${firstname} ${lastname}" />
							</div>
							<div class="col-xs-12 col-md-6">
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.firmname" /></label>
									<div class="col-sm-8">
										<p class="form-control-static">${profile.company_name}</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.firstname" /></label>
									<div class="col-sm-8">
										<p class="form-control-static">${profile.first_name}</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.user.name" /></label>
									<div class="col-sm-8">
										<p class="form-control-static">${profile.username}</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.phone" /></label>
									<div class="col-sm-8">
										<p class="form-control-static">${profile.phone}</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.cellphone" /></label>
									<div class="col-sm-8">
										<p class="form-control-static">${profile.cellphone}</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.user.name" /></label>
									<div class="col-sm-8">
										<p class="form-control-static">${profile.username}</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.email" /></label>
									<div class="col-sm-8">
										<p class="form-control-static" style="overflow:hidden">${profile.email}</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="language" /></label>
									<div class="col-sm-8">
										<c:choose>
											<c:when test="${language=='en'}">
												<img class="imgflag" src="resources/assets/images/Flag-USA.png" alt="English" title="English">&nbsp;
												<span>English</span>
											</c:when>
											<c:otherwise>
												<img class="imgflag" src="resources/assets/images/Flag-Mex.png" alt="Espa&ntilde;ol" title="Español">&nbsp;
												<span>Espa&ntilde;ol</span>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="col-xs-12 col-md-6" style="border-right:1px solid #bbb;padding:0 0 0 20px">
								<div class="col-xs-12 form-group">
									<label class="col-sm-3 control-label"><fmt:message key="text.address" /></label>
									<div class="col-sm-7">
										<p class="form-control-static">${profile.address}</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-3 control-label"><fmt:message key="text.zipcode" /></label>
									<div class="col-sm-7">
										<p class="form-control-static">${profile.zipcode==0?'':profile.zipcode}</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-3 control-label"><fmt:message key="text.country" /></label>
									<div class="col-sm-7">
										<p class="form-control-static">${country}</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-3 control-label"><fmt:message key="text.state" /></label>
									<div class="col-sm-7">
										<p class="form-control-static">${state}</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-3 control-label"><fmt:message key="text.city" /></label>
									<div class="col-sm-7">
										<p class="form-control-static">${city}</p>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-md-6">
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.status" /></label>
									<div class="col-sm-8">
										<p class="form-control-static">
										<c:if test="${profile.status==0}">
											<fmt:message key="status.desactive" />
										</c:if>
										<c:if test="${profile.status==1}">
											<fmt:message key="status.active" />
										</c:if>
										</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.role" /></label>
									<div class="col-sm-8">
										<c:forEach var="r" items="${roles}">
											<c:if test="${profile.role==r.roleid}">
												<p class="form-control-static">${r.rolename}</p>
											</c:if>
										</c:forEach>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.created" /></label>
									<div class="col-sm-8">
										<p class="form-control-static">
										<c:choose>
											<c:when test="${language=='en'}">
												<fmt:formatDate value="${profile.created}" pattern="MM/dd/yyyy HH:mm" />
											</c:when>
											<c:otherwise>
												<fmt:formatDate value="${profile.created}" pattern="dd/MM/yyyy HH:mm" />
											</c:otherwise>
										</c:choose>
										</p>
									</div>
								</div>
								<div class="col-xs-12 form-group">
									<label class="col-sm-4 control-label"><fmt:message key="text.lastupdate" /></label>
									<div class="col-sm-8">
										<p class="form-control-static">
										<c:choose>
											<c:when test="${language=='en'}">
												<fmt:formatDate value="${profile.updated}" pattern="MM/dd/yyyy HH:mm" />
											</c:when>
											<c:otherwise>
												<fmt:formatDate value="${profile.updated}" pattern="dd/MM/yyyy HH:mm" />
											</c:otherwise>
										</c:choose>
										</p>
									</div>
								</div>
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