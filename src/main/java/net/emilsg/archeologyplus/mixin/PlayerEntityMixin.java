package net.emilsg.archeologyplus.mixin;

import net.emilsg.archeologyplus.register.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private static final UUID SPEED_UUID = UUID.fromString("5e1c2f38-3bb9-4b17-86df-a9a443f1078e");
    private static final EntityAttributeModifier SPEED_MODIFIER = new EntityAttributeModifier(SPEED_UUID, "Idol speed attribute", 0.01, EntityAttributeModifier.Operation.ADDITION);
    private static final UUID RESISTANCE_UUID = UUID.fromString("926bacf9-f1f2-4d91-8d7c-28585c7edfa7");
    private static final EntityAttributeModifier RESISTANCE_MODIFIER = new EntityAttributeModifier(RESISTANCE_UUID, "Idol resistance attribute", 2, EntityAttributeModifier.Operation.ADDITION);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract Iterable<ItemStack> getHandItems();

    @Shadow public abstract boolean isPlayer();

    @Shadow public abstract ItemCooldownManager getItemCooldownManager();

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickIdolItems(CallbackInfo ci) {
        World world = this.getWorld();
        boolean sunAndDay = heldItem(ModItems.SUN_IDOL) && world.isDay();
        boolean moonAndNight = heldItem(ModItems.MOON_IDOL) && world.isNight();
        boolean inWater = this.isSubmergedIn(FluidTags.WATER);
        boolean shell = heldItem(ModItems.SEASHELL_IDOL);
        boolean onFire = this.isOnFire();
        boolean fireIdol = heldItem(ModItems.FIRE_IDOL);
        boolean glidingIdol = heldItem(ModItems.GLIDING_IDOL);
        boolean witherIdol = heldItem(ModItems.WITHER_IDOL);
        if(!world.isClient) {
            if(shell && !inWater) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 200, 0, false, false, true));
            } else if (shell && inWater) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 200, 0, false, false, true));
            }
            if(fireIdol && !onFire) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0, false, false, true));
            }
            if ((sunAndDay || moonAndNight) && shouldApplyModifiers()) {
                this.addModifiers();
            } else if (!shouldApplyModifiers() && !sunAndDay && !moonAndNight) {
                removeModifiers();
            }
            if (glidingIdol && this.fallDistance > 20f && !this.isFallFlying() && !this.getItemCooldownManager().isCoolingDown(ModItems.GLIDING_IDOL)) {
                applySlowFalling();
            }
            if (witherIdol && this.hasStatusEffect(StatusEffects.WITHER) && !this.getItemCooldownManager().isCoolingDown(ModItems.WITHER_IDOL)) {
                removeWitherEffect();
            }
        }
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void modifyBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cirF) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        ItemStack mainHand = player.getMainHandStack();
        ItemStack offHand = player.getOffHandStack();

        float originalSpeed = cirF.getReturnValue();

        boolean hasAquaAffinity = EnchantmentHelper.hasAquaAffinity(player);
        boolean isHoldingSeashellItem = (mainHand.getItem() == ModItems.SEASHELL_IDOL || offHand.getItem() == ModItems.SEASHELL_IDOL);

        if (player.isSubmergedIn(FluidTags.WATER) && !hasAquaAffinity && !isHoldingSeashellItem) {
            originalSpeed /= 5.0F;
        }

        cirF.setReturnValue(originalSpeed);
    }

    private boolean shouldApplyModifiers() {
        EntityAttributeInstance speedAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        EntityAttributeInstance healthAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        return Objects.requireNonNull(speedAttribute).getModifier(SPEED_UUID) == null && Objects.requireNonNull(healthAttribute).getModifier(RESISTANCE_UUID) == null;
    }

    private boolean heldItem(Item item) {
        return getStackInHand(Hand.MAIN_HAND).isOf(item) || getStackInHand(Hand.OFF_HAND).isOf(item);
    }
    private void addModifiers() {
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).addTemporaryModifier(SPEED_MODIFIER);
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS)).addTemporaryModifier(RESISTANCE_MODIFIER);
    }
    private void removeModifiers() {
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).removeModifier(SPEED_MODIFIER);
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS)).removeModifier(RESISTANCE_MODIFIER);
    }
    private void applySlowFalling() {
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 0));
        this.getItemCooldownManager().set(ModItems.GLIDING_IDOL, 400);
    }
    private void removeWitherEffect() {
        this.removeStatusEffect(StatusEffects.WITHER);
        this.getItemCooldownManager().set(ModItems.WITHER_IDOL, 200);
    }
}
