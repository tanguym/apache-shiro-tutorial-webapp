package be.cegeka.shiro.realm;

import be.cegeka.shiro.transfer.User;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static List<User> getUsers() {
        ArrayList<User> result = new ArrayList<>();
        try (Connection connection = getRealm().getDataSource().getConnection()) {
            ResultSet resultSet = connection.prepareStatement("select username, last_password_change, invalid_login_attempts from shiro_user").executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                DateTime lastChange = new DateTime(resultSet.getDate("last_password_change"));
                int attempts = resultSet.getInt("invalid_login_attempts");
                result.add(new User(username, attempts, lastChange));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    private static JdbcCustomizedRealm getRealm() {
        return RealmLocator.locate(JdbcCustomizedRealm.class);
    }

    public static boolean userExists(String username) {
        try (Connection conn = getRealm().getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("select count(*) from shiro_user where username= ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static User getUser(String username) {
        try (Connection connection = getRealm().getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select username, last_password_change, invalid_login_attempts from shiro_user where username=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                DateTime lastChange = new DateTime(resultSet.getDate("last_password_change"));
                int attempts = resultSet.getInt("invalid_login_attempts");
                return new User(username, attempts, lastChange);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }

    public static void updateRolesForUser(String username, String[] assignedRoles) {
        try (Connection conn = getRealm().getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("delete from shiro_user_role where user_id = (select id from shiro_user where username = ?)");
            statement.setString(1, username);
            statement.executeUpdate();

            for (String assignedRole : assignedRoles) {
                statement = conn.prepareStatement("insert into shiro_user_role (user_id, role_id) select u.id user_id, r.id role_id from shiro_user u, shiro_role r where username=? and name =?");
                statement.setString(1, username);
                statement.setString(2, assignedRole);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void deleteUser(String username) {
        try (Connection conn = getRealm().getDataSource().getConnection()) {
            PreparedStatement statement = conn.prepareStatement("delete from shiro_user_role where user_id = (select id from shiro_user where username = ?)");
            statement.setString(1, username);
            statement.executeUpdate();

            statement = conn.prepareStatement("delete from shiro_user where username = ?");
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
