package be.cegeka.shiro.servlet;

import be.cegeka.shiro.realm.JdbcCustomizedRealm;
import be.cegeka.shiro.realm.RealmLocator;
import be.cegeka.shiro.validation.PasswordPolicyEnforcer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/account/ChangeMyPassword"})
public class ChangePasswordServlet extends HttpServlet {

    public static final String USERNAME_KEY = "USERNAME";
    public static final String OLD_PASSWORD_KEY = "OLD_PASSWORD";
    public static final String PASSWORD_KEY = "PASSWORD";
    public static final String CONFIRM_PASSWORD_KEY = "CONFIRM_PASSWORD";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> parametersMap = getParametersMap(request);
        List<String> errors = PasswordPolicyEnforcer.validatePassword(parametersMap.get(PASSWORD_KEY), parametersMap.get(CONFIRM_PASSWORD_KEY));
        if (errors.isEmpty()) {
            try {
                doPasswordChange(parametersMap);
            }
            catch (AuthenticationException e) {
                response.sendRedirect(getErrorRedirectPage() + "?error=" + e.getMessage() );
            }
            response.sendRedirect(getSuccessRedirectPage() + "?message=");
        } else {
            String errorsParameter = StringUtils.join(errors.iterator(), ",");
            response.sendRedirect(getErrorRedirectPage() + "?errors=" + errorsParameter);
        }
    }

    protected void doPasswordChange(Map<String, String> parametersMap) {
        UsernamePasswordToken currentCredentials = new UsernamePasswordToken(parametersMap.get(USERNAME_KEY), parametersMap.get(OLD_PASSWORD_KEY));
        String newPassword = parametersMap.get(PASSWORD_KEY);
        RealmLocator.locate(JdbcCustomizedRealm.class).updatePassword(currentCredentials, newPassword);
    }

    protected String getSuccessRedirectPage() {
        return "index.jsp";
    }

    protected String getErrorRedirectPage() {
        return "changepassword.jsp";
    }

    protected Map<String, String> getParametersMap(HttpServletRequest request) {
        Map<String, String> parametersMap = new HashMap<>();
        parametersMap.put(USERNAME_KEY, (String) SecurityUtils.getSubject().getPrincipal());
        parametersMap.put(OLD_PASSWORD_KEY, request.getParameter("old_password"));
        parametersMap.put(PASSWORD_KEY, request.getParameter("password"));
        parametersMap.put(CONFIRM_PASSWORD_KEY, request.getParameter("confirm_password"));
        return parametersMap;
    }
}

