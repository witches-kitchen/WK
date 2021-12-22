package cf.witcheskitchen.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

@Mixin(SimpleBlockStateProvider.class)
public interface SimpleBlockStateProviderInvoker {
    @Invoker("<init>")
    static SimpleBlockStateProvider invokeCtor(BlockState state) { throw new IllegalStateException(); }
}