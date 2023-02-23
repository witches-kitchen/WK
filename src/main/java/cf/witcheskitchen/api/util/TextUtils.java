package cf.witcheskitchen.api.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class TextUtils {
    public static Text formattedFromString(String first, String second, int nameColor, int valueColor) {
        final MutableText name = styledText(first, nameColor);
        final MutableText value = styledText(second, valueColor);

        return name.append(Text.of(": ")).append(value);
    }

    public static MutableText styledText(Object string, int color) {
        return Text.literal(string.toString()).setStyle(Style.EMPTY.withColor(color));
    }

    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'' || chars[i]=='_') {
                chars[i] = ' ';
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}
