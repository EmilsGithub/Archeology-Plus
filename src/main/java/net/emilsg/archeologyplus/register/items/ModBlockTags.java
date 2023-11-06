package net.emilsg.archeologyplus.register.items;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {

    private static TagKey<Block> create(String path) {
        return create(path, ArcheologyPlus.MOD_ID);
    }

    private static TagKey<Block> create(String path, String namespace) {
        return TagKey.of(Registries.BLOCK.getKey(), new Identifier(namespace, path));
    }
}
