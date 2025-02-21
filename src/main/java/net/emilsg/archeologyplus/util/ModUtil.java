package net.emilsg.archeologyplus.util;

import net.emilsg.archeologyplus.command.ResetConfigCommand;
import net.emilsg.archeologyplus.command.UpdateConfigsCommand;
import net.emilsg.archeologyplus.config.APConfig;
import net.emilsg.archeologyplus.util.village.ModVillagerTrades;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

public class ModUtil {
    public static void registerModUtil() {
        ModVillagerTrades.registerTrades();

        registerCommands();
        registerJoinServerEvents();
    }

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(ResetConfigCommand::register);
        CommandRegistrationCallback.EVENT.register(UpdateConfigsCommand::register);
    }

    private static void registerJoinServerEvents() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ServerPlayConnectionEvents.JOIN.register((handler, sender, server1) -> {
                ServerPlayerEntity player = handler.player;
                if (server1.getPlayerManager().isOperator(player.getGameProfile())) {
                    boolean isCurrent = APConfig.getInstance().isConfigVersionCurrent();
                    if (!isCurrent) {

                        MutableText operatorOnlyText = Text.translatable("archeologyplus.commands.config.op_only").formatted(Formatting.RED);

                        MutableText startText = Text.translatable("archeologyplus.commands.config.config_update_prompt", APConfig.getInstance().getFileVersionNumber(APConfig.getInstance().getCurrentFileVersion()));

                        MutableText versionText = Text.literal("(" + APConfig.FILE_VERSION + ")");

                        MutableText updatePromptText = Texts.bracketed(
                                Text.translatable("archeologyplus.commands.config.config_update")).styled(style -> style.withColor(Formatting.GREEN)
                                .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/archeologyplus update_configs"))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("archeologyplus.commands.config.update_tooltip"))));

                        player.sendMessage(Text.translatable("archeologyplus.commands.config.config_prompt", operatorOnlyText, startText, versionText, updatePromptText), false);
                    }
                }
            });
        });
    }

}

