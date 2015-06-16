package be.cegeka.shiro.validation;

import be.cegeka.shiro.configuration.ShiroConfiguration;
import be.cegeka.shiro.realm.ShiroConfigurationRepository;

import java.util.ArrayList;
import java.util.List;

public class PasswordPolicyEnforcer {

    private static List<ShiroConfiguration> passwordConfigurations = null;

    public static List<String> validatePassword(String password, String confirmPassword) {
        initPasswordConfigurationsIfNeeded();
        List<String> errors = new ArrayList<>();
        checkPasswordAndConfirmPassword(password, confirmPassword, errors);
        checkPasswordLength(password, errors);
        checkPasswordOnValidCharacter(password, errors);
        checkPasswordComplexity(password, errors);
        return errors;
    }

    private static void checkPasswordAndConfirmPassword(String password, String confirmPassword, List<String> errors) {
        if (!password.equals(confirmPassword)) {
            errors.add("error.passwords.not.matching");
        }
    }

    private static String checkPasswordLength(String password, List<String> errors) {
        int minimumLength = getPasswordLengthConfiguration("PASSWORD_MIN_LENGTH");
        int maximumLength = getPasswordLengthConfiguration("PASSWORD_MAX_LENGTH");
        if (password.length() < minimumLength) {
            errors.add("error.minimumLength");
        }
        if (password.length() > maximumLength) {
            errors.add("error.maximumLength");
        }
        return null;
    }

    private static void checkPasswordOnValidCharacter(String password, List<String> errors) {
        String validCharacterRegex = getValidCharacterRegex();
        if (!password.toUpperCase().matches(validCharacterRegex)) {
            errors.add("error.not.valid");
        }
    }

    private static void checkPasswordComplexity(String password, List<String> errors) {
        List<String> passwordComplexityRules = getPasswordComplexityRules();
        int minimumAmountPasswordComplexityRulesApplied = Integer.parseInt(getPasswordConfigurationValue("PASSWORD_MINIMUM_COMPLEXITY"));
        int amountPasswordComplexityRulesApplied = 0;

        for (String passwordComplexityRule : passwordComplexityRules) {
            if (password.matches(passwordComplexityRule)) {
                amountPasswordComplexityRulesApplied ++;
            }
        }

        if (amountPasswordComplexityRulesApplied < minimumAmountPasswordComplexityRulesApplied) {
            errors.add("error.not.complex.enough");
        }

    }

    private static List<String> getPasswordComplexityRules() {
        List<String> passwordComplexityRules = new ArrayList<>();
        for (ShiroConfiguration passwordConfiguration : passwordConfigurations) {
            if (passwordConfiguration.getConfigurationKey().equals("PASSWORD_COMPLEXITY_RULE")) {
                passwordComplexityRules.add(passwordConfiguration.getConfigurationValue());
            }
        }
        return passwordComplexityRules;
    }

    private static String getValidCharacterRegex() {
        return getPasswordConfigurationValue("PASSWORD_VALID_CHARACTERS");
    }

    private static String getPasswordConfigurationValue(String configurationKey) {
        for (ShiroConfiguration passwordConfiguration : passwordConfigurations) {
            if (passwordConfiguration.getConfigurationKey().equals(configurationKey)) {
                return passwordConfiguration.getConfigurationValue();
            }
        }
        return null;
    }

    private static int getPasswordLengthConfiguration(String lengthConfiguration) {
        String passwordConfigurationValue = getPasswordConfigurationValue(lengthConfiguration);
        if (passwordConfigurationValue != null) {
            return Integer.parseInt(passwordConfigurationValue);
        } else {
            return 0;
        }
    }

    private static void initPasswordConfigurationsIfNeeded() {
        if (passwordConfigurations == null) {
            passwordConfigurations =  ShiroConfigurationRepository.getShiroPasswordConfigurations();
        }
    }

    protected static void setPasswordConfigurationsForTesting(List<ShiroConfiguration> config) {
        passwordConfigurations = config;
    }

}
