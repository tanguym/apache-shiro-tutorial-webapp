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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getPermissionsForGUI() {
        StringBuilder builder = new StringBuilder();
        for (Permission permission : permissions) {
            builder.append(permission.toString() + ", ");
        }
        String buildedString = builder.toString();
        return buildedString.substring(0, buildedString.length()-2);
    }
}
