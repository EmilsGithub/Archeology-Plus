package net.emilsg.archeologyplus.register.blocks.entity;

import net.emilsg.archeologyplus.enchantment.ModEnchantments;
import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.blocks.custom.SpikeBlock;
import net.emilsg.archeologyplus.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class SpikeTrapBlockEntity extends BlockEntity {
    private static int ticker = 500;
    private static boolean tampered = false;

    public SpikeTrapBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPIKE_TRAP_BLOCK_ENTITY, pos, state);
    }

    public static <T extends BlockEntity> void serverTick(World world, BlockPos pos, BlockState state, T t) {
        if (!world.getBlockState(pos.up()).isReplaceable() && !(world.getBlockState(pos.up()).getBlock() instanceof SpikeBlock))
            return;

        ticker = ticker - (world.random.nextInt(2) + 1);

        if(world.random.nextInt(10) == 0 && !tampered) {
            Box areaToCheck = new Box(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
            List<PlayerEntity> playersAbove = world.getEntitiesByClass(PlayerEntity.class, areaToCheck, player -> true);

            if (!playersAbove.isEmpty()) {
                float bypassLevel = 0;

                for (PlayerEntity player : playersAbove) {
                    int enchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.BYPASS_ENCHANTMENT, player.getEquippedStack(EquipmentSlot.LEGS));
                    if(enchantmentLevel > bypassLevel) bypassLevel = enchantmentLevel;
                }

                tampered = true;

                if(world.random.nextFloat() < bypassLevel / 4) {
                    ticker = 1000;
                    world.playSound(null, pos, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, 0.75f);
                    return;
                }
            }
        }

        if (ticker <= 0) {
            boolean deployed = state.get(ModProperties.DEPLOYED);

            world.setBlockState(pos, state.with(ModProperties.DEPLOYED, !deployed), Block.NOTIFY_ALL);
            world.setBlockState(pos.up(), !deployed ? ModBlocks.SPIKES.getDefaultState().with(Properties.WATERLOGGED, world.getFluidState(pos.up()).getFluid() == Fluids.WATER) : world.getFluidState(pos.up()).getFluid() == Fluids.WATER ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
            world.playSound(null, pos, !deployed ? SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN : SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, 1.0f + (world.getRandom().nextFloat() / 5));
            tampered = false;
            ticker = 500;
        }
    }
}
