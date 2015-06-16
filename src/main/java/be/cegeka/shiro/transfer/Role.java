package be.cegeka.shiro.transfer;

import java.util.List;

public class Role {

    private String name;
    private List<Permission> permissions;

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
