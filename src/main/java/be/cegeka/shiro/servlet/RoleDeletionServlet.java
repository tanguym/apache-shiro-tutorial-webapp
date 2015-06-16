
package be.cegeka.shiro.servlet;

import be.cegeka.shiro.manager.RoleManager;
import be.cegeka.shiro.manager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RoleDeletionServlet", urlPatterns = {"admin/roleDeletion"})
public class RoleDeletionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RoleManager.deleteRole(request.getParameter("name"));
        response.sendRedirect("roles.jsp");
    }
}
