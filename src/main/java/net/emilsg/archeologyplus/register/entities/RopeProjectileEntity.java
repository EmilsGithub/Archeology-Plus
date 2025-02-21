package net.emilsg.archeologyplus.register.entities;

import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.blocks.custom.RopeBlock;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RopeProjectileEntity extends ThrownItemEntity {

    public RopeProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public RopeProjectileEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.THROWN_ROPE_PROJECTILE, livingEntity, world);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        World world = this.getWorld();

        if(!(world instanceof ServerWorld serverWorld)) return;

        serverWorld.sendEntityStatus(this, (byte)3);

        BlockPos pos = blockHitResult.getBlockPos();
        BlockState blockState = serverWorld.getBlockState(pos);
        Direction hitResultSide = blockHitResult.getSide();
        Vec3d vec3dPos = blockHitResult.getPos();

        if (hitResultSide == Direction.UP && !(blockState.getBlock() instanceof RopeBlock)) {
            ItemEntity ropeItemEntity = new ItemEntity(serverWorld, vec3dPos.getX(), vec3dPos.getY(), vec3dPos.getZ(), new ItemStack(ModItems.ROPE_BUNDLE));
            ropeItemEntity.setPickupDelay(10);
            serverWorld.spawnEntity(ropeItemEntity);
            serverWorld.playSound(null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.BLOCKS, 1f, 0.5f + (serverWorld.random.nextFloat() / 2));
            this.discard();
            return;
        } else {
            int ropesToDrop = RopeBlock.placeRopeForItem(serverWorld, blockState.getBlock() instanceof RopeBlock ? pos : pos.offset(hitResultSide), 9, 9);
            if (ropesToDrop > 0) {
                ItemEntity ropeItemEntity = new ItemEntity(serverWorld, vec3dPos.getX(), vec3dPos.getY() -1, vec3dPos.getZ(), new ItemStack(ropesToDrop == 9 ? ModItems.ROPE_BUNDLE : ModBlocks.ROPE, ropesToDrop == 9 ? 1 : ropesToDrop));
                ropeItemEntity.setPickupDelay(10);
                serverWorld.spawnEntity(ropeItemEntity);
                serverWorld.playSound(null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.BLOCKS, 1f, 0.5f + (serverWorld.random.nextFloat() / 2));
            }
            this.discard();
        }

        this.discard();
    }
}
