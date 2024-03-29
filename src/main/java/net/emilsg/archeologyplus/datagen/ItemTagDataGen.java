package net.emilsg.archeologyplus.datagen;

import net.emilsg.archeologyplus.register.items.ModItemTags;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagDataGen extends FabricTagProvider.ItemTagProvider {

    public ItemTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.DECORATED_POT_SHERDS).add(
                ModItems.LOADER_POTTERY_SHERD,
                ModItems.MASTER_POTTERY_SHERD,
                ModItems.MERCHANT_POTTERY_SHERD,
                ModItems.HOP_POTTERY_SHERD,
                ModItems.SIGHT_POTTERY_SHERD,
                ModItems.NIGHT_POTTERY_SHERD,
                ModItems.MIGHT_POTTERY_SHERD,
                ModItems.FRIGHT_POTTERY_SHERD,
                ModItems.LIGHT_POTTERY_SHERD,
                ModItems.FLIGHT_POTTERY_SHERD,
                ModItems.CHOMP_POTTERY_SHERD,
                ModItems.REVIVE_POTTERY_SHERD,
                ModItems.BUTTERFLY_POTTERY_SHERD,
                ModItems.NAUTILUS_POTTERY_SHERD,
                ModItems.HALO_POTTERY_SHERD,
                ModItems.DEVIL_POTTERY_SHERD
        );

        getOrCreateTagBuilder(ModItemTags.OVERWORLD_IDOLS).add(
                ModItems.SUN_IDOL,
                ModItems.MOON_IDOL,
                ModItems.SEASHELL_IDOL,
                ModItems.RAIN_IDOL,
                ModItems.FIRE_IDOL,
                ModItems.HARVEST_IDOL,
                ModItems.GLIDING_IDOL,
                ModItems.WITHER_IDOL
        );
    }
}
