package com.heirteir.autoeye.check.checks.interaction;

import com.google.common.collect.Sets;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.BlockPlaceEvent;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.util.reflections.wrappers.WrappedAxisAlignedBB;
import com.heirteir.autoeye.util.vector.Ray3D;
import com.heirteir.autoeye.util.vector.Vector3D;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.NumberConversions;

import java.util.Set;

public class ImpossibleInteraction extends Check {
    public ImpossibleInteraction(Autoeye autoeye) {
        super(autoeye, "Impossible Interaction");
    }

    @EventExecutor(event = BlockPlaceEvent.class) public boolean check(BlockPlaceEvent event) {
        if (event.getPacket().getBlock() != null && !event.getPacket().getBlock().getType().equals(Material.AIR)) {
            float distance = (float) Math.ceil(new Vector3D(event.getPacket().getBlock().getX(), event.getPacket().getBlock().getY(), event.getPacket().getBlock().getZ()).subtract(new Vector3D(event.getPlayer().getLocationData().getLocation().getX(), event.getPlayer().getLocationData().getLocation().getY() + 1.8F, event.getPlayer().getLocationData().getLocation().getZ())).length());
            Ray3D direction = new Ray3D(event.getPlayer().getLocationData().getLocation().offset(0, (float) event.getPlayer().getPlayer().getEyeHeight(), 0), event.getPlayer().getLocationData().getDirection().toVector3D());
            Set<Block> checked = Sets.newHashSet();
            for (float x = 0; x <= distance; x += 0.00001) {
                Vector3D point = direction.getPointAtDistance(x);
                Block block = event.getPacket().getBlock().getWorld().getBlockAt(NumberConversions.floor(point.getX()), NumberConversions.floor(point.getY()), NumberConversions.floor(point.getZ()));
                if (!checked.contains(block)) {
                    checked.add(block);
                    if (block.getX() == event.getPacket().getBlock().getX() && block.getY() == event.getPacket().getBlock().getY() && block.getZ() == event.getPacket().getBlock().getZ()) {
                        return false;
                    } else {
                        WrappedAxisAlignedBB bb = new WrappedAxisAlignedBB(autoeye, event.getPacket().getBlock().getWorld(), new Vector3D(block.getX(), block.getY(), block.getZ()));
                        if (bb.getAxisAlignedBB() != null) {
                            if (bb.intersectsRay(direction)) {
                                return true;
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override public <T extends Event> void revert(T event) {
    }
}
