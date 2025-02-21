package net.emilsg.archeologyplus.register.blocks.custom;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class MediumLootPotBlock extends LootPotBlock {

    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 13.5, 13);

    public MediumLootPotBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape() {
        return SHAPE;
    }

}
