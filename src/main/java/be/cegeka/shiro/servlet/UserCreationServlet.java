
package be.cegeka.shiro.servlet;

import be.cegeka.shiro.manager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserCreationServlet", urlPatterns = {"admin/userCreation"})
public class UserCreationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = UserManager.addUser(request.getParameter("username"), request.getParameter("password"));
        if (error == null) {
            response.sendRedirect("index.jsp?message=user_created");
        } else {
            response.sendRedirect("addUser.jsp?error=" + error);
        }
    }
}
