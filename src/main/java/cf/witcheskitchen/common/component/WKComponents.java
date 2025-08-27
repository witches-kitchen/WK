package cf.witcheskitchen.common.component;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.fluid.FluidStack;
import cf.witcheskitchen.common.component.blockentity.TeapotData;
import cf.witcheskitchen.common.component.blockentity.WitchesCauldronData;
import cf.witcheskitchen.common.component.item.SeedTypeData;
import cf.witcheskitchen.common.component.item.TaglockEntityData;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class WKComponents {
    public static final ComponentType<BlockPos> BLOCK_POS = register("block_pos", ComponentType.<BlockPos>builder()
            .codec(BlockPos.CODEC)
            .packetCodec(BlockPos.PACKET_CODEC)
            .build()
    );

    public static final ComponentType<TaglockEntityData> TAGLOCK = register("taglock", ComponentType.<TaglockEntityData>builder()
            .codec(TaglockEntityData.CODEC)
            .packetCodec(TaglockEntityData.PACKET_CODEC)
            .build()
    );

    public static final ComponentType<WitchesCauldronData> WITCHES_CAULDRON = register("witches_cauldron", ComponentType.<WitchesCauldronData>builder()
            .codec(WitchesCauldronData.CODEC)
            .packetCodec(WitchesCauldronData.PACKET_CODEC)
            .build()
    );

    public static final ComponentType<FluidStack> FLUID_STACK = register("fluid_stack", ComponentType.<FluidStack>builder()
            .codec(FluidStack.CODEC)
            .packetCodec(FluidStack.PACKET_CODEC)
            .build()
    );

    public static final ComponentType<TeapotData> TEAPOT = register("teapot", ComponentType.<TeapotData>builder()
            .codec(TeapotData.CODEC)
            .packetCodec(TeapotData.PACKET_CODEC)
            .build()
    );

    public static final ComponentType<SeedTypeData> SEED_TYPE = register("seed_type", ComponentType.<SeedTypeData>builder()
            .codec(SeedTypeData.CODEC)
            .packetCodec(SeedTypeData.PACKET_CODEC)
            .build()
    );

    public static final ComponentType<RegistryKey<World>> DIMENSION = register("dimension", ComponentType.<RegistryKey<World>>builder()
            .codec(RegistryKey.createCodec(RegistryKeys.WORLD))
            .packetCodec(RegistryKey.createPacketCodec(RegistryKeys.WORLD))
            .build()
    );

    public static final ComponentType<UUID> UUID = register("uuid", ComponentType.<UUID>builder()
            .codec(Uuids.INT_STREAM_CODEC)
            .packetCodec(Uuids.PACKET_CODEC)
            .build()
    );

    private static <T> ComponentType<T> register(String name, ComponentType<T> component) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, WitchesKitchen.id(name), component);
    }

    public static void init() {
    }
}
