package net.emilsg.archeologyplus.register.entities;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<RopeProjectileEntity> THROWN_ROPE_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(ArcheologyPlus.MOD_ID, "rope_projectile"),
            FabricEntityTypeBuilder.<RopeProjectileEntity>create(SpawnGroup.MISC, RopeProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

    public static void registerEntities() {

    }
}
