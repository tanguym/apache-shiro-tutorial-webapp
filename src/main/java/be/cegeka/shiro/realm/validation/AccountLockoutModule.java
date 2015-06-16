package be.cegeka.shiro.realm.validation;

import be.cegeka.shiro.transfer.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountLockoutModule implements ValidationModule {

    public static int MAX_RETRIES = 5;

    @Override
    public void validate(String username, Connection connection) throws SQLException, AuthenticationException {
        PreparedStatement preparedStatement = connection.prepareStatement("select invalid_login_attempts from shiro_user where username = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int amountOfRetries = resultSet.getInt(1);
            if (amountOfRetries>MAX_RETRIES) {
                throw new ExpiredCredentialsException("error.too.many.wrong.attempts");
            }
        }
        PreparedStatement updateTriesStatement = connection.prepareStatement("update shiro_user set invalid_login_attempts = invalid_login_attempts+1 where username = ?");
        updateTriesStatement.setString(1, username);
        updateTriesStatement.executeUpdate();
    }

    @Override
    public void reset(String username, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update shiro_user set invalid_login_attempts = 0 where username = ?");
        preparedStatement.setString(1, username);
        preparedStatement.executeUpdate();
    }

    public static boolean isUserLocked(User user) {
        return user.getInvalidLoginAttempts()>MAX_RETRIES;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
