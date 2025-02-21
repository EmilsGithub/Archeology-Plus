package net.emilsg.archeologyplus.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.emilsg.archeologyplus.config.APConfig;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class UpdateConfigsCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("archeologyplus")
                .requires(source -> source.hasPermissionLevel(3))
                .then(CommandManager.literal("update_configs").executes(UpdateConfigsCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        APConfig.getInstance().addMissingConfigsAndUpdateVersion();
        MutableText operatorOnlyText = Text.translatable("archeologyplus.commands.config.op_only").formatted(Formatting.RED);
        context.getSource().sendFeedback(() -> Text.translatable("archeologyplus.commands.config.update_configs", operatorOnlyText).formatted(Formatting.YELLOW).formatted(Formatting.ITALIC), true);
        return 1;
    }


}
