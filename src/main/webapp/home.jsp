<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  ~ Copyright (c) 2013 Les Hazlewood and contributors
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>
<jsp:include page="include.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>Apache Shiro Tutorial Webapp</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Add some nice styling and functionality.  We'll just use Twitter Bootstrap -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="static/css/style.css">
    <style>
        body{padding:0 20px;}
    </style>
</head>
<body>
    <jsp:include page="include/messages.jsp"/>

    <h1>Apache Shiro Tutorial Webapp</h1>

    <p>
        ( <shiro:user><a href="<c:url value="/logout"/>">Log out</a></shiro:user>
        <shiro:guest><a href="<c:url value="/login.jsp"/>">Log in</a></shiro:guest> )
    </p>

    <p>Welcome to the Apache Shiro Tutorial Webapp.  This page represents the home page of any web application.</p>

    <shiro:authenticated><p>Visit your <a href="<c:url value="/account"/>">account page</a>.</p></shiro:authenticated>
    <shiro:notAuthenticated><p>If you want to access the authenticated-only <a href="<c:url value="/account"/>">account page</a>,
        you will need to log-in first.</p></shiro:notAuthenticated>

    <shiro:hasPermission name="users:write">
        <a href="admin/index.jsp">Go to the admin panel</a>
    </shiro:hasPermission>

    <h2>Roles</h2>

    <p>Here are the roles you have and don't have. Log out and log back in under different user
        accounts to see different roles.</p>

    <h3>Roles you have:</h3>

    <p>
        <shiro:hasRole name="admin">admin<br/></shiro:hasRole>
        <shiro:hasRole name="reading">reading<br/></shiro:hasRole>
    </p>

    <h3>Roles you DON'T have:</h3>

    <p>
    <%
        pageContext.setAttribute("roles", be.cegeka.shiro.manager.RoleManager.getRoles());
    %>
        <c:forEach items="${roles}" var="role">
            <shiro:lacksRole name="${role.getName()}">${role.getName()}<br/></shiro:lacksRole>
        </c:forEach>
    </p>

    <shiro:hasPermission name="read">WOOT it works</shiro:hasPermission>

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
