package com.opwenam.yawpplayerclaim.item;

import com.opwenam.yawpplayerclaim.component.ModComponents;
import com.opwenam.yawpplayerclaim.component.YPCComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class YPCWand extends Item {
    public YPCWand(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        YPCComponent comp = stack.get(ModComponents.PLAYER_CLAIM);
        if (comp != null && comp.isValidClaim()) {
            if (!world.isClient) {
                user.sendMessage(
                        Text.literal("Valid claim item!"),
                        false
                );
            }
            return TypedActionResult.success(stack);
        }
        return TypedActionResult.pass(stack);
    }

    @Override
    public Text getName(ItemStack stack) {
        return super.getName(stack)
                .copy()
                .formatted(Formatting.GREEN);
    }

    @Override
    public void appendTooltip(ItemStack stack,
                              TooltipContext context,
                              List<Text> tooltip,
                              TooltipType type) {

        super.appendTooltip(stack, context, tooltip, type);

        YPCComponent comp = stack.get(ModComponents.PLAYER_CLAIM);

        if (comp == null) return;

        tooltip.add(gold("Owner: " + comp.owner()));
        tooltip.add(gold("Owner UUID: " + comp.ownerUUID()));

        tooltip.add(gold("Valid Claim: " + comp.isValidClaim()));
        tooltip.add(gold("Activated: " + comp.hasActivated()));

        tooltip.add(gold("Pos1: " + formatPos(comp.pos1())));
        tooltip.add(gold("Pos2: " + formatPos(comp.pos2())));

        tooltip.add(gold("Region: " + comp.regionName()));
    }

    private static Text gold(String text) {
        return Text.literal(text).formatted(Formatting.GOLD);
    }

    private static String formatPos(Optional<BlockPos> pos) {
        return pos.map(p -> p.getX() + ", " + p.getY() + ", " + p.getZ())
                .orElse("None");
    }
}
