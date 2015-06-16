
package be.cegeka.shiro.servlet;

import be.cegeka.shiro.manager.RoleManager;
import be.cegeka.shiro.manager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RoleCreationServlet", urlPatterns = {"admin/roleCreation"})
public class RoleCreationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = RoleManager.addRole(request.getParameter("roleName"));
        if (error == null) {
            response.sendRedirect("index.jsp?message=role_created");
        } else {
            response.sendRedirect("addRole.jsp?error=" + error);
        }
    }
}
