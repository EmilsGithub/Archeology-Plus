package net.emilsg.archeologyplus.util.playerticking;

import net.emilsg.archeologyplus.effect.ModEffects;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.emilsg.archeologyplus.register.items.custom.IIdolItem;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

import java.util.Objects;

public class IdolTicking {
    private static int harvestIdolTicker = 760;
    private static int dayNightTicker = 20;

    public static void tickIdols(PlayerEntity player) {
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;

        if (player.getMainHandStack().getItem() instanceof IIdolItem && player.getOffHandStack().getItem() instanceof IIdolItem) {
            clearDayNightEffects(player);
            clearWaterEffects(player);
            return;
        }

        boolean isDay = serverWorld.isDay();
        boolean isNight = serverWorld.isNight();
        boolean inWater = player.isSubmergedIn(FluidTags.WATER);
        boolean onFire = player.isOnFire();

        handleWaterRelatedIdols(player, inWater);
        handleFireIdol(player, onFire);
        handleDayNightIdols(player, isDay, isNight);
        handleGlidingIdol(player);
        handleWitherIdol(player);
        handleHarvestIdol(player, serverWorld);
    }

    private static void handleHarvestIdol(PlayerEntity player, ServerWorld world) {
        ItemStack stack = null;

        if (player.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.HARVEST_IDOL)) {
            stack = player.getStackInHand(Hand.MAIN_HAND);
        } else if (player.getStackInHand(Hand.OFF_HAND).isOf(ModItems.HARVEST_IDOL)) {
            stack = player.getStackInHand(Hand.OFF_HAND);
        }

        if (stack == null) return;

        if (stack.getDamage() != 0) {
            harvestIdolTicker = harvestIdolTicker - (player.getWorld().random.nextInt(6) + 1);
            if (harvestIdolTicker <= 0 && world.isSkyVisible(player.getBlockPos()) && world.isDay()) {
                stack.setDamage(stack.getDamage() - 1);
                harvestIdolTicker = 763 + player.getWorld().random.nextBetween(138, 581);
            }
        }
    }

    private static void handleWaterRelatedIdols(PlayerEntity player, boolean inWater) {
        if (!heldItem(ModItems.SEASHELL_IDOL, player)) return;

        if (inWater) {
            clearWaterEffects(player);
            return;
        }

        applyStatusEffectIfAbsent(player, StatusEffects.CONDUIT_POWER, 200, 0);
    }

    private static void clearWaterEffects(PlayerEntity player) {
        player.removeStatusEffect(StatusEffects.CONDUIT_POWER);
    }

    private static void handleFireIdol(PlayerEntity player, boolean onFire) {
        if (heldItem(ModItems.FIRE_IDOL, player) && !onFire && !player.getItemCooldownManager().isCoolingDown(ModItems.FIRE_IDOL)) {
            applyStatusEffectIfAbsent(player, StatusEffects.FIRE_RESISTANCE, 200, 0);
        } else if (onFire) {
            player.getItemCooldownManager().set(ModItems.FIRE_IDOL, 200);
        }
    }

    private static void handleDayNightIdols(PlayerEntity player, boolean isDay, boolean isNight) {
        boolean sun = heldItem(ModItems.SUN_IDOL, player);
        boolean moon = heldItem(ModItems.MOON_IDOL, player);

        player.removeStatusEffect(isDay || sun ? ModEffects.MOONS_BLESSING : isNight || moon ? ModEffects.SUNS_BLESSING : null);

        if (sun && moon) return;

        if (!sun && !moon) {
            clearDayNightEffects(player);
            return;
        }

        dayNightTicker--;
        if (dayNightTicker > 0) return;

        if (sun && isDay) {
            applyStatusEffectIfAbsent(player, ModEffects.SUNS_BLESSING, 600, 0);
        } else if (moon && isNight) {
            applyStatusEffectIfAbsent(player, ModEffects.MOONS_BLESSING, 600, 0);
        }

        dayNightTicker = 20;
    }

    private static void clearDayNightEffects(PlayerEntity player) {
        player.removeStatusEffect(ModEffects.SUNS_BLESSING);
        player.removeStatusEffect(ModEffects.MOONS_BLESSING);
    }

    private static void handleGlidingIdol(PlayerEntity player) {
        boolean glidingIdol = heldItem(ModItems.GLIDING_IDOL, player);
        if (glidingIdol && player.fallDistance > 20f && !player.isFallFlying() && !player.getItemCooldownManager().isCoolingDown(ModItems.GLIDING_IDOL)) {
            applySlowFalling(player);
        }
    }

    private static void handleWitherIdol(PlayerEntity player) {
        if (heldItem(ModItems.WITHER_IDOL, player) && player.hasStatusEffect(StatusEffects.WITHER) && !player.getItemCooldownManager().isCoolingDown(ModItems.WITHER_IDOL)) {
            removeWitherEffect(player);
        }
    }

    private static void applyStatusEffectIfAbsent(PlayerEntity player, StatusEffect effect, int duration, int amplifier) {
        if (player == null) return;

        if (!player.hasStatusEffect(effect)) {
            player.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier, false, false, true));
        } else if (Objects.requireNonNull(player.getStatusEffect(effect)).isDurationBelow(duration) && Objects.requireNonNull(player.getStatusEffect(effect)).getAmplifier() <= amplifier) {
            player.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier, false, false, true));
        }
    }

    private static boolean heldItem(Item item, PlayerEntity player) {
        return player.getStackInHand(Hand.MAIN_HAND).isOf(item) || player.getStackInHand(Hand.OFF_HAND).isOf(item);
    }

    private static Hand getHandItemIsIn(PlayerEntity player, Item item) {
        if (player.getStackInHand(Hand.MAIN_HAND).isOf(item)) return Hand.MAIN_HAND;
        else return Hand.OFF_HAND;
    }

    private static void applySlowFalling(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 0));
        player.getItemCooldownManager().set(ModItems.GLIDING_IDOL, 400);
    }

    private static void removeWitherEffect(PlayerEntity player) {
        player.removeStatusEffect(StatusEffects.WITHER);
        player.getItemCooldownManager().set(ModItems.WITHER_IDOL, 200);
    }

}
