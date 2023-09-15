package net.emilsg.archeologyplus.register.items.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

public class RainIdolItem extends DescriptionItem {
    public static final IntProvider CLEAR_WEATHER_DURATION_PROVIDER = UniformIntProvider.create(12000, 180000);
    public static final IntProvider RAIN_WEATHER_DURATION_PROVIDER = UniformIntProvider.create(12000, 24000);
    public static final IntProvider THUNDER_WEATHER_DURATION_PROVIDER = UniformIntProvider.create(3600, 15600);

    public RainIdolItem(Settings settings, String description, Formatting formatting) {
        super(settings, description, formatting);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!user.getItemCooldownManager().isCoolingDown(this) && !world.isClient) {
            this.toggleWeather((ServerWorld) world, user);
            for (PlayerEntity player : world.getPlayers()) {
                player.getItemCooldownManager().set(this, 2400);
            }
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }

    private void toggleWeather(ServerWorld world, PlayerEntity serverUser) {
        if (world.isRaining()) {
            setClear(world);
            serverUser.sendMessage(Text.literal("The Skies Begin to Clear"), true);
        } else {
            if (world.random.nextInt(3) > 0) {
                setRain(world);
                serverUser.sendMessage(Text.literal("Rainclouds Begin to Form"), true);
            } else {
                setThunder(world);
                serverUser.sendMessage(Text.literal("A Thunderstorm is Brewing"), true);
            }
        }

    }

    private static int processDuration(ServerWorld world, IntProvider provider) {
        return provider.get(world.getRandom());
    }

    private static void setClear(ServerWorld world) {
        world.setWeather(processDuration(world, CLEAR_WEATHER_DURATION_PROVIDER), 0, false, false);
    }

    private static void setRain(ServerWorld world) {
        world.setWeather(0, processDuration(world, RAIN_WEATHER_DURATION_PROVIDER), true, false);
    }

    private static void setThunder(ServerWorld world) {
        world.setWeather(0, processDuration(world, THUNDER_WEATHER_DURATION_PROVIDER), true, true);
    }
}
