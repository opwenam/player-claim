package com.opwenam.yawpplayerclaim.component;

import com.opwenam.yawpplayerclaim.YawpPlayerClaim;
import net.minecraft.component.DataComponentType;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.BuiltinRegistries;

public class ModComponents {

    public static final DataComponentType<YPCComponent> MY_CUSTOM_COMPONENT = Registry.register(
            BuiltinRegistries.DATA_COMPONENT_TYPE,
            new Identifier(YawpPlayerClaim.MOD_ID, "custom"),
            DataComponentType.<YPCComponent>builder().persistent(YPCComponent.CODEC).build()
    );

    protected static void initialize() {
        YawpPlayerClaim.LOGGER.info("Registering {} components", YawpPlayerClaim.MOD_ID);
    }
}
