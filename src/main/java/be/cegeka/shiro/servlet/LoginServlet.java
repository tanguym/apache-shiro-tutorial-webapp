package be.cegeka.shiro.servlet;

import be.cegeka.shiro.realm.validation.PasswordExpirationModule;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    public static final String NO_ACCOUNT_FOUND_FOR_USER_EXCEPTION = "No account found for user";
    public static final String PASSWORD_WRONG_EXCEPTION = "Submitted credentials for token";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthenticationToken token = new UsernamePasswordToken(request.getParameter("username"), request.getParameter("password"));
        try {
            SecurityUtils.getSecurityManager().login(SecurityUtils.getSubject(), token);
        } catch (AuthenticationException e) {
            if (PasswordExpirationModule.ERROR_PASSWORD_EXPIRED.equals(e.getMessage())) {
                response.sendRedirect("passwordExpired.jsp");
            } else {
                if (e.getMessage().contains(NO_ACCOUNT_FOUND_FOR_USER_EXCEPTION)
                        || e.getMessage().contains(PASSWORD_WRONG_EXCEPTION)) {
                    response.sendRedirect("login.jsp?error=user.password.wrong");
                }
            }
        }
        response.sendRedirect("home.jsp");
    }

}

