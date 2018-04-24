/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:57 PM
 */
package com.heirteir.autoeye.permissions;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

@Getter public class PermissionsManager {
    private final Permission everything = this.createPermission("autoeye.*");
    private final Permission bypass = this.addParent(this.everything, this.createPermission("autoeye.bypass.*"));
    private final Permission notify = this.addParent(this.everything, this.createPermission("autoeye.notify", PermissionDefault.OP));

    public Permission addParent(Permission parent, Permission child) {
        child.addParent(parent, true);
        return child;
    }

    public Permission createPermission(String name) {
        return this.createPermission(name, PermissionDefault.FALSE);
    }

    private Permission createPermission(String name, PermissionDefault permissionDefault) {
        Permission permission;
        if ((permission = Bukkit.getPluginManager().getPermission(name)) == null) {
            permission = new Permission(name, permissionDefault);
        }
        return permission;
    }
}
