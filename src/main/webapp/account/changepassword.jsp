<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include.jsp"/>
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

    <h1>Change your password</h1>
    <p><a href="<c:url value="../account"/>">Return to the account page.</a></p>

    <p>Welcome to the change password page.</p>

    <p>Enter your current password and new password below to change your password.</p>
    <form action="/account/ChangeMyPassword" method="POST">
        <p>Your current password : <input type = "password" name="old_password"/></p>
        <p>New password : <input type = "password" name="password"/></p>
        <p>Re-enter your new password : <input type = "password" name="confirm_password"/></p>
        <p style="width:200px;"><input class="btn btn-lg btn-success btn-block" type="submit" value="Send!"/></p>
    </form>

    <p><c:out value="${error}"/></p>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</body>
</html>
