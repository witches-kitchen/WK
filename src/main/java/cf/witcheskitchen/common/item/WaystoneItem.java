package cf.witcheskitchen.common.item;

import cf.witcheskitchen.api.util.TextUtils;
import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.data.DimColorReloadListener;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;

public class WaystoneItem extends Item {
    private static final int MAX_USE_TIME = 40;

    public WaystoneItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        if ((context.getLevel() instanceof ServerLevel serverWorld)) {
            bindBlockPosition(serverWorld, context.getClickedPos().relative(context.getClickedFace()), stack);
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
        if (!user.level().isClientSide()) {
            bindEntityPosition(entity, stack);
        }
        return super.interactLivingEntity(stack, user, entity, hand);
    }

    public void teleportToWaystoneLocation(Level world, ItemStack waystone, Entity entity) {
        if (world instanceof ServerLevel serverWorld) {
            BlockPos blockPos = getPosFromWaystone(waystone);
            ServerLevel toWorld = getDimFromWaystone(serverWorld, waystone);
            if (blockPos != null) {
                Vec3 center = blockPos.getBottomCenter();
                // TODO: is this correct?
                entity.teleportTo(toWorld != null ? toWorld : serverWorld, center.x, center.y, center.z, Set.of(Relative.X, Relative.Y, Relative.Z), entity.getYRot(), entity.getXRot(), true);
            }
        }
    }

    public void bindBlockPosition(Level world, BlockPos pos, ItemStack stack) {
        stack.set(WKComponents.BLOCK_POS, pos);
        stack.set(WKComponents.DIMENSION, world.dimension());
    }

    @Deprecated(forRemoval = true)
    public void bindEntityPosition(LivingEntity entity, ItemStack stack) {
        stack.set(WKComponents.UUID, entity.getUUID());
    }

    @Nullable
    public BlockPos getPosFromWaystone(ItemStack stack) {
        if (stack.has(WKComponents.BLOCK_POS)) {
            return stack.get(WKComponents.BLOCK_POS);
        }
        return null;
    }

    @Nullable
    public ServerLevel getDimFromWaystone(ServerLevel serverWorld, ItemStack stack) {
        MinecraftServer minecraftServer = serverWorld.getServer();
        if (stack.has(WKComponents.DIMENSION)) {
            ResourceKey<Level> registryKey = stack.get(WKComponents.DIMENSION);
            return minecraftServer.getLevel(registryKey);
        }
        return null;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        if (stack.has(WKComponents.BLOCK_POS) && stack.has(WKComponents.DIMENSION)) {
            String dimension = stack.get(WKComponents.DIMENSION).location().toString();
            int color = 0xffffff;
            for (Pair<String, Integer> dimData : DimColorReloadListener.COLOR_DATA) {
                if (dimData.getFirst().equals(dimension)) {
                    color = dimData.getSecond();
                }
            }

            String formattedDim = TextUtils.capitalizeString(dimension.substring(dimension.indexOf(":") + 1));
            BlockPos pos = stack.get(WKComponents.BLOCK_POS);

            textConsumer.accept(TextUtils.formattedFromTwoStrings("Dimension", formattedDim, 0xFFAA00, color));
            textConsumer.accept(TextUtils.formattedFromTwoStrings("Position", pos.getX() + " " + pos.getY() + " " + pos.getZ(), 0xFFAA00, 0x55FFFF));
        }
    }
}
