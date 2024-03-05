package net.emilsg.archeologyplus.register.items.custom;

import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.blocks.custom.HieroglyphBlock;
import net.emilsg.archeologyplus.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChiselItem extends DescriptionItem {

    public ChiselItem(Settings settings, String description, Formatting formatting, String subDescription, Formatting subFormatting) {
        super(settings, description, formatting, subDescription, subFormatting);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        BlockState state = ctx.getWorld().getBlockState(pos);
        World world = ctx.getWorld();
        PlayerEntity player = ctx.getPlayer();

        if(state.getBlock() instanceof HieroglyphBlock || isSandstoneVariant(state) || isRedSandstoneVariant(state) || state.isOf(Blocks.STONE_BRICKS) || state.isOf(Blocks.MOSSY_STONE_BRICKS) || isCrumblingVariant(state)) {
            if (world.isClient) {
                world.addBlockBreakParticles(pos, state);
                return ActionResult.SUCCESS;
            }

            if (state.getBlock() instanceof HieroglyphBlock) {
                return updateBlockStateAndChisel(world, pos, state, state.cycle(ModProperties.VARIANT_3), player, ctx);
            } else if (isSandstoneVariant(state)) {
                return updateBlockStateAndChisel(world, pos, state, ModBlocks.SANDSTONE_HIEROGLYPHS.getDefaultState().with(ModProperties.VARIANT_3, getRandomVariant(world)), player, ctx);
            } else if (isRedSandstoneVariant(state)) {
                return updateBlockStateAndChisel(world, pos, state, ModBlocks.RED_SANDSTONE_HIEROGLYPHS.getDefaultState().with(ModProperties.VARIANT_3, getRandomVariant(world)), player, ctx);
            } else if (state.isOf(Blocks.STONE_BRICKS)) {
                return updateBlockStateAndChisel(world, pos, state, ModBlocks.STONE_BRICK_WRITINGS.getDefaultState().with(ModProperties.VARIANT_3, getRandomVariant(world)), player, ctx);
            } else if (state.isOf(Blocks.MOSSY_STONE_BRICKS)) {
                return updateBlockStateAndChisel(world, pos, state, ModBlocks.MOSSY_STONE_BRICK_WRITINGS.getDefaultState().with(ModProperties.VARIANT_3, getRandomVariant(world)), player, ctx);
            }  else if (isCrumblingVariant(state)) {
                return updateBlockStateAndChisel(world, pos, state, state.cycle(ModProperties.CRUMBLE_LEVEL), player, ctx);
            }
        }

        return ActionResult.PASS;
    }

    private boolean isSandstoneVariant(BlockState state) {
        return state.isOf(Blocks.SMOOTH_SANDSTONE) || state.isOf(Blocks.SANDSTONE) ||
                state.isOf(Blocks.CUT_SANDSTONE) || state.isOf(Blocks.CHISELED_SANDSTONE);
    }

    private boolean isRedSandstoneVariant(BlockState state) {
        return state.isOf(Blocks.SMOOTH_RED_SANDSTONE) || state.isOf(Blocks.RED_SANDSTONE) ||
                state.isOf(Blocks.CUT_RED_SANDSTONE) || state.isOf(Blocks.CHISELED_RED_SANDSTONE);
    }

    private boolean isCrumblingVariant(BlockState state) {
        return state.isOf(ModBlocks.CRUMBLING_SANDSTONE) || state.isOf(ModBlocks.CRUMBLING_RED_SANDSTONE);
    }

    private int getRandomVariant(World world) {
        return world.getRandom().nextInt(4);
    }

    private ActionResult updateBlockStateAndChisel(World world, BlockPos pos, BlockState currentState, BlockState newState, PlayerEntity player, ItemUsageContext ctx) {
        world.setBlockState(pos, newState, Block.NOTIFY_ALL);
        return chiselBlock(world, pos, player, ctx);
    }

    private ActionResult chiselBlock(World world, BlockPos pos, PlayerEntity player, ItemUsageContext context) {
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1.25f, 2.0f);
        ItemStack itemStack = context.getStack();
        if (player instanceof ServerPlayerEntity) itemStack.damage(1, player, (p) -> {p.sendToolBreakStatus(context.getHand());});
        return ActionResult.SUCCESS;
    }
}
