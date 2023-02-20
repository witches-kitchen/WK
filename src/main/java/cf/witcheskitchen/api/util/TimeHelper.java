package cf.witcheskitchen.api.util;

//Mostly for debugging
public final class TimeHelper {
    private TimeHelper() {
        // Don't let anyone instantiate this
    }

    public static int toTicks(int seconds) {
        return seconds * 20;
    }

    public static int toSeconds(int ticks) {
        return ticks / 20;
    }

    public static double toSeconds(double ticks) {
        return ticks / 20;
    }

}
