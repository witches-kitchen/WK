package cf.witcheskitchen.api.event.network;

import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.datafixers.util.Function9;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public interface CustomPacketCodecs {
    StreamCodec<FriendlyByteBuf, Vec3> VECTOR3D = StreamCodec.ofMember(
            (value, buf) ->
                    buf.writeDouble(value.x)
                            .writeDouble(value.y)
                            .writeDouble(value.z),
            buf -> new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble())
    );

    StreamCodec<FriendlyByteBuf, SoundSource> SOUND_CATEGORY = createEnumCodec(SoundSource::valueOf);

    StreamCodec<RegistryFriendlyByteBuf, List<Ingredient>> INGREDIENT_LIST = createListCodec(Ingredient.CONTENTS_STREAM_CODEC);

    static <T extends Enum<T>> StreamCodec<FriendlyByteBuf, T> createEnumCodec(Function<String, T> function) {
        return StreamCodec.ofMember(
                (value, buf) -> buf.writeUtf(value.name()),
                buf -> function.apply(buf.readUtf())
        );
    }

    static <T, V extends FriendlyByteBuf> StreamCodec<V, List<T>> createListCodec(StreamCodec<V, T> codec) {
        return StreamCodec.ofMember((list, buf) -> {
            buf.writeVarInt(list.size());
            for (T value : list) {
                codec.encode(buf, value);
            }
        }, buf -> {
            var list = new LinkedList<T>();
            var length = buf.readVarInt();

            for (int i = 0; i < length; i++) {
                list.add(codec.decode(buf));
            }

            return list;
        });
    }

    static <T, V extends FriendlyByteBuf> StreamCodec<V, Set<T>> createSetCodec(StreamCodec<V, T> codec) {
        return StreamCodec.ofMember((set, buf) -> {
            buf.writeVarInt(set.size());
            for (T value : set) {
                codec.encode(buf, value);
            }
        }, buf -> {
            var set = new HashSet<T>();
            var length = buf.readVarInt();

            for (int i = 0; i < length; i++) {
                set.add(codec.decode(buf));
            }

            return set;
        });
    }

    // yes, we needed more
    static <B, C, T1, T2, T3, T4, T5, T6, T7> StreamCodec<B, C> tuple(
            final StreamCodec<? super B, T1> codec1, final Function<C, T1> from1,
            final StreamCodec<? super B, T2> codec2, final Function<C, T2> from2,
            final StreamCodec<? super B, T3> codec3, final Function<C, T3> from3,
            final StreamCodec<? super B, T4> codec4, final Function<C, T4> from4,
            final StreamCodec<? super B, T5> codec5, final Function<C, T5> from5,
            final StreamCodec<? super B, T6> codec6, final Function<C, T6> from6,
            final StreamCodec<? super B, T7> codec7, final Function<C, T7> from7,
            final Function7<T1, T2, T3, T4, T5, T6, T7, C> to
    ) {
        return new StreamCodec<>() {
            public C decode(B object) {
                T1 object2 = codec1.decode(object);
                T2 object3 = codec2.decode(object);
                T3 object4 = codec3.decode(object);
                T4 object5 = codec4.decode(object);
                T5 object6 = codec5.decode(object);
                T6 object7 = codec6.decode(object);
                T7 object8 = codec7.decode(object);
                return to.apply(object2, object3, object4, object5, object6, object7, object8);
            }

            public void encode(B object, C object2) {
                codec1.encode(object, from1.apply(object2));
                codec2.encode(object, from2.apply(object2));
                codec3.encode(object, from3.apply(object2));
                codec4.encode(object, from4.apply(object2));
                codec5.encode(object, from5.apply(object2));
                codec6.encode(object, from6.apply(object2));
                codec7.encode(object, from7.apply(object2));
            }
        };
    }

    static <B, C, T1, T2, T3, T4, T5, T6, T7, T8> StreamCodec<B, C> tuple(
            final StreamCodec<? super B, T1> codec1, final Function<C, T1> from1,
            final StreamCodec<? super B, T2> codec2, final Function<C, T2> from2,
            final StreamCodec<? super B, T3> codec3, final Function<C, T3> from3,
            final StreamCodec<? super B, T4> codec4, final Function<C, T4> from4,
            final StreamCodec<? super B, T5> codec5, final Function<C, T5> from5,
            final StreamCodec<? super B, T6> codec6, final Function<C, T6> from6,
            final StreamCodec<? super B, T7> codec7, final Function<C, T7> from7,
            final StreamCodec<? super B, T8> codec8, final Function<C, T8> from8,
            final Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> to
    ) {
        return new StreamCodec<>() {
            public C decode(B object) {
                T1 object2 = codec1.decode(object);
                T2 object3 = codec2.decode(object);
                T3 object4 = codec3.decode(object);
                T4 object5 = codec4.decode(object);
                T5 object6 = codec5.decode(object);
                T6 object7 = codec6.decode(object);
                T7 object8 = codec7.decode(object);
                T8 object9 = codec8.decode(object);
                return to.apply(object2, object3, object4, object5, object6, object7, object8, object9);
            }

            public void encode(B object, C object2) {
                codec1.encode(object, from1.apply(object2));
                codec2.encode(object, from2.apply(object2));
                codec3.encode(object, from3.apply(object2));
                codec4.encode(object, from4.apply(object2));
                codec5.encode(object, from5.apply(object2));
                codec6.encode(object, from6.apply(object2));
                codec7.encode(object, from7.apply(object2));
                codec8.encode(object, from8.apply(object2));
            }
        };
    }

    static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> StreamCodec<B, C> tuple(
            final StreamCodec<? super B, T1> codec1, final Function<C, T1> from1,
            final StreamCodec<? super B, T2> codec2, final Function<C, T2> from2,
            final StreamCodec<? super B, T3> codec3, final Function<C, T3> from3,
            final StreamCodec<? super B, T4> codec4, final Function<C, T4> from4,
            final StreamCodec<? super B, T5> codec5, final Function<C, T5> from5,
            final StreamCodec<? super B, T6> codec6, final Function<C, T6> from6,
            final StreamCodec<? super B, T7> codec7, final Function<C, T7> from7,
            final StreamCodec<? super B, T8> codec8, final Function<C, T8> from8,
            final StreamCodec<? super B, T9> codec9, final Function<C, T9> from9,
            final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> to
    ) {
        return new StreamCodec<>() {
            public C decode(B object) {
                T1 object2 = codec1.decode(object);
                T2 object3 = codec2.decode(object);
                T3 object4 = codec3.decode(object);
                T4 object5 = codec4.decode(object);
                T5 object6 = codec5.decode(object);
                T6 object7 = codec6.decode(object);
                T7 object8 = codec7.decode(object);
                T8 object9 = codec8.decode(object);
                T9 object10 = codec9.decode(object);
                return to.apply(object2, object3, object4, object5, object6, object7, object8, object9, object10);
            }

            public void encode(B object, C object2) {
                codec1.encode(object, from1.apply(object2));
                codec2.encode(object, from2.apply(object2));
                codec3.encode(object, from3.apply(object2));
                codec4.encode(object, from4.apply(object2));
                codec5.encode(object, from5.apply(object2));
                codec6.encode(object, from6.apply(object2));
                codec7.encode(object, from7.apply(object2));
                codec8.encode(object, from8.apply(object2));
                codec9.encode(object, from9.apply(object2));
            }
        };
    }
}
