package net.emilsg.archeologyplus.register.blocks.custom;

import net.emilsg.archeologyplus.enchantment.ModEnchantments;
import net.emilsg.archeologyplus.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class StepBreakBlock extends Block {
    public static final BooleanProperty WILL_BREAK = ModProperties.WILL_BREAK;

    public StepBreakBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WILL_BREAK, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WILL_BREAK);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(!(world instanceof ServerWorld serverWorld)) return;

        if (state.get(WILL_BREAK) && entity instanceof PlayerEntity player) {
            float bypassLevel = EnchantmentHelper.getLevel(ModEnchantments.BYPASS_ENCHANTMENT, player.getEquippedStack(EquipmentSlot.LEGS));

            if(serverWorld.random.nextFloat() < bypassLevel / 8) {
                bypass(serverWorld, pos, state);
                return;
            }

            breakThisAndTryNeighbours(serverWorld, pos);
        }
    }

    private void breakThisAndTryNeighbours(World world, BlockPos pos) {
        world.breakBlock(pos, false);
        for (Direction direction : Direction.values()) {

            BlockPos neighborPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(neighborPos);

            if (neighborState.getBlock() instanceof StepBreakBlock) {
                boolean willBreak = neighborState.get(WILL_BREAK);
                if (willBreak) {
                    world.breakBlock(neighborPos, false);
                }
            }

        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.onLandedUpon(world, state, pos, entity, fallDistance);

        if(!(world instanceof ServerWorld serverWorld)) return;

        if (state.get(WILL_BREAK) && entity instanceof PlayerEntity player) {
            float bypassLevel = EnchantmentHelper.getLevel(ModEnchantments.BYPASS_ENCHANTMENT, player.getEquippedStack(EquipmentSlot.LEGS));

            if(serverWorld.random.nextFloat() < bypassLevel / 8) {
                this.bypass(serverWorld, pos, state);
                return;
            }

            breakThisAndTryNeighbours(serverWorld, pos);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean powered = world.isReceivingRedstonePower(pos);
        if (powered) {
            world.setBlockState(pos, state.with(WILL_BREAK, false));
        }
    }

    private void bypass(ServerWorld serverWorld, BlockPos pos, BlockState state) {
        serverWorld.setBlockState(pos, state.with(WILL_BREAK, false), Block.NOTIFY_ALL);
        serverWorld.playSound(null, pos, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, 0.75f);
    }
}
