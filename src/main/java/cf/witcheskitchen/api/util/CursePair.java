package cf.witcheskitchen.api.util;

import cf.witcheskitchen.common.curse.Curse;

public class CursePair {
    private Curse curse;
    private int duration;

    public CursePair(Curse curse, int duration) {
        this.curse = curse;
        this.duration = duration;
    }

    public Curse getCurse() {
        return this.curse;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
