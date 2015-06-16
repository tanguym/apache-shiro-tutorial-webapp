
package be.cegeka.shiro.servlet;

import be.cegeka.shiro.manager.PermissionManager;
import be.cegeka.shiro.manager.RoleManager;
import be.cegeka.shiro.realm.PermissionRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PermissionDeletionServlet", urlPatterns = {"admin/permissionDeletion"})
public class PermissionDeletionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PermissionManager.deletePermission(request.getParameter("name"));
        response.sendRedirect("permissions.jsp");
    }
}
