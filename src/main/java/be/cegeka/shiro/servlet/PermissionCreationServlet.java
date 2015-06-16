
package be.cegeka.shiro.servlet;

import be.cegeka.shiro.manager.PermissionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PermissionCreationServlet", urlPatterns = {"admin/permissionCreation"})
public class PermissionCreationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = PermissionManager.addPermission(request.getParameter("permissionName"));
        if (error == null) {
            response.sendRedirect("permissions.jsp?message=permission_created");
        } else {
            response.sendRedirect("addPermission.jsp?error=" + error);
        }
    }
}
