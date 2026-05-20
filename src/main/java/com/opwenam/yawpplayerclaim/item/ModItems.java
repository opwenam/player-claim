package com.opwenam.yawpplayerclaim.item;

import com.opwenam.yawpplayerclaim.YawpPlayerClaim;
import com.opwenam.yawpplayerclaim.component.ModComponents;
import com.opwenam.yawpplayerclaim.component.YPCComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Optional;

public class ModItems {
    //public static final Item YPC_WAND = Registry.register(
    //        Registries.ITEM,
    //        Identifier.of(YawpPlayerClaim.MOD_ID, "ypc_wand"),
    //        new YPCWand(new Item.Settings())
    //);

    public static final Item YPC_WAND = Registry.register(
            Registries.ITEM,
            Identifier.of(YawpPlayerClaim.MOD_ID, "ypc_wand"),
            new YPCWand(
                    new Item.Settings().component(
                            ModComponents.PLAYER_CLAIM,
                            new YPCComponent(
                                    "",
                                    Util.NIL_UUID,

                                    false,
                                    false,

                                    Optional.empty(),
                                    Optional.empty(),

                                    ""
                            )
                    )
            )
    );

    public static void registerItems() {
        YawpPlayerClaim.LOGGER.info("Registered {} Items", YawpPlayerClaim.MOD_ID);
    }
}
