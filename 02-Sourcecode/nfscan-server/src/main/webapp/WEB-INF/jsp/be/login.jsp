<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
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
            <input class="usuario" id="login" type="text" name="j_username" placeholder="Usuario">
            <input class="password" id="password" type="password" name="j_password" placeholder="Senha">
            <span class="message">${message}</span>
            <button class="btn btn-primary button" type="submit">Entrar</button>
        </form>
    </div>
</div>

</body>
</html>
