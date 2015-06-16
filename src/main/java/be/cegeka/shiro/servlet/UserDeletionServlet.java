
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

@WebServlet(name = "UserDeletionServlet", urlPatterns = {"admin/userDeletion"})
public class UserDeletionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManager.deleteUser(request.getParameter("username"));
        response.sendRedirect("users.jsp");
    }
}
