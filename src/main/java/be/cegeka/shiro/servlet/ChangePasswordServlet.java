package be.cegeka.shiro.servlet;

import be.cegeka.shiro.manager.UserManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/account/ChangeMyPassword"})
public class ChangePasswordServlet extends HttpServlet {

    public static final String OLD_PASSWORD_KEY = "old_password";
    public static final String PASSWORD_KEY = "password";
    public static final String CONFIRM_PASSWORD_KEY = "confirm_password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = UserManager.changePassword((String)SecurityUtils.getSubject().getPrincipal(),
                request.getParameter(PASSWORD_KEY),
                request.getParameter(CONFIRM_PASSWORD_KEY),
                request.getParameter(OLD_PASSWORD_KEY)
                );
        if (errors.isEmpty()) {
            response.sendRedirect("index.jsp?message=user_created");
        } else {
            response.sendRedirect("changepassword.jsp?errors=" + StringUtils.join(errors.iterator(), ","));
        }
    }

}

