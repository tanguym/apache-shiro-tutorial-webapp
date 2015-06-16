package be.cegeka.shiro.realm.validation;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditLoggingModule implements ValidationModule {
    @Override
    public void validate(String username, Connection connection) throws SQLException, AuthenticationException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into shiro_auditlog (username, event, ip) values (?, ?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, "attempt login");
        preparedStatement.setString(3, ((WebDelegatingSubject) SecurityUtils.getSubject()).getServletRequest().getRemoteHost());
        preparedStatement.executeUpdate();
    }

    @Override
    public void reset(String username, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into shiro_auditlog (username, event, ip) values (?, ?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, "login succesful");
        preparedStatement.setString(3, ((WebDelegatingSubject) SecurityUtils.getSubject()).getServletRequest().getRemoteHost());
        preparedStatement.executeUpdate();
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
