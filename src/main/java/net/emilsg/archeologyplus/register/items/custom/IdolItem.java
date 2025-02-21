package net.emilsg.archeologyplus.register.items.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class IdolItem extends DescriptionItem implements IIdolItem {

    public IdolItem(Settings settings, String description, Formatting formatting) {
        super(settings, description, formatting);
    }

    public IdolItem(Settings settings, String description, Formatting formatting, String subDescription, Formatting subFormatting) {
        super(settings, description, formatting, subDescription, subFormatting);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        boolean shouldPlaySound = false;

        List<ArrowEntity> arrows = world.getEntitiesByType(TypeFilter.instanceOf(ArrowEntity.class),
                new Box(user.getX()-5,user.getY()-5,user.getZ()-5,user.getX()+5,user.getY()+5,user.getZ()+5),
                EntityPredicates.VALID_ENTITY);

        if(!arrows.isEmpty()) {
            for (ArrowEntity arrow : arrows) {
                BlockPos arrowPos = arrow.getBlockPos();
                boolean isReadyToDiscard = false;

                if(arrow.getVelocity().lengthSquared() > 1) {
                    world.addParticle(ParticleTypes.FLASH, arrowPos.getX(), arrow.getY(), arrowPos.getZ(), 0.0, 0.0, 0.0);
                    isReadyToDiscard = true;
                }

                if (isReadyToDiscard) {
                    arrow.discard();
                    shouldPlaySound = true;
                }
            }

            if(shouldPlaySound) {
                world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.MASTER, 1f, 1f);
            }

            return TypedActionResult.success(user.getStackInHand(hand), true);
        }

        return super.use(world, user, hand);
    }
}
