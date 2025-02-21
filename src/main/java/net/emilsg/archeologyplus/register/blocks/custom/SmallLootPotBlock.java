package net.emilsg.archeologyplus.register.blocks.custom;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class SmallLootPotBlock extends LootPotBlock {

    private static final VoxelShape SHAPE = Block.createCuboidShape(4.5, 0, 4.5, 11.5, 9.5, 11.5);

    public SmallLootPotBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape() {
        return SHAPE;
    }

}
