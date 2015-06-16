package be.cegeka.shiro.manager;

import be.cegeka.shiro.realm.PermissionRepository;
import be.cegeka.shiro.transfer.Permission;
import be.cegeka.shiro.transfer.Role;
import be.cegeka.shiro.transfer.User;
import org.apache.shiro.SecurityUtils;

import java.util.List;

public class PermissionManager {

    public static String addPermission(String permission) {
        SecurityUtils.getSubject().checkPermission("users:write");
        if (PermissionRepository.permissionExists(permission)) {
            return "permission_exists";
        }
        PermissionRepository.createPermission(permission);
        return null;
    }

    public static List<Permission> getPermissions() {
        return PermissionRepository.getPermissions();
    }

    public static List<Permission> getPermissionsForRole(Role role) {
        return PermissionRepository.getPermissionsForRole(role);
    }

}
