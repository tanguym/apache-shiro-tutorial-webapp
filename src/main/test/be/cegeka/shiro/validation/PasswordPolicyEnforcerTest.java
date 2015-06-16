package be.cegeka.shiro.validation;

import be.cegeka.shiro.configuration.ShiroConfiguration;
import junit.framework.TestCase;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PasswordPolicyEnforcerTest extends TestCase {

    @Test
    public void testValidatePassword_short() throws Exception {
        PasswordPolicyEnforcer.setPasswordConfigurationsForTesting(getConfig());
        List<String> result = PasswordPolicyEnforcer.validatePassword("a", "a");

        Assertions.assertThat(result).containsOnly("error.minimumLength", "error.not.complex.enough");
    }

    @Test
    public void testValidatePassword_simple() throws Exception {
        PasswordPolicyEnforcer.setPasswordConfigurationsForTesting(getConfig());
        List<String> result = PasswordPolicyEnforcer.validatePassword("aaaaaaaaaaaa", "aaaaaaaaaaaa");

        Assertions.assertThat(result).containsOnly("error.not.complex.enough");
    }

    @Test
    public void testValidatePassword_not_same() throws Exception {
        PasswordPolicyEnforcer.setPasswordConfigurationsForTesting(getConfig());
        List<String> result = PasswordPolicyEnforcer.validatePassword("aaaaaaaaaaaa", "bbbbbbbbb");

        Assertions.assertThat(result).containsOnly("error.not.complex.enough", "error.passwords.not.matching");
    }

    @Test
    public void testValidatePassword_Correct() throws Exception {
        PasswordPolicyEnforcer.setPasswordConfigurationsForTesting(getConfig());
        List<String> result = PasswordPolicyEnforcer.validatePassword("Test123", "Test123");

        Assertions.assertThat(result).isEmpty();
    }

    private List<ShiroConfiguration> getConfig() {
        ArrayList<ShiroConfiguration> result = new ArrayList<>();
        result.add(new ShiroConfiguration("PASSWORD_MAX_LENGTH","15"));
        result.add(new ShiroConfiguration("PASSWORD_MIN_LENGTH","6"));
        result.add(new ShiroConfiguration("PASSWORD_VALID_CHARACTERS","[\\x21-\\x7E]*"));
        result.add(new ShiroConfiguration("PASSWORD_COMPLEXITY_RULE",".*[A-Z].*"));
        result.add(new ShiroConfiguration("PASSWORD_COMPLEXITY_RULE",".*[a-z].*"));
        result.add(new ShiroConfiguration("PASSWORD_COMPLEXITY_RULE",".*[0-9].*"));
        result.add(new ShiroConfiguration("PASSWORD_COMPLEXITY_RULE",".*[\\W_].*"));
        result.add(new ShiroConfiguration("PASSWORD_MINIMUM_COMPLEXITY","3"));
        return result;
    }
}