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
        int randomInt = world.random.nextInt(4);
        String[] clearRainMsgs = {"The Clouds Break and Rain Stops", "The Rain stops Abruptly", "Suddenly the Rain Stops", "The Rainclouds Clear"};
        String[] clearThunderMsgs = {"The Thunder Fades into Silence", "The Thunder Quiets Down and the Sky Lightens", "The Clouds Scatter and Thunder Ceases", "Thunder Fades into the Distance"};
        String[] startRainMsgs = {"Rainclouds Begin to Form", "Rainclouds Gather in the Sky", "You Feel the Weather Changing", "First Drops of Rain Fall"};
        String[] startThunderMsgs = {"You Hear the First Clap of Thunder", "The Sky Vibrates with Thunder’s Roar", "Electric Tension Fills the Air", "A Thunderstorm is Brewing"};

        if (world.isRaining() && !world.isThundering()) {
            setClear(world);
            serverUser.sendMessage(Text.literal(clearRainMsgs[randomInt]), true);
        } else if (world.isThundering()) {
            setClear(world);
            serverUser.sendMessage(Text.literal(clearThunderMsgs[randomInt]), true);
        } else {
            boolean thunderChance = world.random.nextInt(3) > 0;
            setWeather(world, thunderChance);
            serverUser.sendMessage(Text.literal(thunderChance ? startRainMsgs[randomInt] : startThunderMsgs[randomInt]), true);
        }
    }

    private void setWeather(ServerWorld world, boolean makeRain) {
        if (makeRain) {
            setRain(world);
        } else {
            setThunder(world);
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
