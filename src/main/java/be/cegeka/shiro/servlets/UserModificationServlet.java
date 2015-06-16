
package be.cegeka.shiro.servlets;

import be.cegeka.shiro.realm.JdbcCustomizedRealm;
import be.cegeka.shiro.realm.RealmLocator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserModificationServlet", urlPatterns = {"admin/userModification"})
public class UserModificationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.checkPermission("users:write");
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);

        if ("Update password".equals(request.getParameter("action"))) {
            realm.updatePasswordWithoutValidation(request.getParameter("username"), request.getParameter("password"));
        } else if ("Unlock account".equals(request.getParameter("action"))) {
            realm.resetModuleLocks(request.getParameter("username"));
        } else {
            response.sendRedirect("modifyUser.jsp");
        }
        response.sendRedirect("index.jsp");
    }
}
