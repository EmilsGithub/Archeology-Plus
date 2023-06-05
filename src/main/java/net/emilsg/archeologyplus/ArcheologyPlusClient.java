package net.emilsg.archeologyplus;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

import java.util.Arrays;
import java.util.List;

public class ArcheologyPlusClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        List<Block> blocksToRender = Arrays.asList(

        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), blocksToRender.toArray(new Block[0]));
    }

}
