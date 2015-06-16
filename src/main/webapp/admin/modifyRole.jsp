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
    <% pageContext.setAttribute("role", be.cegeka.shiro.manager.RoleManager.getRole(request.getParameter("role"))); %>

    <p><a href="roles.jsp">Return to roles</a></p>
    <h1>Modify Roles</h1>

    <shiro:hasPermission name="roles:write">
    <p>Role : <c:out value="${role.getName()}"/></p>
    <form action="rolePermissionModification" method="POST">
    <p>Rollen</p>
        <% request.setAttribute("permissions",be.cegeka.shiro.manager.PermissionManager.getPermissions()); %>
        <c:forEach items="${permissions}" var="permission">
            <p><input type="checkbox" <c:if test="${role.getPermissions().contains(permission)}">checked </c:if>  name="assignedPermission" value="${permission.getName()}"/> ${permission.getName()}</p>
        </c:forEach>
        <input type="hidden" value="${role.getName()}" name="name"/>
        <p style="width:200px;"><input class="btn btn-lg btn-success btn-block" type="submit" value="update permissions"/></p>
    </form>
    </shiro:hasPermission>
    <shiro:lacksPermission name="roles:write">
        You are not allowed to modify users.
    </shiro:lacksPermission>

</body>
</html>
