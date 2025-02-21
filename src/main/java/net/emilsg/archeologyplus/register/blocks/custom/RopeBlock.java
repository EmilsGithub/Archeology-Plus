package net.emilsg.archeologyplus.register.blocks.custom;

import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class RopeBlock extends Block implements Waterloggable {
    private static final BooleanProperty END = ModProperties.END;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty WEST = Properties.WEST;
    public static final BooleanProperty UP = Properties.UP;
    private static final BooleanProperty TOP = ModProperties.TOP;

    private static final VoxelShape FULL_SHAPE = Block.createCuboidShape(5.5, 0, 5.5, 10.5, 16, 10.5);
    private static final VoxelShape NO_TOP_SHAPE = Block.createCuboidShape(5.5, 0, 5.5, 10.5, 12, 10.5);

    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(5.5, 6.5, 0, 10.5, 11.5, 6);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(10, 6.5, 5.5, 16, 11.5, 10.5);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(5.5, 6.5, 10, 10.5, 11.5, 16);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0, 6.5, 5.5, 6, 11.5, 10.5);




    public RopeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(END, true)
                .with(WATERLOGGED, false)
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(UP, false)
                .with(TOP, false)
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();

        if(state.get(UP) || (!state.get(UP) && state.get(END) && !state.get(TOP))) shape = VoxelShapes.union(shape, FULL_SHAPE);
        else if(state.get(TOP) && (!state.get(UP) || !state.get(END))) shape = VoxelShapes.union(shape, NO_TOP_SHAPE);
        if(state.get(NORTH)) shape = VoxelShapes.union(shape, NORTH_SHAPE);
        if(state.get(EAST)) shape = VoxelShapes.union(shape, EAST_SHAPE);
        if(state.get(SOUTH)) shape = VoxelShapes.union(shape, SOUTH_SHAPE);
        if(state.get(WEST)) shape = VoxelShapes.union(shape, WEST_SHAPE);

        if(!state.get(END) && !state.get(NORTH) && !state.get(EAST) && !state.get(SOUTH) && !state.get(WEST) && !state.get(UP) && !state.get(TOP)) shape = VoxelShapes.union(shape, FULL_SHAPE);


        return shape;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(END, WATERLOGGED, NORTH, EAST, SOUTH, WEST, UP, TOP);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getConnectionState(ctx.getWorld(), ctx.getBlockPos());
    }

    public static BlockState getConnectionState(World world, BlockPos pos) {
        BlockPos posNorth = pos.north();
        BlockPos posEast = pos.east();
        BlockPos posSouth = pos.south();
        BlockPos posWest = pos.west();
        BlockPos posUp = pos.up();
        BlockState stateNorth = world.getBlockState(posNorth);
        BlockState stateEast = world.getBlockState(posEast);
        BlockState stateSouth = world.getBlockState(posSouth);
        BlockState stateWest = world.getBlockState(posWest);
        BlockState stateUp = world.getBlockState(posUp);
        BlockState ropeState = ModBlocks.ROPE.getDefaultState();

        boolean waterLogged = world.getFluidState(pos).getFluid() == Fluids.WATER;

        if (world.getBlockState(pos.down()).getBlock() instanceof RopeBlock) ropeState = ropeState.with(END, false);

        return ropeState.with(WATERLOGGED, waterLogged)
                .with(NORTH, canConnect(stateNorth, stateNorth.isSideSolidFullSquare(world, posNorth, Direction.SOUTH), world, pos))
                .with(EAST, canConnect(stateEast, stateEast.isSideSolidFullSquare(world, posEast, Direction.WEST), world, pos))
                .with(SOUTH, canConnect(stateSouth, stateSouth.isSideSolidFullSquare(world, posSouth, Direction.NORTH), world, pos))
                .with(WEST, canConnect(stateWest, stateWest.isSideSolidFullSquare(world, posWest, Direction.EAST), world, pos))
                .with(UP, canConnect(stateUp, stateUp.isSideSolidFullSquare(world, posUp, Direction.DOWN), world, pos))
                .with(END, !(world.getBlockState(pos.down()).getBlock() instanceof RopeBlock))
                .with(TOP, !(world.getBlockState(pos.up()).getBlock() instanceof RopeBlock));
    }

    public static boolean canConnect(BlockState state, boolean neighborIsFullSquare, World world, BlockPos pos) {
        return !cannotConnect(state) && neighborIsFullSquare && !(world.getBlockState(pos.up()).getBlock() instanceof RopeBlock);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos posNorth = pos.north();
        BlockPos posEast = pos.east();
        BlockPos posSouth = pos.south();
        BlockPos posWest = pos.west();
        BlockPos posUp = pos.up();
        BlockState stateNorth = world.getBlockState(posNorth);
        BlockState stateEast = world.getBlockState(posEast);
        BlockState stateSouth = world.getBlockState(posSouth);
        BlockState stateWest = world.getBlockState(posWest);
        BlockState stateUp = world.getBlockState(posUp);

        return  canConnect(stateNorth, stateNorth.isSideSolidFullSquare(world, posNorth, Direction.SOUTH), (World) world, pos) ||
                canConnect(stateEast, stateEast.isSideSolidFullSquare(world, posEast, Direction.WEST), (World) world, pos) ||
                canConnect(stateSouth, stateSouth.isSideSolidFullSquare(world, posSouth, Direction.NORTH), (World) world, pos) ||
                canConnect(stateWest, stateWest.isSideSolidFullSquare(world, posWest, Direction.EAST), (World) world, pos) ||
                canConnect(stateUp, stateUp.isSideSolidFullSquare(world, posUp, Direction.DOWN), (World) world, pos);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if(state.get(TOP)) {
            BlockPos posNorth = pos.north();
            BlockPos posEast = pos.east();
            BlockPos posSouth = pos.south();
            BlockPos posWest = pos.west();
            BlockPos posUp = pos.up();
            BlockState stateNorth = world.getBlockState(posNorth);
            BlockState stateEast = world.getBlockState(posEast);
            BlockState stateSouth = world.getBlockState(posSouth);
            BlockState stateWest = world.getBlockState(posWest);
            BlockState stateUp = world.getBlockState(posUp);

            state = state.with(NORTH, canConnect(stateNorth, stateNorth.isSideSolidFullSquare(world, posNorth, Direction.SOUTH), (World) world, pos))
                    .with(EAST, canConnect(stateEast, stateEast.isSideSolidFullSquare(world, posEast, Direction.WEST), (World) world, pos))
                    .with(SOUTH, canConnect(stateSouth, stateSouth.isSideSolidFullSquare(world, posSouth, Direction.NORTH), (World) world, pos))
                    .with(WEST, canConnect(stateWest, stateWest.isSideSolidFullSquare(world, posWest, Direction.EAST), (World) world, pos))
                    .with(UP, canConnect(stateUp, stateUp.isSideSolidFullSquare(world, posUp, Direction.DOWN), (World) world, pos));
        }

        return !isConnected(state, world, pos) ? Blocks.AIR.getDefaultState() : state.with(END, !(world.getBlockState(pos.down()).getBlock() instanceof RopeBlock)).with(TOP, !(world.getBlockState(pos.up()).getBlock() instanceof RopeBlock));
    }

    private boolean isConnected(BlockState state, WorldAccess world, BlockPos pos) {
        return state.get(NORTH) || state.get(EAST) || state.get(SOUTH) || state.get(WEST) || state.get(UP) || world.getBlockState(pos.up()).getBlock() instanceof RopeBlock;
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().getItem() == this.asItem()) {
            return true;
        }
        return super.canReplace(state, context);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stackInHand = player.getStackInHand(hand);
        if (!(stackInHand.getItem() == this.asItem())) return ActionResult.PASS;

        if (world.getBlockState(pos).getBlock() instanceof RopeBlock) {
            return tryPlaceRopeBelow(player, world, pos, stackInHand, hand);
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    public ActionResult tryPlaceRopeBelow(PlayerEntity player, World world, BlockPos pos, ItemStack stackInHand, Hand hand) {
        if (world.getBlockState(pos.down()).getBlock() instanceof RopeBlock) {
            tryPlaceRopeBelow(player, world, pos.down(), stackInHand, hand);
        } else if (world.getBlockState(pos.down()).isReplaceable()) {
            world.setBlockState(pos.down(), this.getDefaultState().with(WATERLOGGED, world.getFluidState(pos.down()).isOf(Fluids.WATER)));
            if (!player.getAbilities().creativeMode) stackInHand.decrement(1);
            world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1f, 0.5f + (world.random.nextFloat() / 2));
            player.swingHand(hand);
            return ActionResult.success(false);
        }
        return ActionResult.PASS;
    }

    public static int placeRopeForItem(ServerWorld serverWorld, BlockPos pos, int depth, int ropesToDrop) {
        if (depth <= 0) {
            return ropesToDrop;
        }

        BlockState currentBlockState = serverWorld.getBlockState(pos);

        if (currentBlockState.getBlock() instanceof RopeBlock) {
            return placeRopeForItem(serverWorld, pos.down(), depth, ropesToDrop);
        } else if (currentBlockState.isReplaceable() && !currentBlockState.isOf(ModBlocks.ROPE)) {
            serverWorld.setBlockState(pos, getConnectionState(serverWorld, pos));
            serverWorld.playSound(null, pos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1f, 0.5f + (serverWorld.random.nextFloat() / 2));
            return placeRopeForItem(serverWorld, pos.down(), depth - 1, ropesToDrop - 1);
        } else {
            return ropesToDrop;
        }
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
