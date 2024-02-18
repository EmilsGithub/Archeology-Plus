package net.emilsg.archeologyplus.register.blocks.custom;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class LootPotBlock extends Block implements Waterloggable {
    private static final VoxelShape SHAPE_ONE = VoxelShapes.union(
            Block.createCuboidShape(5, 0, 5, 11, 6, 11),
            Block.createCuboidShape(6, 6, 6, 10, 7, 10),
            Block.createCuboidShape(5.5, 7, 5.5, 10.5, 9, 10.5)
    );
    private static final VoxelShape SHAPE_TWO = VoxelShapes.union(
            Block.createCuboidShape(8, 0, 2, 14, 6, 8),
            Block.createCuboidShape(9, 6, 3, 13, 7, 7),
            Block.createCuboidShape(8.5, 7, 2.5, 13.5, 9, 7.5)
    );
    private static final VoxelShape SHAPE_THREE = VoxelShapes.union(
            Block.createCuboidShape(2, 0, 2, 8, 6, 8),
            Block.createCuboidShape(3, 6, 3, 7, 7, 7),
            Block.createCuboidShape(2.5, 7, 2.5, 7.5, 9, 7.5)
    );
    private static final VoxelShape SHAPE_FOUR = VoxelShapes.union(
            Block.createCuboidShape(2, 0, 8, 8, 6, 14),
            Block.createCuboidShape(3, 6, 9, 7, 7, 13),
            Block.createCuboidShape(2.5, 7, 8.5, 7.5, 9, 13.5)
    );
    private static final VoxelShape SHAPE_FIVE = VoxelShapes.union(
            Block.createCuboidShape(8, 0, 8, 14, 6, 14),
            Block.createCuboidShape(9, 6, 9, 13, 7, 13),
            Block.createCuboidShape(8.5, 7, 8.5, 13.5, 9, 13.5)
    );

    public static final BooleanProperty DROPS_LOOT = BooleanProperty.of("drops_loot");
    public static final IntProperty POSITION = IntProperty.of("position", 0, 4);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public LootPotBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DROPS_LOOT, false).with(WATERLOGGED, false).with(POSITION, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DROPS_LOOT, WATERLOGGED, POSITION);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(POSITION)) {
            default -> SHAPE_ONE;
            case 1 -> SHAPE_THREE;
            case 2 -> SHAPE_TWO;
            case 3 -> SHAPE_FIVE;
            case 4 -> SHAPE_FOUR;
        };
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : state;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        PlayerEntity player = ctx.getPlayer();
        //if(player != null) player.sendMessage(Text.literal(String.valueOf(ctx.getPlayerLookDirection())));

        int placementPosition = 0;
        boolean bl = ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;

        switch (ctx.getPlayerLookDirection()) {
            case NORTH -> placementPosition = 1;
            case EAST -> placementPosition = 2;
            case SOUTH -> placementPosition = 3;
            case WEST -> placementPosition = 4;
        }

        return this.getDefaultState().with(WATERLOGGED, bl).with(POSITION, placementPosition);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {

            world.setBlockState(pos, (state.with(WATERLOGGED, true)), Block.NOTIFY_ALL);
            world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }
}
