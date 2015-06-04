<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../../common/header.jsp">
	<jsp:param name="active" value="taxreceipts"/>
</jsp:include>

<fmt:setLocale value="pt-BR" />

<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>Id</th>
					<th>CNPJ</th>
					<th>Data</th>
					<th>COO</th>
					<th>Total</th>
					<th>Nota</th>
					<th>Aprova&ccedil;&atilde;o</th>
                    <th>Reprova&ccedil;&atilde;o</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${taxReceipts}" var="item">
					<tr>
						<td>${item.id}</td>
						<td>${item.cnpj}</td>
                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.date}" /></td>
                        <td>${item.coo}</td>
                        <td><fmt:formatNumber value="${item.total}" minFractionDigits="2" type="currency"/></td>
                        <td><a href="${pageContext.request.contextPath}/be/taxreceipts/viewreceipt.action?s3ObjectName=${item.s3Object}" target="_blank">${item.s3Object}</a></td>
						<td>
							<form action="${pageContext.request.contextPath}/be/taxreceipts/approve.action" method="post">
								<input type="hidden" name="id" value="${item.id}"/>
								<button type="submit" class="btn btn-info">Correta</button>
							</form>
						</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/be/taxreceipts/reprove.action" method="post">
                                <input type="hidden" name="id" value="${item.id}"/>
                                <button type="submit" class="btn btn-danger">Errada</button>
                            </form>
                        </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
<jsp:include page="../../common/footer.jsp"/>