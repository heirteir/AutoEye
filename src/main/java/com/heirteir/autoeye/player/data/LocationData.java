package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInFlying;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.BlockSet;
import com.heirteir.autoeye.util.Vector;
import com.heirteir.autoeye.util.reflections.wrappers.WrappedAxisAlignedBB;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter public class LocationData {
    private boolean teleported;
    private boolean inWater;
    private int move;
    private Vector teleportLocation;
    private WrappedAxisAlignedBB axisAlignedBB;
    private BlockSet blocks;
    private BlockSet groundBlocks;
    private boolean onStairs;
    private boolean onSlime;
    private boolean inWeb;

    public LocationData(Autoeye autoeye, AutoEyePlayer player) {
        this.axisAlignedBB = new WrappedAxisAlignedBB(autoeye, player.getPlayer());
        this.reset(player.getPlayer());
    }

    public void reset(Player player) {
        teleportLocation = new Vector((float) player.getLocation().getX(), (float) player.getLocation().getY(), (float) player.getLocation().getZ());
        this.blocks = new BlockSet(this.axisAlignedBB.offset(0, -0.2D, 0, 0, 0, 0).getSolidBlocks());
    }

    public void update(Autoeye autoeye, AutoEyePlayer player, PacketPlayInFlying flying) {
        this.axisAlignedBB = new WrappedAxisAlignedBB(autoeye, player.getPlayer());
        this.blocks = new BlockSet(this.axisAlignedBB.offset(0, -0.2D, 0, 0, 0, 0).getSolidBlocks());
        this.onStairs = this.blocks.containsString("STEP", "STAIR");
        if (this.blocks.getBlocks().size() != 0) {
            this.groundBlocks = this.blocks;
            this.onSlime = this.groundBlocks.containsString("SLIME");
        }
        boolean tempInWeb = (boolean) autoeye.getReflections().getNMSClass("Entity").getFieldAfterOtherByName("velocityChanged").get(autoeye.getReflections().getEntityPlayer(player.getPlayer()));
        boolean tempInWater = (boolean) autoeye.getReflections().getNMSClass("Entity").getFieldByName("inWater").get(autoeye.getReflections().getEntityPlayer(player.getPlayer()));
        if (tempInWeb) {
            player.getTimeData().getInWeb().update();
        }
        if (tempInWater) {
            player.getTimeData().getLastInWater().update();
        }
        this.teleported = player.getTimeData().getLastTeleport().getDifference() < 100;
        this.inWeb = tempInWeb || player.getTimeData().getInWeb().getDifference() < 150;
        this.inWater = tempInWater || player.getTimeData().getLastInWater().getDifference() < 150;
        if (++this.move >= 30 && this.axisAlignedBB.getSolidBlocks().size() == 0) {
            this.teleportLocation = new Vector((float) player.getPlayer().getLocation().getX(), (float) player.getPlayer().getLocation().getY(), (float) player.getPlayer().getLocation().getZ());
            this.move = 0;
        }
    }
}
