package borealiscards.ui;

import basemod.EasyConfigPanel;
import borealiscards.BorealisCards;

public class ModConfig extends EasyConfigPanel {
    public ModConfig() {
        super(BorealisCards.modID, BorealisCards.makeID("ModConfig"));
    }

    public static boolean UselessConfig = false;
    public static boolean HappyPrideMoth = false;
    public static boolean Relics = true;
    public static boolean ColorsRed = true;
    public static boolean ColorsGreen = true;
    public static boolean ColorsBlue = true;
    public static boolean ColorsPurple = true;
    public static boolean ColorsGray = true;
    public static boolean ColorsBlack = true;
//    public static boolean ColorsDarkBlue = true;
}
