package be.cegeka.shiro.manager;

import be.cegeka.shiro.realm.JdbcCustomizedRealm;
import be.cegeka.shiro.realm.PermissionRepository;
import be.cegeka.shiro.realm.RealmLocator;
import be.cegeka.shiro.realm.RoleRepository;
import be.cegeka.shiro.transfer.Role;
import be.cegeka.shiro.transfer.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.List;

public class RoleManager {

    public static String addRole(String role) {
        SecurityUtils.getSubject().checkPermission("users:write");
        if (RoleRepository.roleExists(role)) {
            return "role_exists";
        }
        RoleRepository.createRole(role);
        return null;
    }

    public static List<Role> getRoles() {
        List<Role> roles = RoleRepository.getRoles();
        setPermissionsForRoles(roles);
        return roles;
    }

    private static void setPermissionsForRoles(List<Role> roles) {
        for (Role role : roles) {
            role.setPermissions(PermissionRepository.getPermissionsForRole(role));
        }
    }

    public static List<Role> getRolesForUser(User user) {
        List<Role> roles = RoleRepository.getRolesForUser(user);
        setPermissionsForRoles(roles);
        return roles;
    }

}
