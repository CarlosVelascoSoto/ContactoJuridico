<%@page import="com.aj.utility.UserDTO"%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%	UserDTO userDto=(UserDTO) request.getSession().getAttribute("UserDTO");
	int role=userDto.getRole();
%>
<!-- i18n... -->
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="messages" />
<!-- ...i18n -->

<c:set var="aPermiso" scope="page" value="1" />
<c:set var="ePermiso" scope="page" value="2" />
<c:set var="dPermiso" scope="page" value="3" />
<c:set var="rPermiso" scope="page" value="4" />
<c:set var="menuid" scope="page" value="${menuid}" />
<style>
	#wrapper.enlarged .left.side-menu #sidebar-menu ul > li:hover > a{width:375px}/*270px*/
	#wrapper.enlarged .left.side-menu #sidebar-menu ul > li:hover > ul{width:305px}/*200px*/
	#wrapper.enlarged .left.side-menu #sidebar-menu ul > li:hover > ul a{width:100%}
</style>

${blockhtml}

<!-- Left Sidebar Start -->
<div class="left side-menu">
	<div class="sidebar-inner slimscrollleft">
		<div id="sidebar-menu"><!--- Divider -->
			<ul>
			<!-- MENU -->
			<c:forEach var="item" items="${pMenuLat}">
				<c:if test="${item.menuparentid == 0}">
					<li class="has_sub fs14">					
						<c:forEach var="permiso" items="${dicpermisos}">
							<c:set var="permisoSesion" scope="page" value="${item.menuid}_${permiso.privilegesid}" />
							<c:if test='${fn:contains(arrayPermisos, permisoSesion ) && permiso.privilegesid==rPermiso}'>
							
							<c:choose>
							  <c:when test="${not empty item.link}">
								<a href="${pageContext.request.contextPath}/${item.link}?language=${language}" class="waves-effect" title='${item.menu}'>
									<i class="${item.icon}"></i><span>${item.menu}</span><span class="menu-arrow"></span>
								</a>
							  </c:when>
							  <c:otherwise>
								<a href="#" class="waves-effect" title='${item.menu}'>
									<i class="${item.icon}"></i><span>${item.menu}</span><span class="menu-arrow"></span>
								</a>
							  </c:otherwise>
							</c:choose>
							</c:if>
						</c:forEach>
						
						<c:set var="count" value="0" scope="page" />
						<c:forEach var="itemv2" items="${pMenuLat}">
							<c:if test="${itemv2.menuparentid== item.menuid}">
								<c:set var="count" value="${count + 1}" scope="page"/>
							</c:if>
						</c:forEach>	
						
						<c:if test="${count gt 0}">	
							<ul>
								<c:forEach var="itemv2" items="${pMenuLat}">
									<c:if test="${itemv2.menuparentid== item.menuid}">
									<c:forEach var="permiso" items="${dicpermisos}">
									<c:set var="permisoSesion" scope="page" value=".${itemv2.menuid}_${permiso.privilegesid}" />
									<c:if test='${fn:contains(arrayPermisos,permisoSesion) && permiso.privilegesid==rPermiso}'>
									<li>
										<a href="${pageContext.request.contextPath}/${itemv2.link}?language=${language}" class="waves-effect" title='${itemv2.menu}'>
											<i class="${itemv2.icon}"></i><span>${itemv2.menu}</span><span class="menu-arrow"></span>
										</a>							
									</li>
									</c:if>
									</c:forEach>
									</c:if>
								</c:forEach>
								
							</ul>
						</c:if>
						
						
					</li>
				</c:if>
			</c:forEach>
			</ul>
			<div class="clearfix"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</div><!-- Left Sidebar End -->