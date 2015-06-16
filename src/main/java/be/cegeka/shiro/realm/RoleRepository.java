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
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        try (Connection connection = realm.getDataSource().getConnection()) {
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
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        try (Connection conn = realm.getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("select count(*) from shiro_role where name= ?");
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void createRole(String role) {
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        try (Connection conn = realm.getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("insert into shiro_role (name) values (?)");
            statement.setString(1, role);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<Role> getRolesForUser(User user) {
        List<Role> roles = new ArrayList<>();

        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        try (Connection conn = realm.getDataSource().getConnection()) {
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
}
