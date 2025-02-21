package net.emilsg.archeologyplus.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.List;

public class ExpulsionEnchantment extends Enchantment {

    public ExpulsionEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentTarget.ARMOR_CHEST, slots);
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if (user.getWorld() instanceof ServerWorld serverWorld && serverWorld.random.nextFloat() < (0.2f * level)) {
            double knockBackIntensity = level * 1.125D;
            double radius = 3.0D + level;

            Box area = new Box(user.getBlockPos()).expand(radius, 0, radius);

            List<LivingEntity> nearbyEntities = serverWorld.getEntitiesByClass(LivingEntity.class, area, e ->
                    e != user && user.squaredDistanceTo(e) <= radius * radius);


            if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
                player.getEquippedStack(EquipmentSlot.CHEST).damage(1, player, e -> e.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
            } else {
                user.getEquippedStack(EquipmentSlot.CHEST).damage(1, user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
            }

            for (LivingEntity entity : nearbyEntities) {
                double dx = entity.getX() - user.getX();
                double dz = entity.getZ() - user.getZ();
                double distance = Math.sqrt(dx * dx + dz * dz);
                entity.takeKnockback(knockBackIntensity, -dx / distance, -dz / distance);

                serverWorld.spawnParticles(ParticleTypes.EXPLOSION, entity.getX(), entity.getY() + entity.getHeight() / 2.0, entity.getZ(), 1, 0, 0, 0, 0);
            }
        }
    }
}
