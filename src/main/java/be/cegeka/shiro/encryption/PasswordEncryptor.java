package be.cegeka.shiro.encryption;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;

public class PasswordEncryptor {

    public String encryptPassword(String password) {
        DefaultHashService hashService = new DefaultHashService();
        hashService.setHashAlgorithmName("SHA-256");
        hashService.setHashIterations(1000);
        hashService.setGeneratePublicSalt(true);
        DefaultPasswordService passwordService = new DefaultPasswordService();
        passwordService.setHashService(hashService);
        return passwordService.encryptPassword(password);
    }
}
