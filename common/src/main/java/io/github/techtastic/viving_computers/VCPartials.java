package io.github.techtastic.viving_computers;

import com.jozufozu.flywheel.core.PartialModel;
import net.minecraft.resources.ResourceLocation;

public class VCPartials {
    public static PartialModel HEADSET = gear("headset");

    private static PartialModel block(String path) {
        return new PartialModel(new ResourceLocation(VivingComputersMod.MOD_ID, "block/" + path));
    }

    private static PartialModel entity(String path) {
        return new PartialModel(new ResourceLocation(VivingComputersMod.MOD_ID, "entity/" + path));
    }

    private static PartialModel item(String path) {
        return new PartialModel(new ResourceLocation(VivingComputersMod.MOD_ID, "item/" + path));
    }

    private static PartialModel gear(String path) {
        return new PartialModel(new ResourceLocation(VivingComputersMod.MOD_ID, "gear/" + path));
    }

    public static void init() {
        // init static fields
    }
}
