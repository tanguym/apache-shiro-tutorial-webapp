package be.cegeka.shiro.realm;

import be.cegeka.shiro.transfer.Permission;
import be.cegeka.shiro.transfer.Role;
import be.cegeka.shiro.transfer.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionRepository {

    public static List<Permission> getPermissions() {
        List<Permission> result = new ArrayList<>();
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        try (Connection connection = realm.getDataSource().getConnection()) {
            ResultSet resultSet = connection.prepareStatement("select name from shiro_permission").executeQuery();
            while (resultSet.next()) {
                result.add(new Permission(resultSet.getString("name")));
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean permissionExists(String permission) {
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        try (Connection conn = realm.getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("select count(*) from shiro_permission where name= ?");
            statement.setString(1, permission);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void createPermission(String permission) {
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        try (Connection conn = realm.getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("insert into shiro_permission (name) values (?)");
            statement.setString(1, permission);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<Permission> getPermissionsForRole(Role role) {
        List<Permission> permissions = new ArrayList<>();

        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        try (Connection conn = realm.getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("select p.name from shiro_role r join shiro_permission_role pr on r.id = pr.role_id join shiro_permission p on p.id = pr.permission_id where r.name = ?");
            statement.setString(1, role.getName());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                permissions.add(new Permission(resultSet.getString("name")));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return permissions;
    }
}
