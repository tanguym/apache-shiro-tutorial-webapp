package be.cegeka.shiro.manager;

import be.cegeka.shiro.realm.JdbcCustomizedRealm;
import be.cegeka.shiro.realm.RealmLocator;
import be.cegeka.shiro.realm.UserRepository;
import be.cegeka.shiro.transfer.User;
import be.cegeka.shiro.validation.PasswordPolicyEnforcer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserManager {

    public static List<String> addUser(String username, String password) {
        SecurityUtils.getSubject().checkPermission("users:write");
        if (UserRepository.userExists(username)) {
            return Arrays.asList("user_exists");
        }
        List<String> errors = PasswordPolicyEnforcer.validatePassword(password, password);
        if (errors.isEmpty()) {
            JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
            realm.createUser(new UsernamePasswordToken(username, password));
            return new ArrayList<>();
        } else {
            return errors;
        }
    }

    public static List<String> changePassword(String username, String newPassword) {
        SecurityUtils.getSubject().checkPermission("users:write");
        List<String> errors = PasswordPolicyEnforcer.validatePassword(newPassword, newPassword);
        if (errors.isEmpty()) {
            JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
            realm.updatePasswordWithoutValidation(username, newPassword);
            return new ArrayList<>();
        } else {
            return errors;
        }
    }

    public static List<String> changePassword(String username, String newPassword, String passwordVerification, String oldPassword) {
        SecurityUtils.getSubject().checkPermission("users:write");
        List<String> errors = PasswordPolicyEnforcer.validatePassword(newPassword, passwordVerification);
        if (errors.isEmpty()) {
            JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
            try {
                realm.updatePassword(new UsernamePasswordToken(username, oldPassword), newPassword);
            } catch (AuthenticationException e) {
                return Arrays.asList("error.invalid.password");
            }
            return new ArrayList<>();
        } else {
            return errors;
        }
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
            user.setRoles(RoleManager.getRolesForUser(user));
        }

        return users;
    }

    public static User getUser(String username) {
        SecurityUtils.getSubject().checkPermission("users:write");
        User user = UserRepository.getUser(username);
        user.setRoles(RoleManager.getRolesForUser(user));
        return user;
    }

    public static void updateRolesForUser(String username, String[] assignedRoles) {
        SecurityUtils.getSubject().checkPermission("users:write");
        UserRepository.updateRolesForUser(username, assignedRoles);
    }
}
