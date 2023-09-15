package net.emilsg.archeologyplus.register.blocks.custom;

import net.emilsg.archeologyplus.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class HieroglyphBlock extends Block {
    public static final IntProperty VARIANT = ModProperties.VARIANT_3;

    public HieroglyphBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(VARIANT, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }
}
