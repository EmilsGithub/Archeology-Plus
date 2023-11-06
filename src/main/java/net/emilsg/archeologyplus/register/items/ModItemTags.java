package net.emilsg.archeologyplus.register.items;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> OVERWORLD_IDOLS = create("overworld_idols");

    private static TagKey<Item> create(String path) {
        return create(path, ArcheologyPlus.MOD_ID);
    }

    private static TagKey<Item> create(String path, String namespace) {
        return TagKey.of(Registries.ITEM.getKey(), new Identifier(namespace, path));
    }
}
