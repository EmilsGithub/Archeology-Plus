package net.emilsg.archeologyplus.util.tags;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

public class ModStructureTags {

    public static final TagKey<Structure> IS_DESERT_TEMPLE = create("is_desert_temple");

    public static final TagKey<Structure> APPLIES_CERTAIN_DEATH = create("applies_certain_death");

    private static TagKey<Structure> create(String path) {
        return create(path, ArcheologyPlus.MOD_ID);
    }

    private static TagKey<Structure> create(String path, String namespace) {
        return TagKey.of(RegistryKeys.STRUCTURE, new Identifier(namespace, path));
    }
}
