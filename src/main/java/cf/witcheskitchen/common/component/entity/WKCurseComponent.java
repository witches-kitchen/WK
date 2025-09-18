package cf.witcheskitchen.common.component.entity;

import cf.witcheskitchen.api.curse.CurseDefinition;
import cf.witcheskitchen.api.util.CursePair;
import cf.witcheskitchen.common.registry.WKRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.HashSet;
import java.util.Set;

public class WKCurseComponent implements ServerTickingComponent, AutoSyncedComponent {
    private final Set<CursePair> curses = new HashSet<>();
    private final Player player;

    public WKCurseComponent(Player player) {
        this.player = player;
    }

    @Override
    public void serverTick() {
        for (CursePair cursePair : getCurses()) {
            if (cursePair.getDuration() > 0) {
                cursePair.getCurse().tick(player);
                cursePair.setDuration(cursePair.getDuration() - 1);
            } else {
                removeCurse(cursePair.getCurse());
            }
        }
    }

    public void addCurse(CurseDefinition curseDefinition, int duration) {
        if (hasCurse(curseDefinition)) {
            for (CursePair cursePair : getCurses()) {
                if (cursePair.getCurse() == curseDefinition) {
                    cursePair.setDuration(duration);
                    curseDefinition.onAdded(this.player);
                    return;
                }
            }
        }
        getCurses().add(new CursePair(curseDefinition, duration));
        curseDefinition.onAdded(this.player);
    }

    public void removeCurse(CurseDefinition curseDefinition) {
        if (hasCurse(curseDefinition)) {
            for (CursePair cursePair : getCurses()) {
                if (cursePair.getCurse() == curseDefinition) {
                    cursePair.getCurse().onRemoved(this.player);
                    getCurses().remove(cursePair);
                }
            }
        }
    }

    @Override
    public void readData(ValueInput data) {
        ValueInput.ValueInputList cursesList = data.childrenListOrEmpty("Curses");
        for (ValueInput curseView : cursesList) {
            addCurse(WKRegistries.CURSES.getValue(ResourceLocation.tryParse(curseView.getString("Curse").orElseThrow())), curseView.getInt("Duration").orElseThrow());
        }
    }

    @Override
    public void writeData(ValueOutput data) {
        writeCurse(data.childrenList("Curses"));
    }

    public Set<CursePair> getCurses() {
        return curses;
    }

    public boolean hasCurse(CurseDefinition curseDefinition) {
        return getCurses().stream().anyMatch(c -> c.getCurse() == curseDefinition);
    }

    public void writeCurse(ValueOutput.ValueOutputList cursesList) {
        for (CursePair cursePair : getCurses()) {
            ValueOutput curseView = cursesList.addChild();
            curseView.putString("Curse", WKRegistries.CURSES.getKey(cursePair.getCurse()).toString());
            curseView.putInt("Duration", cursePair.getDuration());
        }
    }
}
