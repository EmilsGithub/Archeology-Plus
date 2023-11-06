package net.emilsg.archeologyplus.register.items.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DescriptionItem extends Item {
    private final String description;
    private final Formatting formatting;
    private final String subDescription;
    private final Formatting subFormatting;

    public DescriptionItem(Settings settings, String description, Formatting formatting) {
        this(settings, description, formatting, null, null);
    }

    public DescriptionItem(Settings settings, String description, Formatting formatting, String subDescription, Formatting subFormatting) {
        super(settings);
        this.description = description;
        this.formatting = formatting;
        this.subDescription = subDescription;
        this.subFormatting = subFormatting;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(description).formatted(formatting));
        if (subDescription != null && subFormatting != null && !Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("description.shift.prompt").formatted(subFormatting));
        }
        if (subDescription != null && subFormatting != null && Screen.hasShiftDown()) {
            tooltip.add(Text.translatable(subDescription).formatted(subFormatting));
        }
    }
}
