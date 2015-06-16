
package be.cegeka.shiro.servlet;

import be.cegeka.shiro.manager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserModificationServlet", urlPatterns = {"admin/userModification"})
public class UserModificationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("Update password".equals(request.getParameter("action"))) {
            UserManager.changePassword(request.getParameter("username"), request.getParameter("password"));
            response.sendRedirect("index.jsp?message=password_changed");
        } else if ("Unlock account".equals(request.getParameter("action"))) {
            UserManager.unlockAccount(request.getParameter("username"));
            response.sendRedirect("index.jsp?message=user_reset");
        } else {
            response.sendRedirect("modifyUser.jsp");
        }
    }
}
