package be.cegeka.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;

public class RealmLocator {

    public static <T> T locate(Class<T> clazz) {
        for (Realm realm : ((RealmSecurityManager) SecurityUtils.getSecurityManager()).getRealms()) {
            if (realm.getClass().equals(clazz)) {
                return (T) realm;
            }
        }
        return null;
    }
}
