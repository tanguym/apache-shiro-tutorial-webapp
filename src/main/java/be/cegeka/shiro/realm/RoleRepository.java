package be.cegeka.shiro.realm;

import be.cegeka.shiro.transfer.Role;
import be.cegeka.shiro.transfer.User;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {

    public static List<Role> getRoles() {
        List<Role> result = new ArrayList<>();
        try (Connection connection = getRealm().getDataSource().getConnection()) {
            ResultSet resultSet = connection.prepareStatement("select name from shiro_role").executeQuery();
            while (resultSet.next()) {
                result.add(new Role(resultSet.getString("name")));
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean roleExists(String role) {
        try (Connection conn = getRealm().getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("select count(*) from shiro_role where name= ?");
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void createRole(String role) {
        try (Connection conn = getRealm().getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("insert into shiro_role (name) values (?)");
            statement.setString(1, role);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<Role> getRolesForUser(User user) {
        List<Role> roles = new ArrayList<>();

        try (Connection conn = getRealm().getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("select r.name from shiro_user u join shiro_user_role ur on u.id = ur.user_id join shiro_role r on r.id = ur.role_id where u.username = ?");
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roles.add(new Role(resultSet.getString("name")));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return roles;
    }

    public static void updatePermissionsForRole(String role, String[] assignedPermissions) {
        try (Connection conn = getRealm().getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("delete from shiro_permission_role where role_id = (select id from shiro_role where name = ?)");
            statement.setString(1, role);
            statement.executeUpdate();

            for (String assignedPermission : assignedPermissions) {
                statement = conn.prepareStatement("insert into shiro_permission_role (permission_id, role_id) select p.id permission_id, r.id role_id from shiro_permission p, shiro_role r where p.name=? and r.name =?");
                statement.setString(1, assignedPermission);
                statement.setString(2, role);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private static JdbcCustomizedRealm getRealm() {
        return RealmLocator.locate(JdbcCustomizedRealm.class);
    }

    public static void deleteRole(String name) {
        try (Connection conn = getRealm().getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("delete from shiro_user_role where role_id = (select id from shiro_role where name = ?)");
            statement.setString(1, name);
            statement.executeUpdate();

            statement = conn.prepareStatement("delete from shiro_permission_role where role_id = (select id from shiro_role where name = ?)");
            statement.setString(1, name);
            statement.executeUpdate();

            statement = conn.prepareStatement("delete from shiro_role where name = ?");
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
