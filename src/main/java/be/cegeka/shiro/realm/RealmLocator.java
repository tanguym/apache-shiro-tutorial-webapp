package be.cegeka.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;

public class RealmLocator {

    public static <T extends Realm> T getJdbcRealm(T clazz) {
        for (Realm realm : ((RealmSecurityManager) SecurityUtils.getSecurityManager()).getRealms()) {
            if (realm.getName().contains(clazz.getName())) {
                return (T) realm;
            }
        }
        return null;
    }
}
