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

    public ChiselItem(Settings settings, String description, Formatting formatting) {
        super(settings, description, formatting);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();

        if(state.getBlock() instanceof HieroglyphBlock) {
            if(world.isClient) {
                world.addBlockBreakParticles(pos, state);
                return ActionResult.SUCCESS;
            }
            world.setBlockState(pos, state.cycle(ModProperties.VARIANT_3), Block.NOTIFY_ALL);
            return chiselBlock(world, pos, player, context);
        } else if (state.isOf(Blocks.SMOOTH_SANDSTONE) || state.isOf(Blocks.SANDSTONE) || state.isOf(Blocks.CUT_SANDSTONE)) {
            if(world.isClient) {
                world.addBlockBreakParticles(pos, state);
                return ActionResult.SUCCESS;
            }
            world.setBlockState(pos, ModBlocks.SANDSTONE_HIEROGLYPHS.getDefaultState().with(ModProperties.VARIANT_3, world.getRandom().nextInt(4)), Block.NOTIFY_ALL);
            return chiselBlock(world, pos, player, context);
        } else if (state.isOf(Blocks.SMOOTH_RED_SANDSTONE) || state.isOf(Blocks.RED_SANDSTONE) || state.isOf(Blocks.CUT_RED_SANDSTONE)) {
            if(world.isClient) {
                world.addBlockBreakParticles(pos, state);
                return ActionResult.SUCCESS;
            }
            world.setBlockState(pos, ModBlocks.RED_SANDSTONE_HIEROGLYPHS.getDefaultState().with(ModProperties.VARIANT_3, world.getRandom().nextInt(4)), Block.NOTIFY_ALL);
            return chiselBlock(world, pos, player, context);
        } else if (state.isOf(ModBlocks.CRUMBLING_SANDSTONE) || state.isOf(ModBlocks.CRUMBLING_RED_SANDSTONE)) {
            if(world.isClient) {
                world.addBlockBreakParticles(pos, state);
                return ActionResult.SUCCESS;
            }
            world.setBlockState(pos, state.cycle(ModProperties.CRUMBLE_LEVEL), Block.NOTIFY_ALL);
            return chiselBlock(world, pos, player, context);
        }

        return ActionResult.PASS;
    }

    private ActionResult chiselBlock(World world, BlockPos pos, PlayerEntity player, ItemUsageContext context) {
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1.25f, 2.0f);
        ItemStack itemStack = context.getStack();
        if (player instanceof ServerPlayerEntity) itemStack.damage(1, player, (p) -> {p.sendToolBreakStatus(context.getHand());});
        return ActionResult.SUCCESS;
    }
}
