<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="aPermiso" scope="page" value="1" />
<c:set var="ePermiso" scope="page" value="2" />
<c:set var="dPermiso" scope="page" value="3" />
<c:set var="rPermiso" scope="page" value="4" />
<c:set var="readPermiso" scope="page" value="4" />
<c:set var="headerid" scope="page" value="${headerid}" />
<style>
	.catalog_sub{margin:5px 0;color:#333}
	.hovpoint{width:100%}
	.hovpoint a{color:#777 !important}
	.catalog-sub-menu li{margin:5px 0;padding:0 0 0 25px}
	.catalog-sub-menu li a{display:block;color:#333}
	.catalog_sub > span,.catalog-sub-menu li{width:100%;padding:5px 0 5px 20px;color:#777 !important}
	.catalog_sub > span:hover,.catalog-sub-menu li:hover,.hovpoint:hover,.catalog-sub-menu li a:hover{background-color:#f5f5f5;color:#333 !important}
	.catalog-sub-menu{
		position:absolute;width:110%;margin:5px 0;padding:0;right:-30%;
		-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;
		-webkit-box-shadow:0 2px 3px #d0d0d0;box-shadow:0 2px 3px #d0d0d0;
		list-style-type:none;line-height:30px;background-color:#fff
	}
</style>

<div id="wrapper">
	<div class="topbar">
		<div class="topbar-left">
			<div class="text-center" style="background-color:#212121">
				 <a href="./home?language=${language}" class="logo">
				 	<img class="icon-home-logo icon-c-logo"
				 		src="resources/assets/images/logo-white-small.png"
				 		alt="Contacto jur\u00eddico">
				 	<span><img class="icon-home-logo icon-c-logo largelogo dnone"
				 		src="resources/assets/images/logo-white-large.png"
				 		style="display:block" alt="Contacto jur\u00eddico"></span>
				 </a>
			</div>
		</div>
		<div class="navbar navbar-default -trial-nav" role="navigation">
			<div class="container">
				<div class="">
					<div class="pull-left">
						<span class="cont-hammenu open-left waves-effect waves-light">
							<button type="button" class="hamburger-icon"></button>
						</span>
					</div>
					<ul class="nav navbar-nav navbar-right pull-right">
						<li class="dropdown top-menu-item-xs">
							<a href="./home?language=${language}"
								class="dropdown-toggle profile waves-effect waves-light"
								title="<fmt:message key='text.start'/>">
							<span id="bellNotify"><i class="material-icons">notifications_none</i></span>
							</a>
						</li>
						<c:if test="${homeNotificationsVis==1}">
						<!-- li class="dropdown top-menu-item-xs">
							<a href="#" data-target="#" class="dropdown-toggle waves-effect waves-light"
								data-toggle="dropdown" aria-expanded="true">
								<i class="icon-bell"></i> <span class="badge badge-xs badge-danger">3</span>
							</a>
							<ul class="dropdown-menu dropdown-menu-lg">
								<li class="notifi-title">
									<span class="label label-default pull-right">
										<fmt:message key="home.notification.new.qty" /> 3
									</span>
									<fmt:message key="home.notification.title" />
								</li>
								<li class="list-group slimscroll-noti notification-list">
									<a href="javascript:void(0);" class="list-group-item">
										<div class="media">
											<div class="pull-left p-r-10">
												<em class="fa fa-diamond noti-primary"></em>
											</div>
											<div class="media-body">
												<h5 class="media-heading">
													<fmt:message key="home.notification.order" />
												</h5>
												<p class="m-0">
													<small><fmt:message key="home.notification.title.small.msg" /></small>
												</p>
											</div>
										</div>
									</a>
									<a href="javascript:void(0);" class="list-group-item">
										<div class="media">
											<div class="pull-left p-r-10">
												<em class="fa fa-cog noti-warning"></em>
											</div>
											<div class="media-body">
												<h5 class="media-heading"><fmt:message key="home.notification.settings" /></h5>
												<p class="m-0">
													<small><fmt:message key="home.notification.settings.small.msg" /></small>
												</p>
											</div>
										</div>
									</a>
									<a href="javascript:void(0);" class="list-group-item">
										<div class="media">
											<div class="pull-left p-r-10">
												<em class="fa fa-bell-o noti-custom"></em>
											</div>
											<div class="media-body">
												<h5 class="media-heading"><fmt:message key="home.notification.updates" /></h5>
												<p class="m-0">
													<small>
														<span class="text-primary font-600">2</span>
														<fmt:message key="home.notification.updates.small.msg" />
													</small>
												</p>
											</div>
										</div>
									</a>
									<a href="javascript:void(0);" class="list-group-item">
										<div class="media">
											<div class="pull-left p-r-10">
												<em class="fa fa-user-plus noti-pink"></em>
											</div>
											<div class="media-body">
												<h5 class="media-heading"><fmt:message key="home.notification.new.user" /></h5>
												<p class="m-0">
													<small><fmt:message key="home.notification.unread.msg" /></small>
												</p>
											</div>
										</div>
									</a>
									<a href="javascript:void(0);" class="list-group-item">
										<div class="media">
											<div class="pull-left p-r-10">
												<em class="fa fa-diamond noti-primary"></em>
											</div>
											<div class="media-body">
												<h5 class="media-heading"><fmt:message key="home.notification.order" /></h5>
												<p class="m-0">
													<small><fmt:message key="home.notification.order" /></small>
												</p>
											</div>
										</div>
									</a>
									<a href="javascript:void(0);" class="list-group-item">
										<div class="media">
											<div class="pull-left p-r-10">
												<em class="fa fa-cog noti-warning"></em>
											</div>
											<div class="media-body">
												<h5 class="media-heading"><fmt:message key="home.notification.list.settings" /></h5>
												<p class="m-0">
													<small>
														<fmt:message key="home.notification.list.settings.small.msg" />
													</small>
												</p>
											</div>
										</div>
									</a>
								</li>
							</ul>
						</li-->
						</c:if>
<!-- MENU -->
						<!-- chat menu
						<li class="hidden-xs">
							<a href="#" class="right-bar-toggle waves-effect waves-light"><i class="ti-comment-alt"></i></a>
						</li>
						-->
<!-- CONFIGURACIÓN -->
						<c:set var="count" value="0" scope="page" />
						<c:forEach var="item" items="${pMenuSup}">
							<c:forEach var="permiso" items="${dicpermisos}">
								<c:set var="permisoSesion" scope="page" value="${item.menuid}_${permiso.privilegesid}" />
								<c:if test='${fn:contains(arrayPermisos, permisoSesion ) && permiso.privilegesid==rPermiso}'>
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:if>
							</c:forEach>	
						</c:forEach>
							
						<c:if test="${count gt 0}">
							<li class="dropdown top-menu-item-xs">
								<a href="" class="dropdown-toggle profile waves-effect waves-light" data-toggle="dropdown" aria-expanded="true" title=''>
									<i class="ti-settings m-r-10 text-custom"></i>
								</a>

								<ul class="dropdown-menu">
									<c:forEach var="item" items="${pMenuSup}">
										<c:forEach var="permiso" items="${dicpermisos}">
										<c:set var="permisoSesion" scope="page" value="${item.menuid}_${permiso.privilegesid}" />
										<c:if test='${fn:contains(arrayPermisos, permisoSesion ) && permiso.privilegesid==rPermiso}'>
										<li class="hovpoint">
											<a href="./${item.link}?language=${language}" title="${item.menu}">
												<i class="${item.icon} m-r-10 text-custom"></i>
												${item.menu}
											</a>
										</li>
										<li class="divider"></li>
										</c:if>
										</c:forEach>
									</c:forEach>
								</ul>

							</li>
						</c:if>

						<li class="dropdown top-menu-item-xs">
							<a href="" class="dropdown-toggle profile waves-effect waves-light" data-toggle="dropdown" aria-expanded="true">
								<img src="${picprofile}" alt="${firstname.substring(0,1).toUpperCase()}${lastname.substring(0,1).toUpperCase()}"
									onerror="this.src='resources/assets/images/users/user-profile.png'" class="img-circle" title="${firstname} ${lastname}" />
								<i class="material-icons fs16" style="position:relative;top:5px;color:#555">&#xe313;</i>
							</a>
							<ul class="dropdown-menu">
								<c:forEach var="item" items="${pMenuUser}">
									<c:forEach var="permiso" items="${dicpermisos}">
										<c:set var="permisoSesion" scope="page" value="${item.menuid}_${permiso.privilegesid}" />
										<c:if test='${fn:contains(arrayPermisos, permisoSesion ) && permiso.privilegesid==rPermiso}'>
										<li class="hovpoint">
											<a href="./${item.link}?language=${language}" title="${item.menu} />">
												<i class="${item.icon} m-r-10 text-custom"></i>
												${item.menu}
											</a>
										</li>
										<li class="divider"></li>
										</c:if>
									</c:forEach>
								</c:forEach>
								<c:if test="${profileVis==1}">
								<li>
									<a href="./profile?language=${language}">
										<i class="ti-user m-r-10 text-custom"></i>
										<fmt:message key="text.profile" />
									</a>
								</li>
								</c:if>
								<li>
									<a href="./logout?language=${language}">
										<i class="ti-power-off m-r-10 text-danger"></i>
										<fmt:message key="home.notification.profile.logout" />
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<!--script>var ctx="${pageContext.request.contextPath}"</script-->