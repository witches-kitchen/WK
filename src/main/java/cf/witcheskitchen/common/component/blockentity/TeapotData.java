package cf.witcheskitchen.common.component.blockentity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;

public record TeapotData(int progress, int effectTimer, boolean hasWater, RegistryEntry<StatusEffect> effect) {
    public static final Codec<TeapotData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                            Codec.INT
                                    .fieldOf("progress")
                                    .forGetter(TeapotData::progress),
                            Codec.INT
                                    .fieldOf("effectTimer")
                                    .forGetter(TeapotData::effectTimer),
                            Codec.BOOL
                                    .fieldOf("hasWater")
                                    .forGetter(TeapotData::hasWater),
                            Registries.STATUS_EFFECT.getEntryCodec()
                                    .fieldOf("effect")
                                    .forGetter(TeapotData::effect)
                    )
                    .apply(instance, TeapotData::new)
    );

    public static final PacketCodec<RegistryByteBuf, TeapotData> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, TeapotData::progress,
            PacketCodecs.INTEGER, TeapotData::effectTimer,
            PacketCodecs.BOOLEAN, TeapotData::hasWater,
            PacketCodecs.registryEntry(RegistryKeys.STATUS_EFFECT), TeapotData::effect,
            TeapotData::new
    );
}
