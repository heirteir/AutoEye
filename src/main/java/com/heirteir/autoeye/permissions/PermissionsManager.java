/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:46 PM
 */
package com.heirteir.autoeye.permissions;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

@Getter public class PermissionsManager {
    private final Permission everything = this.createPermission("autoeye.*");
    private final Permission bypass = this.addParent(this.everything, this.createPermission("autoeye.bypass.*"));
    private final Permission notify = this.addParent(this.everything, this.createPermission("autoeye.notify"));

    public Permission addParent(Permission parent, Permission child) {
        child.addParent(parent, true);
        return child;
    }

    public Permission createPermission(String name) {
        Permission permission;
        if ((permission = Bukkit.getPluginManager().getPermission(name)) == null) {
            permission = new Permission(name, PermissionDefault.FALSE);
        }
        return permission;
    }
}
