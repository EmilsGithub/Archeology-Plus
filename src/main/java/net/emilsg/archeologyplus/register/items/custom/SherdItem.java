package net.emilsg.archeologyplus.register.items.custom;

import net.minecraft.item.Item;

public class SherdItem extends Item {
    private final String patternName;

    public SherdItem(Settings settings, String patternName) {
        super(settings);
        this.patternName = patternName;
    }

    public String getPatternName() {
        return patternName;
    }
}
