package borealiscards.patches;

import borealiscards.relics.MarauderAnt;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class AntPatch {

    @SpirePatch2(clz = AbstractRoom.class, method = "applyEndOfTurnRelics")
    public static class MarauderBlock {
        @SpirePostfixPatch
        public static void doubleBlock() {
            if (AbstractDungeon.player.currentBlock <= 6 && AbstractDungeon.player.currentBlock > 0) {
                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    if (r.relicId == MarauderAnt.ID) {
                        r.flash();
                        AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player.currentBlock));
                        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, r));
                    }
                }
            }
        }
    }
}
