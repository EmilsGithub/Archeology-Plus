package net.emilsg.archeologyplus.register.blocks.custom;

import net.emilsg.archeologyplus.register.blocks.entity.FakeBlockBlockEntity;
import net.emilsg.archeologyplus.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class FakeBlockBlock extends BlockWithEntity {
    public static final BooleanProperty HIDDEN = ModProperties.HIDDEN;

    public FakeBlockBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HIDDEN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HIDDEN);
    }

    private static final int MAX_RECURSION_DEPTH = 64;

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!state.get(HIDDEN) && player.getStackInHand(hand).isOf(Items.AIR)) {
            setHidden(world, pos, state, true);
            Set<BlockPos> visitedPositions = new HashSet<>();
            tryHideNeighbours(pos, world, visitedPositions, 0);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public static void setHidden(World world, BlockPos pos, BlockState state, boolean hidden) {
        if (world instanceof ServerWorld) {
            world.setBlockState(pos, state.with(HIDDEN, hidden), Block.NOTIFY_ALL);
        }
        world.addParticle(ParticleTypes.POOF, pos.getX() + 0.5f + world.random.nextDouble() / 1.5 * (double) (world.random.nextBoolean() ? 1 : -1), pos.getY() + 0.5f + world.random.nextDouble() / 1.5 * (double) (world.random.nextBoolean() ? 1 : -1), pos.getZ() + 0.5f + world.random.nextDouble() / 1.5 * (double) (world.random.nextBoolean() ? 1 : -1), world.random.nextDouble() / 16.0 * (double) (world.random.nextBoolean() ? 1 : -1), world.random.nextDouble() / 16.0 * (double) (world.random.nextBoolean() ? 1 : -1), world.random.nextDouble() / 16.0 * (double) (world.random.nextBoolean() ? 1 : -1));
        world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f + (world.getRandom().nextFloat() / 5));
    }

    private void tryHideNeighbours(BlockPos pos, World world, Set<BlockPos> visitedPositions, int depth) {
        if (depth >= MAX_RECURSION_DEPTH) {
            return;
        }

        if (!visitedPositions.add(pos)) {
            return;
        }

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(neighborPos);

            if (neighborState.getBlock() instanceof FakeBlockBlock && !neighborState.get(HIDDEN)) {
                setHidden(world, neighborPos, neighborState, true);
                this.tryHideNeighbours(neighborPos, world, visitedPositions, depth + 1);
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HIDDEN) ? VoxelShapes.empty() : VoxelShapes.fullCube();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FakeBlockBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return FakeBlockBlockEntity::tick;
    }
}
