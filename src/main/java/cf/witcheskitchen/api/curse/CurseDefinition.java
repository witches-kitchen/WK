package cf.witcheskitchen.api.curse;

import cf.witcheskitchen.api.fortune.FortuneDefinition;
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
import net.minecraft.util.StringRepresentable;

//TODO: FACTOR IN MORE THINGS

/**
 *
 * @param level
 * @param canDeflectToCaster      Can the curse be sent back to the caster via ritual?
 * @param canCurseBeNegated       Can the curse be negated, as opposed to dispelling it, rendering it useless to either party?
 * @param canDispelCurse          Can the curse be dispelled before it is over?
 * @param canDispelPermanentCurse Does the curse last until it is dispelled by the victim?
 * @param isCurseInstant          Is the curse instant?
 * @param minTimeFrame            How many ticks at minimum does it take for a non-instant curse to fire?
 * @param maxTimeFrame            How many ticks at maximum does it take for a non-instant curse to fire?
 * @param curingFortunes          List of fortunes that can cure this curse.
 * @param curseLength             How many ticks does a curse last for once fired?
 * @param isCursePermanent        Is the curse permanent if it is not treated?
 * @param canCurseBeInflictedOnlyAtNight        Is the curse dependent on it being night?
 * @param canCurseBeInflictedOnlyAtDay       Is the curse dependent on it being day?
 */
public record CurseDefinition(
    //TODO: What does this line do?
    int level,

    boolean canDeflectToCaster,
    boolean canCurseBeNegated,
    boolean canDispelCurse,
    boolean canDispelPermanentCurse,
    boolean isCurseInstant,
    boolean isCursePermanent,
    boolean canCurseBeInflictedOnlyAtNight,
    boolean canCurseBeInflictedOnlyAtDay,

    int minTimeFrame,
    int maxTimeFrame,
    int curseLength,

    TagKey<FortuneDefinition> curingFortunes,
    CurseEffect curseEffect
) {
    public static final Codec<CurseDefinition> DIRECT_CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.INT
                    .fieldOf("level")
                    .forGetter(CurseDefinition::level),

                Codec.BOOL
                    .fieldOf("can_deflect_to_caster")
                    .forGetter(CurseDefinition::canDeflectToCaster),
                Codec.BOOL
                    .fieldOf("can_curse_be_negated")
                    .forGetter(CurseDefinition::canCurseBeNegated),
                Codec.BOOL
                    .fieldOf("can_dispel_curse")
                    .forGetter(CurseDefinition::canDispelCurse),
                Codec.BOOL
                    .fieldOf("can_dispel_permanent_curse")
                    .forGetter(CurseDefinition::canDispelPermanentCurse),
                Codec.BOOL
                    .fieldOf("is_curse_instant")
                    .forGetter(CurseDefinition::isCurseInstant),
                Codec.BOOL
                    .fieldOf("is_curse_permanent_unless_dispelled")
                    .forGetter(CurseDefinition::isCursePermanent),
                Codec.BOOL
                    .fieldOf("can_cursed_be_inflicted_only_at_night")
                    .forGetter(CurseDefinition::canCurseBeInflictedOnlyAtNight),
                Codec.BOOL
                    .fieldOf("can_cursed_be_inflicted_only_at_day")
                    .forGetter(CurseDefinition::canCurseBeInflictedOnlyAtDay),


                ExtraCodecs.POSITIVE_INT
                    .fieldOf("min_time_frame")
                    .forGetter(CurseDefinition::minTimeFrame),
                ExtraCodecs.POSITIVE_INT
                    .fieldOf("max_time_frame")
                    .forGetter(CurseDefinition::maxTimeFrame),
                ExtraCodecs.POSITIVE_INT
                    .fieldOf("curse_length")
                    .forGetter(CurseDefinition::curseLength),

                TagKey.hashedCodec(WKRegistryKeys.FORTUNES)
                    .fieldOf("curing_fortunes")
                    .forGetter(CurseDefinition::curingFortunes),

                WKRegistries.CURSE_EFFECTS.byNameCodec()
                    .fieldOf("curse_effect")
                    .forGetter(CurseDefinition::curseEffect)
            )
            .apply(instance, CurseDefinition::new)
    );

    public static final Codec<Holder<CurseDefinition>> HOLDER_CODEC = RegistryFixedCodec.create(WKRegistryKeys.CURSES);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CurseDefinition>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WKRegistryKeys.CURSES);

    /**
     * Enum for curse strength
     * Hex is the weakest
     * Demonic is the strongest
     * This also dictates how
     * easy a curse is to
     * dispel off of a player
     */
    public enum CurseStrength implements StringRepresentable {
        HEX("hex"),
        LESSER("lesser"),
        GREATER("greater"),
        DEMONIC("demonic");

        private final String serializedName;

        CurseStrength(String serializedName) {
            this.serializedName = serializedName;
        }

        @Override
        public String getSerializedName() {
            return this.serializedName;
        }
    }
}
