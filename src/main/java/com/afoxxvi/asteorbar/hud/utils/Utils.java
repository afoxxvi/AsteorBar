package com.afoxxvi.asteorbar.hud.utils;

public class Utils {
    public static int getTotalExperience(int level) {
        if (level < 0) {
            return 0;
        } else if (level < 17) {
            return level * level + 6 * level;
        } else {
            return level < 32 ? (int) (2.5D * (double) level * (double) level - 40.5D * (double) level + 360.0D + 0.5D) : (int) (4.5D * (double) level * (double) level - 162.5D * (double) level + 2220.0D + 0.5D);
        }
    }
}
