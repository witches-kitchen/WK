package cf.witcheskitchen.common.component.blockentity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;

public record TeapotData(int progress, int effectTimer, boolean hasWater, Holder<MobEffect> effect) {
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
                BuiltInRegistries.MOB_EFFECT.holderByNameCodec()
                    .fieldOf("effect")
                    .forGetter(TeapotData::effect)
            )
            .apply(instance, TeapotData::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, TeapotData> PACKET_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT, TeapotData::progress,
        ByteBufCodecs.INT, TeapotData::effectTimer,
        ByteBufCodecs.BOOL, TeapotData::hasWater,
        ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT), TeapotData::effect,
        TeapotData::new
    );
}
