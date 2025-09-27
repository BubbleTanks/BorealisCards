package borealiscards.ui;

import basemod.EasyConfigPanel;
import borealiscards.BorealisCards;

public class ModConfig extends EasyConfigPanel {
    public ModConfig() {
        super(BorealisCards.modID, BorealisCards.makeID("ModConfig"));
    }

    public static boolean HappyPrideMoth = false;
}
