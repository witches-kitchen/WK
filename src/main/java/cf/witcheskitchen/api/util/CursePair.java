package cf.witcheskitchen.api.util;

import cf.witcheskitchen.api.curse.CurseDefinition;

public class CursePair {
    private CurseDefinition curseDefinition;
    private int duration;

    public CursePair(CurseDefinition curseDefinition, int duration) {
        this.curseDefinition = curseDefinition;
        this.duration = duration;
    }

    public CurseDefinition getCurse() {
        return this.curseDefinition;
    }

    public void setCurse(CurseDefinition curseDefinition) {
        this.curseDefinition = curseDefinition;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
