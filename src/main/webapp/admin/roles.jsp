<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin pages</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="../static/css/style.css">
        <style>
            body{padding:0 20px;}
        </style>
</head>
<body>

<jsp:include page="messages.jsp"/>

<%
    request.setAttribute("roles", be.cegeka.shiro.manager.RoleManager.getRoles());
%>

<h1>Roles</h1>
    <shiro:hasPermission name="users:read">
    <table>
        <tr><th>Role</th><th>Permissions</th></tr>
            <c:forEach items="${roles}" var="roleItem">
                <tr><td><c:out value="${roleItem.getName()}"/></td><td></td></tr>
            </c:forEach>
    </table>
    </shiro:hasPermission>
    <shiro:lacksPermission name="users:read">
        You are not allowed to read roles.
    </shiro:lacksPermission>

<a href="addRole.jsp">add role</a><br>

</body>
</html>