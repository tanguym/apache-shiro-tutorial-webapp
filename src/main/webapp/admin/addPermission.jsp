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

    <p><a href="/admin/permissions.jsp">Return to permissions</a></p>
    <h1>Add Permission</h1>

    <shiro:hasPermission name="permissions:write">
    <form action="permissionCreation" method="POST">
        <p>Name : <input type = "text" name="permissionName"/></p>
            <p style="width:200px;"><input class="btn btn-lg btn-success btn-block" type="submit" name="action" value="Create permission"/>
        </p>
    </form>
    </shiro:hasPermission>
    <shiro:lacksPermission name="permissions:write">
        You are not allowed to modify permissions.
    </shiro:lacksPermission>

</body>
</html>
