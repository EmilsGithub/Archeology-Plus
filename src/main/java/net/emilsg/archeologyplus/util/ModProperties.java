package net.emilsg.archeologyplus.util;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {
    public static final IntProperty CRUMBLE_LEVEL = IntProperty.of("crumble_level", 0, 3);
    public static final IntProperty VARIANT_3 = IntProperty.of("variant", 0, 3);
    public static final BooleanProperty WILL_BREAK = BooleanProperty.of("will_break");
    public static final BooleanProperty DEPLOYED = BooleanProperty.of("deployed");
    public static final BooleanProperty DROPS_LOOT = BooleanProperty.of("drops_loot");
    public static final BooleanProperty END = BooleanProperty.of("end");
    public static final BooleanProperty TOP = BooleanProperty.of("top");
    public static final BooleanProperty HIDDEN = BooleanProperty.of("hidden");
}
