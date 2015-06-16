package be.cegeka.shiro.realm;

import be.cegeka.shiro.configuration.ShiroConfiguration;
import be.cegeka.shiro.transfer.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        super.setAuthenticationQuery("SELECT password FROM shiro_user WHERE username = ?");
        super.setPermissionsLookupEnabled(true);
        super.setUserRolesQuery("select r.name from shiro_role r join shiro_user_role ur on ur.role_id=r.id join shiro_user u on ur.user_id = u.id where u.username = ?");
        super.setPermissionsQuery("select p.name from shiro_permission p join shiro_permission_role rp on rp.permission_id=p.id join shiro_role r on r.id = rp.role_id where r.name=?");
    }

    public void updatePassword(UsernamePasswordToken currentCredentials, String newPassword) throws AuthenticationException {
        AuthenticationInfo authenticationInfo = super.doGetAuthenticationInfo(currentCredentials);
        if (getCredentialsMatcher().doCredentialsMatch(currentCredentials, authenticationInfo)) {
            String username = currentCredentials.getUsername();
            updatePasswordWithoutValidation(username, newPassword);
        } else {
            throw new AuthenticationException("error.username.password.not.matching");
        }
    }

    public void createUser(UsernamePasswordToken credentials) {
        String encryptedNewPassword = ((PasswordMatcher) getCredentialsMatcher()).getPasswordService().encryptPassword(credentials.getPassword());
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("insert into shiro_user (username, password) values (?, ?)");
            statement.setString(1, credentials.getUsername());
            statement.setString(2, encryptedNewPassword);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleException(credentials.getUsername(), e);
        }
    }

    public boolean userExists(String username) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("select count(*) from shiro_user where username= ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            handleException(username, e);
        }
        return false;
    }

    public void updatePasswordWithoutValidation(String username, String password) {
        String encryptedNewPassword = ((PasswordMatcher) getCredentialsMatcher()).getPasswordService().encryptPassword(password);
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("update shiro_user set password = ?, last_password_change = CURRENT_TIMESTAMP where username = ?");
            statement.setString(1, encryptedNewPassword);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleException(username, e);
        }
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = ((UsernamePasswordToken) token).getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        validateModules(username);
        AuthenticationInfo authenticationInfo = super.doGetAuthenticationInfo(token);
        if (getCredentialsMatcher().doCredentialsMatch(token, authenticationInfo)) {
            resetModuleLocks(username);
        }
        return authenticationInfo;
    }

    public void resetModuleLocks(String username) {
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
        final String message = "There was a SQL error for user [" + username + "]";
        if (log.isErrorEnabled()) {
            log.error(message, e);
        }
        throw new AuthenticationException(message, e);
    }

    @Override
    public void setAuthenticationQuery(String authenticationQuery) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUserRolesQuery(String userRolesQuery) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPermissionsQuery(String permissionsQuery) {
        throw new UnsupportedOperationException();
    }

    public List<ShiroConfiguration> getShiroPasswordConfigurations() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select configuration_key, configuration_value from shiro_configuration where configuration_key like 'PASSWORD_%'");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ShiroConfiguration> shiroConfigs = new ArrayList<>();
            while (resultSet.next()) {
                shiroConfigs.add(new ShiroConfiguration(resultSet.getString("configuration_key"), resultSet.getString("configuration_value")));
            }
            return shiroConfigs;

        } catch (SQLException e) {
            final String message = "There was a SQL error while retrieving shiro_password_configurations";
            if (log.isErrorEnabled()) {
                log.error(message, e);
            }
            throw new AuthenticationException(message, e);
        }
    }

    public List<User> getUsers() {
        ArrayList<User> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            ResultSet resultSet = connection.prepareStatement("select username, last_password_change, invalid_login_attempts from shiro_user").executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                DateTime lastChange = new DateTime(resultSet.getDate("last_password_change"));
                int attempts = resultSet.getInt("invalid_login_attempts");
                result.add(new User(username, attempts, lastChange));
            }
        } catch (SQLException e) {
            handleException("all_users", e);
        }
        return result;
    }

}
