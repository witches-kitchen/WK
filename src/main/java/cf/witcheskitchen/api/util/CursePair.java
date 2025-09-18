package cf.witcheskitchen.api.util;

import cf.witcheskitchen.api.curse.CurseDefinition;
import net.minecraft.core.Holder;

public class CursePair {
    private Holder<CurseDefinition> curseDefinition;
    private int duration;

    public CursePair(Holder<CurseDefinition> curseDefinition, int duration) {
        this.curseDefinition = curseDefinition;
        this.duration = duration;
    }

    public Holder<CurseDefinition> getCurse() {
        return this.curseDefinition;
    }

    public void setCurse(Holder<CurseDefinition> curseDefinition) {
        this.curseDefinition = curseDefinition;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
