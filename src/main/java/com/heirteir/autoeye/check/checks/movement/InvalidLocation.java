package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedConstructor;
import com.heirteir.autoeye.util.reflections.types.WrappedMethod;
import org.bukkit.util.NumberConversions;

public class InvalidLocation extends Check {
    private final WrappedMethod getHandleMethod = Reflections.getCBClass("CraftWorld").getMethod("getHandle");
    private final WrappedMethod isLoaded = Reflections.getNMSClass("World").getMethod("isLoaded", Reflections.getNMSClass("BlockPosition").getParent());
    private final WrappedConstructor blockPosition = Reflections.getNMSClass("BlockPosition").getConstructor(int.class, int.class, int.class);

    public InvalidLocation(Autoeye autoeye) {
        super(autoeye, "Invalid Location");
    }

    @Override public boolean check(AutoEyePlayer player) {
        return player.getLocationData().isChangedPos() && !(boolean) isLoaded.invoke(getHandleMethod.invoke(player.getPlayer().getWorld()), (Object) blockPosition.newInstance(NumberConversions.floor(player.getLocationData().getLocation().getX()), NumberConversions.floor(player.getLocationData().getLocation().getY()), NumberConversions.floor(player.getLocationData().getLocation().getZ())));
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
