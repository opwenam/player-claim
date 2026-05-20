package com.opwenam.yawpplayerclaim.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
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

    public static final PacketCodec<RegistryByteBuf, YPCComponent> PACKET_CODEC =
            PacketCodec.of(
                    (value, buf) -> {
                        PacketCodecs.STRING.encode(buf, value.owner());
                        Uuids.PACKET_CODEC.encode(buf, value.ownerUUID());

                        PacketCodecs.BOOL.encode(buf, value.isValidClaim());
                        PacketCodecs.BOOL.encode(buf, value.hasActivated());

                        PacketCodecs.optional(BlockPos.PACKET_CODEC).encode(buf, value.pos1());
                        PacketCodecs.optional(BlockPos.PACKET_CODEC).encode(buf, value.pos2());

                        PacketCodecs.STRING.encode(buf, value.regionName());
                    },

                    buf -> new YPCComponent(
                            PacketCodecs.STRING.decode(buf),
                            Uuids.PACKET_CODEC.decode(buf),

                            PacketCodecs.BOOL.decode(buf),
                            PacketCodecs.BOOL.decode(buf),

                            PacketCodecs.optional(BlockPos.PACKET_CODEC).decode(buf),
                            PacketCodecs.optional(BlockPos.PACKET_CODEC).decode(buf),

                            PacketCodecs.STRING.decode(buf)
                    )
            );
}