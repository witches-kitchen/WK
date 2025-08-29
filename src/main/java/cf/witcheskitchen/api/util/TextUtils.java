package cf.witcheskitchen.api.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public class TextUtils {
    /**
     * stylized two strings with different colors on the same line of Text
     *
     * @param first      string to be stylized
     * @param second     string to be stylized
     * @param nameColor  color of first string
     * @param valueColor color fo second string
     * @return Text styled to "first: second"
     */
    public static Component formattedFromTwoStrings(String first, String second, int nameColor, int valueColor) {
        final MutableComponent name = styledText(first, nameColor);
        final MutableComponent value = styledText(second, valueColor);

        return name.append(Component.nullToEmpty(": ")).append(value);
    }

    public static MutableComponent styledText(Object string, int color) {
        return Component.literal(string.toString()).setStyle(Style.EMPTY.withColor(color));
    }

    /**
     * turns "a_fun, string.minecraft:stick" to "A Fun, String Minecraft:stick"
     *
     * @param string string to be formatted
     * @return formatted string
     */
    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'' || chars[i] == '_') {
                chars[i] = ' ';
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}
