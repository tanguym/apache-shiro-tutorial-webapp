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
    request.setAttribute("permissions", be.cegeka.shiro.manager.PermissionManager.getPermissions());
%>

    <p><a href="/admin">Return to admin-page</a></p>
<h1>Permissions</h1>
    <shiro:hasPermission name="permissions:read">
    <table>
        <tr><th>Permission</th><th>Actions</th></tr>
            <c:forEach items="${permissions}" var="permissionItem">
                <tr><td><c:out value="${permissionItem.getName()}"/></td><td><a href="permissionDeletion?name=${permissionItem.getName()}">Delete permission</a></td></tr>
            </c:forEach>
    </table>
    </shiro:hasPermission>
    <shiro:lacksPermission name="permissions:read">
        You are not allowed to read permissions.
    </shiro:lacksPermission>

<p><a class="btn btn-lg btn-success btn-block" href="addPermission.jsp">add permission</a></p>

</body>
</html>