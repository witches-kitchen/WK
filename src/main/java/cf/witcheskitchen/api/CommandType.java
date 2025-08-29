package cf.witcheskitchen.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class CommandType {
    public static Codec<CommandType> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                            Codec.STRING
                                    .fieldOf("command")
                                    .forGetter(CommandType::getCommand),
                            Codec.STRING
                                    .fieldOf("type")
                                    .forGetter(CommandType::getType)
                    )
                    .apply(instance, CommandType::new)
    );

    public static StreamCodec<FriendlyByteBuf, CommandType> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, CommandType::getCommand,
            ByteBufCodecs.STRING_UTF8, CommandType::getType,
            CommandType::new
    );

    public String command;
    public String type;

    public CommandType(String command, String type) {
        this.command = command;
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public String getType() {
        return type;
    }
}
