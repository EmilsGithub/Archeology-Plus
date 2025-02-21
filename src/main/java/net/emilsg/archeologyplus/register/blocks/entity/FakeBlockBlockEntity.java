package net.emilsg.archeologyplus.register.blocks.entity;

import net.emilsg.archeologyplus.register.blocks.custom.FakeBlockBlock;
import net.emilsg.archeologyplus.util.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FakeBlockBlockEntity extends BlockEntity {
    private int ticker = 100;

    public FakeBlockBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FAKE_BLOCK_BLOCK_ENTITY, pos, state);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T t) {
        if (!(t instanceof FakeBlockBlockEntity entity)) return;

        if (state.get(ModProperties.HIDDEN)) {
            entity.ticker--;
        }

        if (entity.ticker <= 0) {
            FakeBlockBlock.setHidden(world, pos, state, false);
            entity.ticker = 100;
        }
    }
}
