package net.emilsg.archeologyplus.util.tags;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {

    private static TagKey<Block> create(String path) {
        return create(path, ArcheologyPlus.MOD_ID);
    }

    private static TagKey<Block> create(String path, String namespace) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(namespace, path));
    }
}
