<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="navbar">
    <div class="navbar-inner">
        <ul class="nav">

            <li class="dropdown  ${param.active eq 'taxreceipts' ? 'active' : '' }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Notas Fiscais</a>
                <ul class="dropdown-menu navbar">
                    <li><a href="${pageContext.request.contextPath}/be/taxreceipts/all.action">Listar</a></li>
                </ul>
            </li>
        </ul>
        <ul class="nav pull-right">
            <li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><sec:authentication property="principal.username" /><b class="caret"></b></a>
                <ul class="dropdown-menu navbar">
                    <li>
                        <a href="${pageContext.request.contextPath}/j_spring_security_logout"><i
                                class="icon-off"></i> Sair</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>