/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
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
