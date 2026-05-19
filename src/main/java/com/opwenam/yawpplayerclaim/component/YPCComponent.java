package com.opwenam.yawpplayerclaim.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Util;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.UUID;

public record YPCComponent(
        String owner,
        UUID ownerUUID,

        boolean isValidClaim,
        boolean hasActivated,

        Optional<BlockPos> pos1,
        Optional<BlockPos> pos2,

        String regionName
) {

    public static final Codec<YPCComponent> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(

                    Codec.STRING.optionalFieldOf("owner", "")
                            .forGetter(YPCComponent::owner),

                    Uuids.CODEC.optionalFieldOf("ownerUUID", Util.NIL_UUID)
                            .forGetter(YPCComponent::ownerUUID),

                    Codec.BOOL.optionalFieldOf("isValidClaim", false)
                            .forGetter(YPCComponent::isValidClaim),

                    Codec.BOOL.optionalFieldOf("hasActivated", false)
                            .forGetter(YPCComponent::hasActivated),

                    BlockPos.CODEC.optionalFieldOf("pos1")
                            .forGetter(YPCComponent::pos1),

                    BlockPos.CODEC.optionalFieldOf("pos2")
                            .forGetter(YPCComponent::pos2),

                    Codec.STRING.optionalFieldOf("regionName", "")
                            .forGetter(YPCComponent::regionName)

            ).apply(instance, YPCComponent::new));
}