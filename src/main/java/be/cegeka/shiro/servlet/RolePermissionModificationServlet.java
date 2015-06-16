
package be.cegeka.shiro.servlet;

import be.cegeka.shiro.manager.RoleManager;
import be.cegeka.shiro.manager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RolePermissionModificationServlet", urlPatterns = {"admin/rolePermissionModification"})
public class RolePermissionModificationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] assignedPermissions = request.getParameterValues("assignedPermission");
        String role = request.getParameter("name");
        RoleManager.updatePermissionsForRole(role, assignedPermissions);
        response.sendRedirect("roles.jsp");
    }
}
