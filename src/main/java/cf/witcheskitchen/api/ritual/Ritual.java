package cf.witcheskitchen.api.ritual;

import cf.witcheskitchen.api.CommandType;
import cf.witcheskitchen.api.event.RitualEvent;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import com.mojang.brigadier.ParseResults;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Set;
import java.util.UUID;

public class Ritual {
    public final UUID playerUuid;
    public final Box boundingBox;
    public final Set<RitualCircle> circles;
    public final long id;
    public String command;

    public BlockPos ritualPos;
    public PlayerEntity activator;

    public Ritual(UUID uuid, long id, Box boundingBox, Set<RitualCircle> circles){
        this.playerUuid = uuid;
        this.boundingBox = boundingBox;
        this.circles = circles;
        this.id = id;
    }

    public void tick(World world, BlockPos blockPos, BlockState blockState, RitualRecipe ritualRecipe) {
        RitualEvent.TICK.invoker().tick(this, world, blockPos);
        MinecraftServer minecraftServer = world.getServer();
        for(CommandType commandType : ritualRecipe.command){
            if(commandType.type.equals("tick")){
                runCommand(minecraftServer, ritualRecipe, blockPos);
            }
        }

    }

    public void onStart(World world, BlockPos blockPos, PlayerEntity player, RitualRecipe ritualRecipe) {
        RitualEvent.START.invoker().start(this, world, blockPos, player);
        MinecraftServer minecraftServer = world.getServer();
        for(CommandType commandType : ritualRecipe.command){
            if(commandType.type.equals("start")){
                runCommand(minecraftServer, ritualRecipe, blockPos);
            }
        }
    }

    public void onEnd(World world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        RitualEvent.END.invoker().end(this, world, blockPos);
        MinecraftServer minecraftServer = world.getServer();
        for(CommandType commandType : ritualRecipe.command){
            if(commandType.type.equals("end")){
                runCommand(minecraftServer, ritualRecipe, blockPos);
            }
        }
    }

    //TODO test this
    public void runCommand(MinecraftServer minecraftServer, RitualRecipe recipe, BlockPos blockPos){
        if (minecraftServer != null && !recipe.command.isEmpty()) {
            String posString = blockPos.getX() + " " + blockPos.getY() + " " + blockPos.getZ();
            String parsedCommand = this.command.replaceAll("\\{pos}", posString);
            ServerCommandSource commandSource = minecraftServer.getCommandSource();
            CommandManager commandManager = minecraftServer.getCommandManager();
            ParseResults<ServerCommandSource> parseResults = commandManager.getDispatcher().parse(parsedCommand, commandSource);
            commandManager.execute(parseResults, parsedCommand);
        }
    }
}
