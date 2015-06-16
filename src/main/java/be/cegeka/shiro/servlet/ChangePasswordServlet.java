package be.cegeka.shiro.servlet;

import be.cegeka.shiro.realm.JdbcCustomizedRealm;
import be.cegeka.shiro.realm.RealmLocator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/account/ChangeMyPassword"})
public class ChangePasswordServlet extends AbstractChangePasswordServlet {

    @Override
    protected void doPasswordChange(Map<String, String> parametersMap) {
        UsernamePasswordToken currentCredentials = new UsernamePasswordToken(parametersMap.get(USERNAME_KEY), parametersMap.get(OLD_PASSWORD_KEY));
        String newPassword = parametersMap.get(PASSWORD_KEY);
        RealmLocator.locate(JdbcCustomizedRealm.class).updatePassword(currentCredentials, newPassword);
    }

    @Override
    protected String getSuccessRedirectPage() {
        return "index.jsp";
    }

    @Override
    protected String getErrorRedirectPage() {
        return "changepassword.jsp";
    }

    @Override
    protected Map<String, String> getParametersMap(HttpServletRequest request) {
        Map<String, String> parametersMap = new HashMap<>();
        parametersMap.put(USERNAME_KEY, (String) SecurityUtils.getSubject().getPrincipal());
        parametersMap.put(OLD_PASSWORD_KEY, request.getParameter("old_password"));
        parametersMap.put(PASSWORD_KEY, request.getParameter("password"));
        parametersMap.put(CONFIRM_PASSWORD_KEY, request.getParameter("confirm_password"));
        return parametersMap;
    }
}

