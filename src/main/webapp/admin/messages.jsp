<%@ page import = "java.util.ResourceBundle" %><%
ResourceBundle resource = ResourceBundle.getBundle("messages");
String message = request.getParameter("message");
if (message != null) {
    out.println("<div class='message'>"+resource.getString(message)+"</div>");
}
String error = request.getParameter("error");
if (error != null) {
    out.println("<div class='error'>"+resource.getString(error)+"</div>");
}
%>