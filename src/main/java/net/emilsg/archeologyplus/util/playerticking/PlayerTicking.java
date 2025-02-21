package net.emilsg.archeologyplus.util.playerticking;

import net.emilsg.archeologyplus.config.APConfig;
import net.emilsg.archeologyplus.effect.ModEffects;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.emilsg.archeologyplus.util.tags.ModStructureTags;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;

public class PlayerTicking {
    private static final ItemStack protectionIdolStack = new ItemStack(ModItems.IDOL_OF_PROTECTION);
    private static int ticker = 200;

    public static void doPlayerTick(PlayerEntity player) {
        if (player instanceof ClientPlayerEntity clientPlayerEntity && clientPlayerEntity.hasStatusEffect(ModEffects.CERTAIN_DEATH)) {
            World world = clientPlayerEntity.getWorld();
            spawnDeathParticles(world, clientPlayerEntity.getPos());
        }

        if (player instanceof ServerPlayerEntity serverPlayer && serverPlayer.getWorld() instanceof ServerWorld serverWorld && APConfig.getInstance().getOrDefault(APConfig.DO_CERTAIN_DEATH, true)) {

            if (ticker <= 0) {
                tryToApplyCertainDeath(serverPlayer, serverWorld);
                ticker = 200;
            }

            ticker--;
        }
    }

    private static void tryToApplyCertainDeath(ServerPlayerEntity serverPlayer, ServerWorld serverWorld) {
        if (serverPlayer.getInventory().contains(protectionIdolStack)) {
            serverPlayer.removeStatusEffect(ModEffects.CERTAIN_DEATH);
        }

        boolean appliesCertainDeath = isPlayerInStructure(serverPlayer, serverWorld, ModStructureTags.APPLIES_CERTAIN_DEATH);

        if (appliesCertainDeath && !serverPlayer.getInventory().contains(protectionIdolStack)) {
            serverPlayer.addStatusEffect(new StatusEffectInstance(ModEffects.CERTAIN_DEATH, 1200 * APConfig.getInstance().getOrDefault(APConfig.CERTAIN_DEATH_DURATION, 120), 0, false, false, true));
        }
    }

    public static boolean isPlayerInStructure(ServerPlayerEntity serverPlayer, ServerWorld world, TagKey<Structure> structureKey) {
        BlockPos playerPos = serverPlayer.getBlockPos();
        StructureStart structureStart = world.getStructureAccessor().getStructureContaining(playerPos, structureKey);

        if (structureStart != null && structureStart.hasChildren()) {
            return structureStart.getBoundingBox().contains(playerPos);
        }

        return false;
    }

    public static void spawnDeathParticles(World world, Vec3d pos) {
        Random random = world.getRandom();
        DefaultParticleType defaultParticleType = ParticleTypes.ASH;
        world.addImportantParticle(defaultParticleType, true, pos.getX() + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), pos.getY() + 0.25 + random.nextDouble() + random.nextDouble(), pos.getZ() + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);
    }
}
