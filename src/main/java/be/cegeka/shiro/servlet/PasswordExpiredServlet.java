package be.cegeka.shiro.servlet;

import be.cegeka.shiro.manager.UserManager;
import be.cegeka.shiro.realm.JdbcCustomizedRealm;
import be.cegeka.shiro.realm.RealmLocator;
import be.cegeka.shiro.realm.validation.PasswordExpirationModule;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PasswordExpiredServlet", urlPatterns = {"/passwordExpired"})
public class PasswordExpiredServlet extends HttpServlet {

    public static final String OLD_PASSWORD_KEY = "old_password";
    public static final String PASSWORD_KEY = "password";
    public static final String CONFIRM_PASSWORD_KEY = "confirm_password";
    public static final String USER_KEY = "username";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        AuthenticationToken token = new UsernamePasswordToken(request.getParameter(USER_KEY), request.getParameter(OLD_PASSWORD_KEY));
        try {
            realm.removeValidationModule(new PasswordExpirationModule().getName());
            SecurityUtils.getSecurityManager().login(SecurityUtils.getSubject(), token);
            List<String> errors = UserManager.changePassword(request.getParameter(USER_KEY),
                    request.getParameter(PASSWORD_KEY),
                    request.getParameter(CONFIRM_PASSWORD_KEY),
                    request.getParameter(OLD_PASSWORD_KEY)
            );
            if (errors.isEmpty()) {
                response.sendRedirect("home.jsp?message=password_changed");
            } else {
                response.sendRedirect("passwordExpired.jsp?errors=" + StringUtils.join(errors.iterator(), ","));
            }
        } catch (AuthenticationException e) {
            response.sendRedirect("passwordExpired.jsp?error=error.login");
        } finally {
            realm.addValidationModule(new PasswordExpirationModule());
        }
    }

}

