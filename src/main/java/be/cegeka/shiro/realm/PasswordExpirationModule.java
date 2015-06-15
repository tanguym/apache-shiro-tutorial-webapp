package be.cegeka.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.joda.time.DateTime;

import java.sql.*;

public class PasswordExpirationModule implements ValidationModule {

    public static final int PASSWORD_EXPIRATION_TIME_IN_DAYS = 30;

    public void validate(String username, Connection connection) throws SQLException, AuthenticationException {
        PreparedStatement preparedStatement = connection.prepareStatement("select last_password_change from shiro_user where username = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Date lastPasswordChange = resultSet.getDate(1);
            DateTime now = new DateTime();
            if (new DateTime(lastPasswordChange).isBefore(now.minusDays(PASSWORD_EXPIRATION_TIME_IN_DAYS))) {
                throw new ExpiredCredentialsException("You haven't changed your password in the past " + PASSWORD_EXPIRATION_TIME_IN_DAYS + " days, please change it.");
            }
        }
    }
}
