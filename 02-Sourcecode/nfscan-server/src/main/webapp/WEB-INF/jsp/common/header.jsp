<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-br">
<jsp:include page="taghead.jsp"/>
<body>

<script type="text/javascript">var ctx = "${pageContext.request.contextPath}"</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/be/js/lib/jquery/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/be/js/lib/bootstrap/bootstrap.min.js"></script>

<div class="container">
    <div class="header">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/be/img/logoadm.png" alt="Área Administrativa"/>
        </div>
        <jsp:include page="navigation.jsp">
            <jsp:param name="active" value="${param.active}"></jsp:param>
        </jsp:include>
    </div>