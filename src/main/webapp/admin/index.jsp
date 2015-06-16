<!DOCTYPE html>
<html>
<head>
    <title>Admin pages</title>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="../static/css/style.css">
        <style>
            body{padding:0 20px;}
        </style>
</head>
<body>

<jsp:include page="../include/messages.jsp"/>

    <p><a href="/">Return to homepage</a></p>
    <h1>Admin page</h1>
<p><a class="btn btn-lg btn-success btn-block" href="users.jsp">Users</a></p>
<p><a class="btn btn-lg btn-success btn-block" href="roles.jsp">Roles</a></p>
<p><a class="btn btn-lg btn-success btn-block" href="permissions.jsp">Permissions</a></p>

</body>
</html>