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
}
