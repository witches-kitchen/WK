package cf.witcheskitchen.api.ritual;

import cf.witcheskitchen.api.CommandType;
import cf.witcheskitchen.api.event.RitualEvent;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Ritual {

    public Ritual() {

    }

    public void tick(Level world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        RitualEvent.TICK.invoker().tick(this, world, blockPos);
        MinecraftServer minecraftServer = world.getServer();
        for (CommandType commandType : ritualRecipe.command) {
            if (commandType.type.equals("tick")) {
                runCommand(minecraftServer, blockPos, commandType.command);
            }
        }
    }

    public void onStart(Level world, BlockPos blockPos, Player player, RitualRecipe ritualRecipe) {
        RitualEvent.START.invoker().start(this, world, blockPos, player);
        MinecraftServer minecraftServer = world.getServer();
        for (CommandType commandType : ritualRecipe.command) {
            if (commandType.type.equals("start")) {
                runCommand(minecraftServer, blockPos, commandType.command);
            }
        }
    }

    public void onEnd(Level world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        RitualEvent.END.invoker().end(this, world, blockPos);
        MinecraftServer minecraftServer = world.getServer();
        for (CommandType commandType : ritualRecipe.command) {
            if (commandType.type.equals("end")) {
                runCommand(minecraftServer, blockPos, commandType.command);
            }
        }
    }

    //TODO test this, and maybe add option for {pos} to be taglock owners pos instead of ritual center pos
    public void runCommand(MinecraftServer minecraftServer, BlockPos blockPos, String command) {
        if (minecraftServer != null && !command.isEmpty()) {
            String posString = blockPos.getX() + " " + blockPos.getY() + " " + blockPos.getZ();
            String parsedCommand = command.replaceAll("\\{pos}", posString);
            CommandSourceStack commandSource = minecraftServer.createCommandSourceStack();
            Commands commandManager = minecraftServer.getCommands();
            ParseResults<CommandSourceStack> parseResults = commandManager.getDispatcher().parse(parsedCommand, commandSource);
            commandManager.performCommand(parseResults, parsedCommand);
        }
    }
}
