<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="../static/css/style.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
      <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
      <script>
      $(function() {
        $(".roleTooltip").tooltip();
      });
      </script>
    <style>
        body{padding:0 20px;}
    </style>
</head>
<body>
    <jsp:include page="../include/messages.jsp"/>
    <p><a href="/admin">Return to admin-page</a></p>
    <h1>Users</h1>

    <shiro:hasPermission name="users:read">
        <table>
        <tr><th>User</th><th>Locked</th><th>Expired</th><th>Roles</th><th>Actions</th></tr>
        <% request.setAttribute("users",be.cegeka.shiro.manager.UserManager.getUsers()); %>
        <c:forEach items="${users}" var="user">
            <tr><td>${user.getUsername()}</td><td>${user.isAccountLocked()}</td><td>${user.needsPasswordChange()}</td><td>
                <c:forEach items="${user.getRoles()}" var="role"><span class="roleTooltip" title="This role has following permissions : ${role.getPermissionsForGUI()}">${role.getName()}</span> </c:forEach>
            </td><td><a href="modifyUser.jsp?user=${user.getUsername()}">modify user</a><br><a href="userDeletion?username=${user.getUsername()}">delete user</a><br></td></tr>
        </c:forEach>
        </table>
<p><a class="btn btn-lg btn-success btn-block" href="addUser.jsp">add user</a></p>
    </shiro:hasPermission>
    <shiro:lacksPermission name="users:read">
        You are not allowed to read users.
    </shiro:lacksPermission>

</body>
</html>
