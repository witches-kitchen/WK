package cf.witcheskitchen.mixin.entity;

import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Entity.class)
public class EntityMixin {

    @ModifyArgs(method = "positionRider(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity$MoveFunction;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity$MoveFunction;accept(Lnet/minecraft/world/entity/Entity;DDD)V"))
    private void wk$ferretVehicleOffset(Args args) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.isVehicle() && livingEntity.getFirstPassenger() instanceof FerretEntity) {
                double height = entity.getBbHeight();
                args.set(2, (double) args.get(2) - height + 0.3d);
            }
        }
    }
}
