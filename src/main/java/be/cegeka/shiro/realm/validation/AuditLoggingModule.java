package be.cegeka.shiro.realm.validation;

import org.apache.shiro.authc.AuthenticationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditLoggingModule implements ValidationModule {
    @Override
    public void validate(String username, Connection connection) throws SQLException, AuthenticationException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into shiro_auditlog (username, event) values (?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, "attempt login");
        preparedStatement.executeUpdate();
    }

    @Override
    public void reset(String username, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into shiro_auditlog (username, event) values (?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, "login succesful");
        preparedStatement.executeUpdate();
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
