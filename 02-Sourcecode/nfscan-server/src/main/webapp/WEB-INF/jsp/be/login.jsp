<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="pt-br">
<jsp:include page="../common/taghead.jsp"/>
<body>
<div class="container">
    <div class="login-form">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/be/img/logoadm.png" alt="Área Administrativa"/>
        </div>

        <form class="form" method="post"
              action="${pageContext.request.contextPath}/be/j_spring_security_check">

            <c:if test="${not empty message}">
                <span class="info_message">${message}</span>
            </c:if>

            <input class="usuario" id="username" type="text" name="username" placeholder="Usuario">
            <input class="password" id="password" type="password" name="password" placeholder="Senha">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <c:if test="${not empty error}">
                <span class="error_message">${error}</span>
            </c:if>

            <button class="btn btn-primary button" type="submit">Entrar</button>
        </form>
    </div>
</div>

</body>
</html>