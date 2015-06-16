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
String errorMessages = request.getParameter("errors");
if (errorMessages != null) {
    out.println("<div class='error'>");
    for (String e : org.apache.shiro.util.StringUtils.split(errorMessages, ',')) {
        out.println(resource.getString(e)+"<br>");
    }
    out.println("</div>");
}
%>