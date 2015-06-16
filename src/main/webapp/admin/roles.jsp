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

<jsp:include page="../include/messages.jsp"/>

<%
    request.setAttribute("roles", be.cegeka.shiro.manager.RoleManager.getRoles());
%>

    <p><a href="/admin">Return to admin-page</a></p>
<h1>Roles</h1>
    <shiro:hasPermission name="roles:read">
    <table>
        <tr><th>Role</th><th>Permissions</th><th>Actions</th></tr>
            <c:forEach items="${roles}" var="roleItem">
                <tr><td><c:out value="${roleItem.getName()}"/></td>
                <td>
                    <c:forEach items="${roleItem.getPermissions()}" var="permissionItem">
                        ${permissionItem.getName()}
                    </c:forEach>
                </td><td><a href="modifyRole.jsp?role=${roleItem.getName()}">Modify role</a></td></tr>
            </c:forEach>
    </table>

<p><a class="btn btn-lg btn-success btn-block" href="addRole.jsp">add role</a></p>
    </shiro:hasPermission>
    <shiro:lacksPermission name="roles:read">
        You are not allowed to read roles.
    </shiro:lacksPermission>



</body>
</html>