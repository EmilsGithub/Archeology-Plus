package net.emilsg.archeologyplus.register.items.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HarvestIdol extends DescriptionItem {
    protected static final Map<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> TILLING_ACTIONS;
    int ticker = 760;

    public HarvestIdol(Settings settings, String description, Formatting formatting) {
        super(settings, description, formatting);
    }

    public HarvestIdol(Settings settings, String description, Formatting formatting, String subDescription, Formatting subFormatting) {
        super(settings, description, formatting, subDescription, subFormatting);
    }


    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient) {
            if (stack.getDamage() != 0) {
                ticker = ticker - (entity.getWorld().random.nextInt(6) + 1);
                if (ticker <= 0) {
                    stack.setDamage(stack.getDamage() - 1);
                    ticker = 763 + entity.getWorld().random.nextBetween(138, 581);
                }
            }
        }
    }

    public ActionResult useOnBlock(ItemUsageContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = ctx.getStack();
        Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair = TILLING_ACTIONS.get(world.getBlockState(pos).getBlock());

        if(stack.getDamage() < this.getMaxDamage()) {
            if (useOnFertilizable(world, pos.down(), state)) {
                if (!world.isClient) {
                    world.syncWorldEvent(1505, pos, 0);
                    damageWithoutBreaking(ctx, stack);
                }
                return ActionResult.success(world.isClient);
            }
        }

        return this.tillGround(ctx, world, pos, pair, stack);
    }

    public ActionResult tillGround(ItemUsageContext context, World world, BlockPos blockPos, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair, ItemStack stack) {
        if (pair == null) {
            return ActionResult.PASS;
        } else {
            Predicate<ItemUsageContext> predicate = pair.getFirst();
            Consumer<ItemUsageContext> consumer = pair.getSecond();
            if(stack.getDamage() < this.getMaxDamage()) {
                if (predicate.test(context)) {
                    PlayerEntity playerEntity = context.getPlayer();
                    world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    damageWithoutBreaking(context, stack);
                    if (!world.isClient) consumer.accept(context);
                    return ActionResult.success(world.isClient);
                }
            }
        }

        return ActionResult.PASS;
    }

    public static void damageWithoutBreaking(ItemUsageContext ctx, ItemStack stack) {
        if(!Objects.requireNonNull(ctx.getPlayer()).getAbilities().creativeMode) stack.setDamage(stack.getDamage() + 1);
    }

    public static boolean useOnFertilizable(World world, BlockPos cropPos, BlockState state) {
        if (state.getBlock() instanceof Fertilizable fertilizable) {
            if (fertilizable.isFertilizable(world, cropPos, state, world.isClient)) {
                if (world instanceof ServerWorld) {
                    if (fertilizable.canGrow(world, world.random, cropPos, state)) {
                        fertilizable.grow((ServerWorld)world, world.random, cropPos.up(), state);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static Consumer<ItemUsageContext> createTillAction(BlockState result) {
        return (context) -> {
            context.getWorld().setBlockState(context.getBlockPos(), result, 11);
            context.getWorld().emitGameEvent(GameEvent.BLOCK_CHANGE, context.getBlockPos(), GameEvent.Emitter.of(context.getPlayer(), result));
        };
    }

    public static Consumer<ItemUsageContext> createTillAndDropAction(BlockState result, ItemConvertible droppedItem) {
        return (context) -> {
            context.getWorld().setBlockState(context.getBlockPos(), result, 11);
            context.getWorld().emitGameEvent(GameEvent.BLOCK_CHANGE, context.getBlockPos(), GameEvent.Emitter.of(context.getPlayer(), result));
            Block.dropStack(context.getWorld(), context.getBlockPos(), context.getSide(), new ItemStack(droppedItem));
        };
    }

    public static boolean canTillFarmland(ItemUsageContext context) {
        return context.getSide() != Direction.DOWN && context.getWorld().getBlockState(context.getBlockPos().up()).isAir();
    }

    static {
        TILLING_ACTIONS = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Pair.of(HarvestIdol::canTillFarmland, createTillAction(Blocks.FARMLAND.getDefaultState())), Blocks.DIRT_PATH, Pair.of(HarvestIdol::canTillFarmland, createTillAction(Blocks.FARMLAND.getDefaultState())), Blocks.DIRT, Pair.of(HarvestIdol::canTillFarmland, createTillAction(Blocks.FARMLAND.getDefaultState())), Blocks.COARSE_DIRT, Pair.of(HarvestIdol::canTillFarmland, createTillAction(Blocks.DIRT.getDefaultState())), Blocks.ROOTED_DIRT, Pair.of((itemUsageContext) -> true, createTillAndDropAction(Blocks.DIRT.getDefaultState(), Items.HANGING_ROOTS))));
    }
}
