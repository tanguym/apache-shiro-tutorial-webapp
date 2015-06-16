
package be.cegeka.shiro.servlets;

import be.cegeka.shiro.realm.JdbcCustomizedRealm;
import be.cegeka.shiro.realm.RealmLocator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserCreationServlet", urlPatterns = {"admin/userCreation"})
public class UserCreationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.checkPermission("users:write");
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        if (realm.userExists(request.getParameter("username"))) {
            response.sendRedirect("addUser.jsp?error=user_exists");
        }
        String password = request.getParameter("password");
        //TODO check password
        realm.createUser(new UsernamePasswordToken(request.getParameter("username"), password));
        response.sendRedirect("index.jsp?message=user_created");
    }
}
