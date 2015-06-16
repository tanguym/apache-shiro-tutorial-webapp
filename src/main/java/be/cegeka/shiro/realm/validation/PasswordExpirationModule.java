package be.cegeka.shiro.realm.validation;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordExpirationModule implements ValidationModule {

    public static final int PASSWORD_EXPIRATION_TIME_IN_DAYS = 30;
    public static final String ERROR_PASSWORD_EXPIRED = "error.password.expired";

    public void validate(String username, Connection connection) throws SQLException, AuthenticationException {
        PreparedStatement preparedStatement = connection.prepareStatement("select last_password_change from shiro_user where username = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            DateTime lastChange = new DateTime(resultSet.getDate(1));
            if (isPasswordExpired(lastChange)) {
                throw new ExpiredCredentialsException(ERROR_PASSWORD_EXPIRED);
            }
        }
    }

    public static boolean isPasswordExpired(DateTime lastChange) {
        return lastChange.isBefore(new DateTime().minusDays(PASSWORD_EXPIRATION_TIME_IN_DAYS));
    }

    @Override
    public void reset(String username, Connection connection) throws SQLException {
        // do nothing
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
