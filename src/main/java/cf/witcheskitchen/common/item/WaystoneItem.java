package cf.witcheskitchen.common.item;

import cf.witcheskitchen.api.util.TextUtils;
import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.data.DimColorReloadListener;
import com.mojang.datafixers.util.Pair;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;

public class WaystoneItem extends Item {
    private static final int MAX_USE_TIME = 40;

    public WaystoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        if ((context.getWorld() instanceof ServerWorld serverWorld)) {
            bindBlockPosition(serverWorld, context.getBlockPos().offset(context.getSide()), stack);
        }
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getWorld().isClient()) {
            bindEntityPosition(entity, stack);
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    public void teleportToWaystoneLocation(World world, ItemStack waystone, Entity entity) {
        if (world instanceof ServerWorld serverWorld) {
            BlockPos blockPos = getPosFromWaystone(waystone);
            ServerWorld toWorld = getDimFromWaystone(serverWorld, waystone);
            if (blockPos != null) {
                Vec3d center = blockPos.toBottomCenterPos();
                // TODO: is this correct?
                entity.teleport(toWorld != null ? toWorld : serverWorld, center.x, center.y, center.z, Set.of(PositionFlag.X, PositionFlag.Y, PositionFlag.Z), entity.getYaw(), entity.getPitch(), true);
            }
        }
    }

    public void bindBlockPosition(World world, BlockPos pos, ItemStack stack) {
        stack.set(WKComponents.BLOCK_POS, pos);
        stack.set(WKComponents.DIMENSION, world.getRegistryKey());
    }

    @Deprecated(forRemoval = true)
    public void bindEntityPosition(LivingEntity entity, ItemStack stack) {
        stack.set(WKComponents.UUID, entity.getUuid());
    }

    @Nullable
    public BlockPos getPosFromWaystone(ItemStack stack) {
        if (stack.contains(WKComponents.BLOCK_POS)) {
            return stack.get(WKComponents.BLOCK_POS);
        }
        return null;
    }

    @Nullable
    public ServerWorld getDimFromWaystone(ServerWorld serverWorld, ItemStack stack) {
        MinecraftServer minecraftServer = serverWorld.getServer();
        if (stack.contains(WKComponents.DIMENSION)) {
            RegistryKey<World> registryKey = stack.get(WKComponents.DIMENSION);
            return minecraftServer.getWorld(registryKey);
        }
        return null;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        if (stack.contains(WKComponents.BLOCK_POS) && stack.contains(WKComponents.DIMENSION)) {
            String dimension = stack.get(WKComponents.DIMENSION).getValue().toString();
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
