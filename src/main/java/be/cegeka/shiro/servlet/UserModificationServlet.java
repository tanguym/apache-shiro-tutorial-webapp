
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

@WebServlet(name = "UserModificationServlet", urlPatterns = {"admin/userModification"})
public class UserModificationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("Update password".equals(request.getParameter("action"))) {
            List<String> errors = UserManager.changePassword(request.getParameter("username"), request.getParameter("password"));
            if (errors.isEmpty()) {
                response.sendRedirect("index.jsp?message=password_changed");
            } else {
                response.sendRedirect("modifyUser.jsp?errors=" + StringUtils.join(errors.iterator(), ","));
            }
        } else if ("Unlock account".equals(request.getParameter("action"))) {
            UserManager.unlockAccount(request.getParameter("username"));
            response.sendRedirect("index.jsp?message=user_reset");
        } else {
            response.sendRedirect("modifyUser.jsp");
        }
    }
}
