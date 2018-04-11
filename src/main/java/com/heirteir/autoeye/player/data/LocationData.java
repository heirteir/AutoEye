package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInFlying;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.BlockSet;
import com.heirteir.autoeye.util.Vector;
import com.heirteir.autoeye.util.reflections.wrappers.WrappedAxisAlignedBB;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

@Getter public class LocationData {
    private boolean teleported;
    private boolean inWater;
    private int move;
    private Vector teleportLocation;
    private WrappedAxisAlignedBB axisAlignedBB;
    private BlockSet solidBlocks;
    private BlockSet groundBlocks;
    private boolean onStairs;
    private boolean onSlime;
    private boolean inWeb;
    private boolean onLadder;
    private boolean serverOnGround;
    private boolean onPiston;

    public LocationData(Autoeye autoeye, AutoEyePlayer player) {
        this.axisAlignedBB = new WrappedAxisAlignedBB(autoeye, player.getPlayer());
        this.reset(player.getPlayer());
    }

    public void reset(Player player) {
        teleportLocation = new Vector((float) player.getLocation().getX(), (float) player.getLocation().getY(), (float) player.getLocation().getZ());
        this.solidBlocks = new BlockSet(this.axisAlignedBB.offset(0, -0.2D, 0, 0, 0, 0).getSolidBlocks());
    }

    public void update(Autoeye autoeye, AutoEyePlayer player, PacketPlayInFlying flying) {
        this.axisAlignedBB = new WrappedAxisAlignedBB(autoeye, player.getPlayer());
        WrappedAxisAlignedBB offset = this.axisAlignedBB.offset(0, -0.08D, 0, 0, 0, 0);
        this.solidBlocks = new BlockSet(offset.getSolidBlocks());
        this.onStairs = this.solidBlocks.containsString("STEP", "STAIR");
        this.serverOnGround = this.solidBlocks.getBlocks().size() > 0;
        if (this.solidBlocks.getBlocks().size() != 0) {
            this.groundBlocks = this.solidBlocks;
            this.onSlime = this.groundBlocks.containsString("SLIME");
        }
        if (this.inWeb = offset.containsMaterial("WEB")) {
            player.getTimeData().getLastInWeb().update();
        }
        if (this.onPiston = offset.containsMaterial("PISTON")) {
            player.getTimeData().getLastOnPiston().update();
        } else {
            this.onPiston = player.getTimeData().getLastOnPiston().getDifference() < 150L;
        }
        if (flying.getX() != 0 && flying.getZ() != 0) {
            Block block = player.getPlayer().getWorld().getBlockAt(NumberConversions.floor(flying.getX()), NumberConversions.floor(this.axisAlignedBB.get("b")), NumberConversions.floor(flying.getZ()));
            if (this.onLadder = (block.getType().equals(Material.LADDER) || block.getType().equals(Material.VINE)) && !player.getPlayer().getGameMode().name().equals("SPECTATOR")) {
                player.getTimeData().getLastOnLadder().update();
            } else {
                this.onLadder = player.getTimeData().getLastOnLadder().getDifference() < 150;
            }
        }
        if (this.inWater = autoeye.getReflections().getNMSClass("Entity").getFieldByName("inWater").get(autoeye.getReflections().getEntityPlayer(player.getPlayer()))) {
            player.getTimeData().getLastInWater().update();
        } else {
            this.inWater = player.getTimeData().getLastInWater().getDifference() < 150;
        }
        this.teleported = player.getTimeData().getLastTeleport().getDifference() < 100;
        if (++this.move >= 30 && this.axisAlignedBB.getSolidBlocks().size() == 0) {
            this.teleportLocation = new Vector((float) player.getPlayer().getLocation().getX(), (float) player.getPlayer().getLocation().getY(), (float) player.getPlayer().getLocation().getZ());
            this.move = 0;
        }
    }
}
