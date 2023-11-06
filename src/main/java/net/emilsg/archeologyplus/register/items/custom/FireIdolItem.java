package net.emilsg.archeologyplus.register.items.custom;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Objects;

public class FireIdolItem extends DescriptionItem{

    public FireIdolItem(Settings settings, String description, Formatting formatting) {
        super(settings, description, formatting);
    }

    public FireIdolItem(Settings settings, String description, Formatting formatting, String subDescription, Formatting subFormatting) {
        super(settings, description, formatting, subDescription, subFormatting);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        ItemStack itemStack = context.getStack();
        BlockState blockState = world.getBlockState(blockPos);
        if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
            BlockPos blockPos2 = blockPos.offset(context.getSide());
            if (AbstractFireBlock.canPlaceAt(world, blockPos2, context.getHorizontalPlayerFacing())) {
                world.playSound(playerEntity, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                world.setBlockState(blockPos2, blockState2, 11);
                world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, blockPos);
                if (playerEntity instanceof ServerPlayerEntity) {
                    Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos2, itemStack);
                    damageWithoutBreaking(context, itemStack);
                }

                return ActionResult.success(world.isClient());
            } else {
                return ActionResult.FAIL;
            }
        } else {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
            world.setBlockState(blockPos, blockState.with(Properties.LIT, true), 11);
            world.emitGameEvent(playerEntity, GameEvent.BLOCK_CHANGE, blockPos);
            if (playerEntity != null) {
                damageWithoutBreaking(context, itemStack);
            }

            return ActionResult.success(world.isClient());
        }
    }

    public static void damageWithoutBreaking(ItemUsageContext ctx, ItemStack stack) {
        if(!Objects.requireNonNull(ctx.getPlayer()).getAbilities().creativeMode) stack.setDamage(stack.getDamage() + 1);
    }

}
