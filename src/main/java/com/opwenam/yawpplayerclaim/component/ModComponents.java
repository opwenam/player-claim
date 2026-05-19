package com.opwenam.yawpplayerclaim.component;

import com.opwenam.yawpplayerclaim.YawpPlayerClaim;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModComponents {
//    public static final ComponentType<YPCComponent> PLAYER_CLAIM = register("player_claim", builder -> builder.codec(YPCComponent.CODEC));

    //    private static <T>ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
//        return Registry.register(Registries.DATA_COMPONENT_TYPE,
//               Identifier.of(YawpPlayerClaim.MOD_ID, id) ,
//                builderOperator.apply(ComponentType.builder()).build());
//    }

    public static final ComponentType<YPCComponent> PLAYER_CLAIM =
            Registry.register(
                    Registries.DATA_COMPONENT_TYPE,
                    Identifier.of(YawpPlayerClaim.MOD_ID, "player_claim"),
                    ComponentType.<YPCComponent>builder()
                            .codec(YPCComponent.CODEC)
                            .build()
            );

    public static void registerDataComponentTypes(){
        // Force class initialization so static fields (which register the component types) run.
        YawpPlayerClaim.LOGGER.info("Registered {} components", YawpPlayerClaim.MOD_ID);
    }
}
