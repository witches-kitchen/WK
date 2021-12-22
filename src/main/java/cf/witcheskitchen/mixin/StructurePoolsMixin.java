package cf.witcheskitchen.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;

@Mixin(StructurePools.class)
public class StructurePoolsMixin {


    @Inject(method = "register", at = { @At("HEAD")}, cancellable = true)
    private static void register(StructurePool templatePool, CallbackInfoReturnable<StructurePool> cir) {
        //System.out.println(templatePool.getId());
    }
}
