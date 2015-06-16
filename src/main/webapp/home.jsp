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
        p{margin:0px;}
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

    <h2>What did we add to Shiro?</h2>
    <ul>
    <li>Database schema</li>
    <li>User, role en permission management for admins</li>
    <li>Change password functionality</li>
    <li>Account lockout on too many login-attempts</li>
    <li>Audit logging on login</li>
    <li>Session timeout (configuration)</li>
    <li>Password encryption (configuration)</li>
    <li>Password expiration</li>
    <li>Password policies</li>
    </ul>

    <h2>Roles</h2>

    <p>Here are the roles you have and don't have. Log out and log back in under different user
        accounts to see different roles.</p>

    <%
        pageContext.setAttribute("roles", be.cegeka.shiro.manager.RoleManager.getRoles());
    %>
    <h3>Roles you have:</h3>
        <c:forEach items="${roles}" var="role">
            <shiro:hasRole name="${role.getName()}"><p style="font-weight:bold;">${role.getName()}</p>
                <div style="margin-left:20px;">
                    <p>Permissions in this role : </p>
                    <c:forEach items="${role.getPermissions()}" var="permission">
                        <p>${permission.getName()}</p>
                    </c:forEach>
                </div>
            </shiro:hasRole>
        </c:forEach>

    <h3>Roles you DON'T have:</h3>

    <c:forEach items="${roles}" var="role">
        <shiro:lacksRole name="${role.getName()}"><p style="font-weight:bold;">${role.getName()}</p>
            <div style="margin-left:20px;">
                <p>Permissions in this role : </p>
                <c:forEach items="${role.getPermissions()}" var="permission">
                    <p>${permission.getName()}</p>
                </c:forEach>
            </div>
        </shiro:lacksRole>
    </c:forEach>

    <shiro:hasPermission name="read">WOOT it works, since everyone has the read permission.</shiro:hasPermission>

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
