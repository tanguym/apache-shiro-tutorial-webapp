package be.cegeka.shiro.realm;

import be.cegeka.shiro.transfer.User;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static List<User> getUsers() {
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        ArrayList<User> result = new ArrayList<>();
        try (Connection connection = realm.getDataSource().getConnection()) {
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
}
