package UserUtils;

import android.graphics.Color;

/**
 * Created by mohammad on 4/19/15.
 * This is the color util that will get the color from
 */
public class ColorUtil {

    /**
     * Get color from system colors
     */
    public static int getSystemColor(final SystemColors color) {


        if (color == SystemColors.ACCENT_COLOR_LITE_BLUE) {

            return Color.parseColor("#c7e6fb");

        }

        if (color == SystemColors.ACCENT_DARK_BLUE) {

            return Color.parseColor("#0059b3");

        }

        if (color == SystemColors.WHITE) {

            return Color.parseColor("#ffffff");

        }

        if (color == SystemColors.ACCENT_COLOR_DARK_NAVY) {

            return Color.parseColor("#363D56");

        }

        if (color == SystemColors.APP_BLUE) {

            return Color.parseColor("#29498A");

        }

        if (color == SystemColors.ACCENT_MEDIUM_GRAY) {

            return Color.parseColor("#6a6c74");

        }

        if (color == SystemColors.DEAL_COLOR) {

            return Color.parseColor("#EF6359");

        }

        if (color == SystemColors.LITE_RED) {
            return Color.parseColor("#f07970");
        }

        if (color == SystemColors.RED) {

            return Color.parseColor("#EF6359");
        }

        if (color == SystemColors.ACCENT_GREEN) {

            return Color.parseColor("#00B890");
        }

        if (color == SystemColors.ACCENT_ORANGE) {

            return Color.parseColor("#FFB336");
        }

        if (color == SystemColors.ACCENT_ORANGE_DARK) {

            return Color.parseColor("#FB8C00");
        }

        if (color == SystemColors.ACCENT_COLOR_BLUE) {

            return Color.parseColor("#1D9AEF");
        }

        if (color == SystemColors.EVEN_TABLE_ROW) {

            return Color.parseColor("#F5F5F5");
        }

        if (color == SystemColors.ODD_TABLE_ROW) {

            return Color.parseColor("#F0F0EE");
        }

        if (color == SystemColors.ACCENT_DARK_GRAY) {

            return Color.parseColor("#ebebe9");

        }

        if (color == SystemColors.ACCENT_COLOR_LITE_NAVY) {

            return Color.parseColor("#90939E");

        }

        if (color == SystemColors.BLACK) {

            return Color.parseColor("#000000");

        }

        if (color == SystemColors.BLACK) {

            return Color.parseColor("#000000");

        }

        return Color.parseColor("#363D56");
    }
}
