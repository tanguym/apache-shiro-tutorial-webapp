<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
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
    <h1>Users</h1>

    <shiro:hasPermission name="users:read">
        <table>
        <tr><th>User</th><th>Locked</th><th>Expired</th><th>Roles</th></tr>
        <% request.setAttribute("users",be.cegeka.shiro.manager.UserManager.getUsers()); %>
        <c:forEach items="${users}" var="user">
            <tr><td>${user.getUsername()}</td><td>${user.isAccountLocked()}</td><td>${user.needsPasswordChange()}</td><td>
                <c:forEach items="${user.getRoles()}" var="role">${role.getName()} </c:forEach>
            </td></tr>
        </c:forEach>
        </table>
    </shiro:hasPermission>
    <shiro:lacksPermission name="users:read">
        You are not allowed to read users.
    </shiro:lacksPermission>

</body>
</html>
