package be.cegeka.shiro.realm;

import org.apache.shiro.authc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCustomizedRealm extends org.apache.shiro.realm.jdbc.JdbcRealm {


    private static final Logger log = LoggerFactory.getLogger(JdbcCustomizedRealm.class);
    private List<ValidationModule> validationModules = new ArrayList<>();

    public JdbcCustomizedRealm() {
        super();
        validationModules.add(new PasswordExpirationModule());
        validationModules.add(new AccountLockoutModule());
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = ((UsernamePasswordToken) token).getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        validateModules(username);
        AuthenticationInfo authenticationInfo = super.doGetAuthenticationInfo(token);
        if (getCredentialsMatcher().doCredentialsMatch(token, authenticationInfo)) {
            resetModules(username);
        }
        return authenticationInfo;
    }

    private void resetModules(String username) {
        try (Connection conn = dataSource.getConnection()) {
            for (ValidationModule validationModule : validationModules) {
                validationModule.reset(username, conn);
            }
        } catch (SQLException e) {
            handleException(username, e);
        }
    }

    private void validateModules(String username) {
        try (Connection conn = dataSource.getConnection()) {
            for (ValidationModule validationModule : validationModules) {
                validationModule.validate(username, conn);
            }
        } catch (SQLException e) {
            handleException(username, e);
        }
    }

    private void handleException(String username, SQLException e) {
        final String message = "There was a SQL error while authenticating user [" + username + "]";
        if (log.isErrorEnabled()) {
            log.error(message, e);
        }
        throw new AuthenticationException(message, e);
    }
}
