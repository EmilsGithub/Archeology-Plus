package net.emilsg.archeologyplus.util.village;

import com.mojang.datafixers.util.Pair;
import net.emilsg.archeologyplus.ArcheologyPlus;
import net.emilsg.archeologyplus.mixin.StructurePoolAccessorMixin;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AlterVillageStructures {

    private static final String[] biomes = {"plains", "desert", "taiga", "snowy", "savanna"};

    public static void addHouses() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            for (String biome : biomes) {
                Identifier vanillaPoolId = new Identifier("minecraft:village/" + biome + "/houses");
                Identifier aPlusHouseId = new Identifier(ArcheologyPlus.MOD_ID, "village/" + biome + "/houses/" + biome + "_archeologist_1");
                addToPool(server, vanillaPoolId, aPlusHouseId);
            }
        });


    }

    private static void addToPool(MinecraftServer server, Identifier poolId, Identifier nbtId) {
        int weight = 4;

        RegistryEntry.Reference<StructureProcessorList> empty = server.getRegistryManager().get(RegistryKeys.PROCESSOR_LIST).entryOf(RegistryKey.of(RegistryKeys.PROCESSOR_LIST, new Identifier("minecraft", "empty")));

        server.getRegistryManager().get(RegistryKeys.TEMPLATE_POOL).getOrEmpty(poolId).ifPresentOrElse(structurePool -> {
            SinglePoolElement archeologistPool = StructurePoolElement.ofProcessedSingle(nbtId.toString(), empty)
                    .apply(StructurePool.Projection.RIGID);

            List<Pair<StructurePoolElement, Integer>> elementCounts = new ArrayList<>(((StructurePoolAccessorMixin) structurePool).getElementCounts());

            elementCounts.add(Pair.of(archeologistPool, weight));
            ((StructurePoolAccessorMixin) structurePool).setElementCounts(elementCounts);

            IntStream.range(0, weight).forEach(value -> ((StructurePoolAccessorMixin) structurePool).getElements().add(archeologistPool));
        }, () -> ArcheologyPlus.LOGGER.warn("No structure pool found for" + poolId + "."));
    }
}
