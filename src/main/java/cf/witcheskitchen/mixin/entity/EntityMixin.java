package cf.witcheskitchen.mixin.entity;

import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Entity.class)
public class EntityMixin {

    @ModifyArgs(method = "updatePassengerPosition(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity$PositionUpdater;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity$PositionUpdater;accept(Lnet/minecraft/entity/Entity;DDD)V"))
    private void wk$ferretVehicleOffset(Args args) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasPassengers() && livingEntity.getFirstPassenger() instanceof FerretEntity) {
                double height = entity.getHeight();
                args.set(2, (double) args.get(2) - height + 0.3d);
            }
        }
    }
}
