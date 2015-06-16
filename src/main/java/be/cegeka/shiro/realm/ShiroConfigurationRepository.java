package be.cegeka.shiro.realm;

import be.cegeka.shiro.configuration.ShiroConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShiroConfigurationRepository {

    public static List<ShiroConfiguration> getShiroPasswordConfigurations() {
        JdbcCustomizedRealm realm = RealmLocator.locate(JdbcCustomizedRealm.class);
        try (Connection connection = realm.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select configuration_key, configuration_value from shiro_configuration where configuration_key like 'PASSWORD_%'");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ShiroConfiguration> shiroConfigs = new ArrayList<>();
            while (resultSet.next()) {
                shiroConfigs.add(new ShiroConfiguration(resultSet.getString("configuration_key"), resultSet.getString("configuration_value")));
            }
            return shiroConfigs;

        } catch (SQLException e) {
            throw new IllegalStateException("There was a SQL error while retrieving shiro_password_configurations", e);
        }
    }
}
