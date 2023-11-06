package net.emilsg.archeologyplus.register.blocks.custom;

import net.emilsg.archeologyplus.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrumblingBlock extends Block {
    public static final IntProperty CRUMBLE_LEVEL = ModProperties.CRUMBLE_LEVEL;
    int ticker = 16;

    public CrumblingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(CRUMBLE_LEVEL, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CRUMBLE_LEVEL);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        Entity entity = projectile.getOwner();
        if (entity instanceof ServerPlayerEntity && !world.isClient) {
            breakOrCycleState(world, hit.getBlockPos(), state, 1, false);
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(CRUMBLE_LEVEL, ctx.getWorld().random.nextInt(2));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        ticker = ticker - (world.random.nextInt(1) + 1);
        if (world.random.nextBoolean() && !world.isClient && ticker <= 0) {
            breakOrCycleState(world, pos, state, 1, false);
            ticker = 16;
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.2f, world.getDamageSources().fall());
        if(!world.isClient) breakOrCycleState(world, pos, state, (int) fallDistance, true);
    }

    private void breakOrDamageNeighbours(World world, BlockPos pos, boolean isBrokenByFall) {

        for (Direction direction : Direction.values()) {

            BlockPos neighborPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(neighborPos);

            if (neighborState.getBlock() instanceof CrumblingBlock) {
                int crumbleLevel = neighborState.get(CRUMBLE_LEVEL);
                if (world.random.nextBoolean()) {
                    if (crumbleLevel >= (isBrokenByFall ? 1 : 2)) {
                        world.breakBlock(neighborPos, false);
                    } else {
                        world.setBlockState(neighborPos, neighborState.with(CRUMBLE_LEVEL, crumbleLevel + (isBrokenByFall ? 2 : 1)), 3);
                    }
                }
            }

        }
    }

    private void breakOrCycleState(World world, BlockPos pos, BlockState state, int crumbleAmount, boolean isBrokenByFall) {
        int crumbleLevel = world.getBlockState(pos).get(CRUMBLE_LEVEL);
        if (crumbleLevel + crumbleAmount >= 3) {
            world.breakBlock(pos, false);
            breakOrDamageNeighbours(world, pos, isBrokenByFall);
            return;
        }
        world.setBlockState(pos, state.with(CRUMBLE_LEVEL, state.get(CRUMBLE_LEVEL) + crumbleAmount), Block.NOTIFY_ALL);

        world.playSound(null, pos, SoundEvents.BLOCK_STONE_HIT, SoundCategory.BLOCKS, 1.25f, world.random.nextFloat() * 0.7f + 0.2f);
    }
}
