package com.heirteir.autoeye.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;

import java.util.Set;

@RequiredArgsConstructor @Getter public class BlockSet {
    private final Set<Block> blocks;

    public boolean containsString(String... values) {
        for (Block block : this.blocks) {
            for (String value : values) {
                if (block.getType().name().contains(value)) {
                    return true;
                }
            }
        }
        return false;
    }
}
