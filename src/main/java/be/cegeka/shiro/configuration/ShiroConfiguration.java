package be.cegeka.shiro.configuration;

public class ShiroConfiguration {

    private String configurationKey;
    private String configurationValue;

    public ShiroConfiguration(String configurationKey, String configurationValue) {
        this.configurationKey = configurationKey;
        this.configurationValue = configurationValue;
    }

    public String getConfigurationKey() {
        return configurationKey;
    }

    public String getConfigurationValue() {
        return configurationValue;
    }
}
