package cf.witcheskitchen.common.component;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.fluid.FluidStack;
import cf.witcheskitchen.common.component.blockentity.TeapotData;
import cf.witcheskitchen.common.component.blockentity.WitchesCauldronData;
import cf.witcheskitchen.common.component.item.SeedTypeData;
import cf.witcheskitchen.common.component.item.TaglockEntityData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class WKComponents {
    public static final DataComponentType<BlockPos> BLOCK_POS = register("block_pos", DataComponentType.<BlockPos>builder()
        .persistent(BlockPos.CODEC)
        .networkSynchronized(BlockPos.STREAM_CODEC)
        .build()
    );

    public static final DataComponentType<TaglockEntityData> TAGLOCK = register("taglock", DataComponentType.<TaglockEntityData>builder()
        .persistent(TaglockEntityData.CODEC)
        .networkSynchronized(TaglockEntityData.PACKET_CODEC)
        .build()
    );

    public static final DataComponentType<WitchesCauldronData> WITCHES_CAULDRON = register("witches_cauldron", DataComponentType.<WitchesCauldronData>builder()
        .persistent(WitchesCauldronData.CODEC)
        .networkSynchronized(WitchesCauldronData.PACKET_CODEC)
        .build()
    );

    public static final DataComponentType<FluidStack> FLUID_STACK = register("fluid_stack", DataComponentType.<FluidStack>builder()
        .persistent(FluidStack.CODEC)
        .networkSynchronized(FluidStack.PACKET_CODEC)
        .build()
    );

    public static final DataComponentType<TeapotData> TEAPOT = register("teapot", DataComponentType.<TeapotData>builder()
        .persistent(TeapotData.CODEC)
        .networkSynchronized(TeapotData.PACKET_CODEC)
        .build()
    );

    public static final DataComponentType<SeedTypeData> SEED_TYPE = register("seed_type", DataComponentType.<SeedTypeData>builder()
        .persistent(SeedTypeData.CODEC)
        .networkSynchronized(SeedTypeData.PACKET_CODEC)
        .build()
    );

    public static final DataComponentType<ResourceKey<Level>> DIMENSION = register("dimension", DataComponentType.<ResourceKey<Level>>builder()
        .persistent(ResourceKey.codec(Registries.DIMENSION))
        .networkSynchronized(ResourceKey.streamCodec(Registries.DIMENSION))
        .build()
    );

    public static final DataComponentType<UUID> UUID = register("uuid", DataComponentType.<UUID>builder()
        .persistent(UUIDUtil.CODEC)
        .networkSynchronized(UUIDUtil.STREAM_CODEC)
        .build()
    );

    private static <T> DataComponentType<T> register(String name, DataComponentType<T> component) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, WitchesKitchen.id(name), component);
    }

    public static void init() {
    }
}
