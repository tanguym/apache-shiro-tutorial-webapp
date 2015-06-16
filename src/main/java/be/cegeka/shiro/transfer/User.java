package be.cegeka.shiro.transfer;

import be.cegeka.shiro.realm.AccountLockoutModule;
import be.cegeka.shiro.realm.PasswordExpirationModule;
import org.joda.time.DateTime;

import java.util.List;

public class User {
    private String username;
    private int invalidLoginAttempts;
    private DateTime lastPasswordChange;
    private List<Role> roles;

    public User(String username, int invalidLoginAttempts, DateTime lastPasswordChange) {
        this.username = username;
        this.invalidLoginAttempts = invalidLoginAttempts;
        this.lastPasswordChange = lastPasswordChange;
    }

    public String getUsername() {
        return username;
    }

    public int getInvalidLoginAttempts() {
        return invalidLoginAttempts;
    }

    public DateTime getLastPasswordChange() {
        return lastPasswordChange;
    }

    public boolean isAccountLocked() {
        return AccountLockoutModule.isUserLocked(this);
    }

    public boolean needsPasswordChange() {
        return PasswordExpirationModule.isPasswordExpired(getLastPasswordChange());
    }

    public List<Role> getRolesForGui() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
