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
<p><a href="/admin/users.jsp">Return to users</a></p>
    <h1>Add User</h1>

    <shiro:hasPermission name="users:write">
    <form action="userCreation" method="POST">
        <p>Username : <input type = "text" name="username"/></p>
        <p>Password : <input type = "password" name="password"/></p>
        <p style="width:200px;">
            <input class="btn btn-lg btn-success btn-block" type="submit" name="action" value="Create user"/>
        </p>
    </form>
    </shiro:hasPermission>
    <shiro:lacksPermission name="users:write">
        You are not allowed to modify users.
    </shiro:lacksPermission>

</body>
</html>
