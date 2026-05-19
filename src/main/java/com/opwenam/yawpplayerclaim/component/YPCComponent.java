package com.opwenam.yawpplayerclaim.component;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import io.netty.buffer.Unpooled;

/**
 * YPCComponent represents player claim data as an immutable record.
 * Uses Minecraft data types (BlockPos) and PacketByteBuf for serialization
 * (preferred for component syncing) instead of direct NBT manipulation.
 *
 * Replace registration wiring in ModComponents to use your chosen component
 * library and its sync hooks; use writeToBuf/readFromBuf for network sync.
 */
public record YPCComponent(
    BlockPos firstPos,
    BlockPos secondPos,
    String owner,
    boolean validClaim,
    String regionName,
    boolean hadActivated
) {
    public static final YPCComponent EMPTY = new YPCComponent(BlockPos.ORIGIN, BlockPos.ORIGIN, "", false, "", false);

    public YPCComponent {
        // Normalize nulls to defaults
        if (firstPos == null) firstPos = BlockPos.ORIGIN;
        if (secondPos == null) secondPos = BlockPos.ORIGIN;
        if (owner == null) owner = "";
        if (regionName == null) regionName = "";
    }

    // Builder-like convenience methods (records are immutable)
    public YPCComponent withFirstPos(BlockPos pos) { return new YPCComponent(pos, this.secondPos, this.owner, this.validClaim, this.regionName, this.hadActivated); }
    public YPCComponent withSecondPos(BlockPos pos) { return new YPCComponent(this.firstPos, pos, this.owner, this.validClaim, this.regionName, this.hadActivated); }
    public YPCComponent withOwner(String owner) { return new YPCComponent(this.firstPos, this.secondPos, owner, this.validClaim, this.regionName, this.hadActivated); }
    public YPCComponent withValidClaim(boolean valid) { return new YPCComponent(this.firstPos, this.secondPos, this.owner, valid, this.regionName, this.hadActivated); }
    public YPCComponent withRegionName(String name) { return new YPCComponent(this.firstPos, this.secondPos, this.owner, this.validClaim, name, this.hadActivated); }
    public YPCComponent withHadActivated(boolean had) { return new YPCComponent(this.firstPos, this.secondPos, this.owner, this.validClaim, this.regionName, had); }

    /**
     * Serialize to a PacketByteBuf for network syncing.
     */
    public void writeToBuf(PacketByteBuf buf) {
        buf.writeBlockPos(firstPos);
        buf.writeBlockPos(secondPos);
        buf.writeString(owner, 32767);
        buf.writeBoolean(validClaim);
        buf.writeString(regionName, 32767);
        buf.writeBoolean(hadActivated);
    }

    /**
     * Deserialize from a PacketByteBuf (reads in the same order as writeToBuf).
     */
    public static YPCComponent readFromBuf(PacketByteBuf buf) {
        BlockPos f = buf.readBlockPos();
        BlockPos s = buf.readBlockPos();
        String owner = buf.readString(32767);
        boolean valid = buf.readBoolean();
        String region = buf.readString(32767);
        boolean had = buf.readBoolean();
        return new YPCComponent(f, s, owner, valid, region, had);
    }

    /**
     * Helper to get a PacketByteBuf containing this component (convenience for tests).
     */
    public PacketByteBuf toPacket() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        writeToBuf(buf);
        return buf;
    }
}
