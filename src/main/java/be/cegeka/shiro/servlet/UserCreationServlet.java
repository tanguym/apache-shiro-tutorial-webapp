
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

@WebServlet(name = "UserCreationServlet", urlPatterns = {"admin/userCreation"})
public class UserCreationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = UserManager.addUser(request.getParameter("username"), request.getParameter("password"));
        if (errors.isEmpty()) {
            response.sendRedirect("index.jsp?message=user_created");
        } else {
            response.sendRedirect("addUser.jsp?errors=" + StringUtils.join(errors.iterator(), ","));
        }
    }
}
