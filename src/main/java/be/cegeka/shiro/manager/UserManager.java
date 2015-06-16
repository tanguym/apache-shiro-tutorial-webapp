package be.cegeka.shiro.manager;

import be.cegeka.shiro.realm.JdbcCustomizedRealm;
import be.cegeka.shiro.realm.RealmLocator;
import be.cegeka.shiro.realm.RoleRepository;
import be.cegeka.shiro.realm.UserRepository;
import be.cegeka.shiro.transfer.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.List;

public class UserManager {

    public static String addUser(String username, String password) {
        SecurityUtils.getSubject().checkPermission("users:write");
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        if (realm.userExists(username)) {
            return "user_exists";
        }
        //TODO check password
        realm.createUser(new UsernamePasswordToken(username, password));
        return null;
    }

    public static String changePassword(String username, String newPassword) {
        SecurityUtils.getSubject().checkPermission("users:write");
        //TODO check password
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        realm.updatePasswordWithoutValidation(username, newPassword);
        return null;
    }

    public static String unlockAccount(String username) {
        SecurityUtils.getSubject().checkPermission("users:write");
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        realm.resetModuleLocks(username);
        return null;
    }

    public static List<User> getUsers() {
        SecurityUtils.getSubject().checkPermission("users:read");
        List<User> users = UserRepository.getUsers();

        for (User user : users) {
            user.setRoles(RoleRepository.getRolesForUser(user));
        }

        return users;
    }
}
