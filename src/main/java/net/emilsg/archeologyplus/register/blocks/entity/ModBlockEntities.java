package net.emilsg.archeologyplus.register.blocks.entity;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static BlockEntityType<SpikeTrapBlockEntity> SPIKE_TRAP_BLOCK_ENTITY;
    public static BlockEntityType<FakeBlockBlockEntity> FAKE_BLOCK_BLOCK_ENTITY;

    public static void registerModBlockEntities() {
        SPIKE_TRAP_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ArcheologyPlus.MOD_ID, "spike_trap"),
                FabricBlockEntityTypeBuilder.create(SpikeTrapBlockEntity::new,
                        ModBlocks.SPIKE_TRAP).build());

        FAKE_BLOCK_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ArcheologyPlus.MOD_ID, "fake_block"),
                FabricBlockEntityTypeBuilder.create(FakeBlockBlockEntity::new,
                        ModBlocks.FAKE_BLOCK).build());
    }
}
