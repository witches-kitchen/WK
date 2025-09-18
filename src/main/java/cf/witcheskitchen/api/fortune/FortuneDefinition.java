package cf.witcheskitchen.api.fortune;

import cf.witcheskitchen.api.curse.CurseDefinition;
import cf.witcheskitchen.api.registry.WKRegistries;
import cf.witcheskitchen.api.registry.WKRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;


//TODO: FACTOR IN MORE THINGS

/**
 *
 * @param isNegative Is the fortune bad?
 * @param isInstant Does the fortune fire instantly?
 * @param minTimeFrame How many ticks at minimum does it take for a non-instant fortune to fire?
 * @param maxTimeFrame How many ticks at maximum does it take for a non-instant fortune to fire?
 * @param fortuneLength How many ticks does a fortune last for once fired?
 * @param negatingCurses List of curses that negate this fortune from firing.
 */
public record FortuneDefinition(
    boolean isNegative,
    boolean isInstant,

    int minTimeFrame,
    int maxTimeFrame,
    int fortuneLength,

    TagKey<CurseDefinition> negatingCurses,

    FortuneEffect fortuneEffect
) {
    public static final Codec<FortuneDefinition> DIRECT_CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.BOOL
                .fieldOf("is_negative")
                .forGetter(FortuneDefinition::isNegative),
            Codec.BOOL
                .fieldOf("is_instant")
                .forGetter(FortuneDefinition::isInstant),

            ExtraCodecs.POSITIVE_INT
                .fieldOf("min_time_frame")
                .forGetter(FortuneDefinition::minTimeFrame),
            ExtraCodecs.POSITIVE_INT
                .fieldOf("max_time_frame")
                .forGetter(FortuneDefinition::maxTimeFrame),
            ExtraCodecs.POSITIVE_INT
                .fieldOf("fortune_length")
                .forGetter(FortuneDefinition::fortuneLength),

            TagKey.hashedCodec(WKRegistryKeys.CURSES)
                .fieldOf("negating_curses")
                .forGetter(FortuneDefinition::negatingCurses),

            WKRegistries.FORTUNE_EFFECTS.byNameCodec()
                .fieldOf("fortune_effect")
                .forGetter(FortuneDefinition::fortuneEffect)
        )
            .apply(instance, FortuneDefinition::new)
    );

    public static final Codec<Holder<FortuneDefinition>> HOLDER_CODEC = RegistryFixedCodec.create(WKRegistryKeys.FORTUNES);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<FortuneDefinition>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WKRegistryKeys.FORTUNES);
}
