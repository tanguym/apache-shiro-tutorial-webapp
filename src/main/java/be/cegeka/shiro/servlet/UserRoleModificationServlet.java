
package be.cegeka.shiro.servlet;

import be.cegeka.shiro.manager.UserManager;
import org.apache.shiro.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserRoleModificationServlet", urlPatterns = {"admin/userRoleModification"})
public class UserRoleModificationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] assignedRoles = request.getParameterValues("assignedRoles");
        String username = request.getParameter("username");
        UserManager.updateRolesForUser(username, assignedRoles);
        response.sendRedirect("users.jsp");
    }
}
