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
    <% pageContext.setAttribute("user", be.cegeka.shiro.manager.UserManager.getUser(request.getParameter("user"))); %>

    <h1>Modify User</h1>

    <shiro:hasPermission name="users:write">
    <form action="userModification" method="POST">
        <p>Username : <c:out value="${user.getUsername()}"/></p>
        <p>Password : <input type = "password" name="password"/></p>
        <p style="width:200px;">
            <input class="btn btn-lg btn-success btn-block" type="submit" name="action" value="Update password"/>
            <input class="btn btn-lg btn-success btn-block" type="submit" name="action" value="Unlock account"/>
        </p>
    </form>
    <form action="userRoleModification" method="POST">
    <p>Rollen</p>
        <% request.setAttribute("roles",be.cegeka.shiro.manager.RoleManager.getRoles()); %>
        <c:forEach items="${roles}" var="role">
            <p><input type="checkbox" <c:if test="${user.getRoles().contains(role)}">checked </c:if>  name="assignedRoles" value="${role.getName()}"/> ${role.getName()}</p>
        </c:forEach>
        <input type="hidden" value="${user.getUsername()}" name="username"/>
        <p style="width:200px;"><input class="btn btn-lg btn-success btn-block" type="submit" value="update roles"/></p>
    </form>
    </shiro:hasPermission>
    <shiro:lacksPermission name="users:write">
        You are not allowed to modify users.
    </shiro:lacksPermission>

</body>
</html>
