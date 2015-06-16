package be.cegeka.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ValidationModule {
    void validate(String username, Connection connection) throws SQLException, AuthenticationException;
    void reset(String username, Connection connection) throws SQLException;
}
