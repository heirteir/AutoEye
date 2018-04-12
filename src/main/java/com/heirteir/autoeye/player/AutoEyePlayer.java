package com.heirteir.autoeye.player;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.PacketPlayInFlyingEvent;
import com.heirteir.autoeye.player.data.InfractionData;
import com.heirteir.autoeye.player.data.LocationData;
import com.heirteir.autoeye.player.data.Physics;
import com.heirteir.autoeye.player.data.TimeData;
import com.heirteir.autoeye.util.Vector3D;
import com.heirteir.autoeye.util.reflections.wrappers.WrappedEntity;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Getter public class AutoEyePlayer {
    private final WrappedEntity wrappedEntity;
    private final Player player;
    private final Physics physics;
    private final LocationData locationData;
    private final InfractionData infractionData;
    private final TimeData timeData;

    public AutoEyePlayer(Autoeye autoeye, Player player) {
        this.wrappedEntity = new WrappedEntity(autoeye, player);
        this.player = player;
        this.physics = new Physics(this);
        this.locationData = new LocationData(autoeye, this);
        this.infractionData = new InfractionData(autoeye);
        this.timeData = new TimeData();
    }

    public void update(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        this.locationData.update(autoeye, this, event.getPacket());
        this.physics.update(this, event.getPacket());
    }

    public synchronized void teleport(Vector3D location) {
        this.getPlayer().teleport(new Location(this.player.getWorld(), location.getX(), location.getY(), location.getZ(), this.player.getEyeLocation().getYaw(), this.player.getEyeLocation().getPitch()));
    }
}
