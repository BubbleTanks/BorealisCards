package borealiscards.patches;

import borealiscards.BorealisCards;
import borealiscards.relics.ParanoiasBox;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

public class ParanoiaBoxPreventSkip {

    //Borrowed from Spire Biomes which borrowed from replay the spire
    @SpirePatch(clz = CombatRewardScreen.class, method = "rewardViewUpdate")
    public static class ReplayRewardSkipPositionPatch {

        public static float HIDE_X = -1.0f;
        public static float SHOW_X = -1.0f;

        public static void Postfix(CombatRewardScreen __Instance) {
            if (AbstractDungeon.player.hasRelic(ParanoiasBox.ID)) {
                if (HIDE_X == -1.0f) {
                    HIDE_X = AbstractDungeon.topPanel.mapHb.cX - 400.0f * Settings.scale;
                    SHOW_X = AbstractDungeon.topPanel.mapHb.cX;
                }
                boolean proceed = true;
                for (int i = 0; i < __Instance.rewards.size(); i++){
                    if (__Instance.rewards.get(i).type == CustomRewardTypes.ER_PARANOIA || BorealisCards.choosingTransformCard){
                        proceed = false;
                        break;
                    }
                }
                if (proceed) {
                    AbstractDungeon.overlayMenu.proceedButton.show();
                    AbstractDungeon.topPanel.mapHb.move(SHOW_X, AbstractDungeon.topPanel.mapHb.cY);
                } else {
                    AbstractDungeon.overlayMenu.proceedButton.hide();
                    AbstractDungeon.overlayMenu.cancelButton.hide();
                    AbstractDungeon.topPanel.mapHb.move(ReplayRewardSkipPositionPatch.HIDE_X, AbstractDungeon.topPanel.mapHb.cY);
                }
            }
        }
    }
}
