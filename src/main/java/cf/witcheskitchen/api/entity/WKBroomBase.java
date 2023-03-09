package cf.witcheskitchen.api.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class WKBroomBase extends Entity {
    public final ItemStack stack;

    public WKBroomBase(EntityType<?> type, World world, ItemStack stack) {
        super(type, world);
        this.stack = stack;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        } else if (!world.isClient) {
            if (player.isSneaking() && player.getMainHandStack().isEmpty()) {
                return pickUpBroom(player);
            } else {
                return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
            }

        }
        return super.interact(player, hand);
    }

    private ActionResult pickUpBroom(PlayerEntity player) {
        player.setStackInHand(player.preferredHand, this.stack);
        return ActionResult.CONSUME;
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, 0);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
}
