package cf.witcheskitchen.common.component.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record TaglockEntityData(UUID uuid, String name) {
    public static final Codec<TaglockEntityData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                            UUIDUtil.AUTHLIB_CODEC
                                    .fieldOf("uuid")
                                    .forGetter(TaglockEntityData::uuid),
                            Codec.STRING
                                    .fieldOf("name")
                                    .forGetter(TaglockEntityData::name)
                    )
                    .apply(instance, TaglockEntityData::new)
    );

    public static final StreamCodec<FriendlyByteBuf, TaglockEntityData> PACKET_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, TaglockEntityData::uuid,
            ByteBufCodecs.STRING_UTF8, TaglockEntityData::name,
            TaglockEntityData::new
    );
}
