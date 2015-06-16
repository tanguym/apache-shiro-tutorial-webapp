package be.cegeka.shiro.servlet;

import be.cegeka.shiro.validation.PasswordPolicyEnforcer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class AbstractChangePasswordServlet extends HttpServlet {

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

    protected abstract void doPasswordChange(Map<String, String> parametersMap);

    protected abstract String getSuccessRedirectPage();

    protected abstract String getErrorRedirectPage();

    protected abstract Map<String, String> getParametersMap(HttpServletRequest request);
}

