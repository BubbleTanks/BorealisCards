package borealiscards.patches;

import basemod.ReflectionHacks;
import borealiscards.powers.RondelmancyPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch2(
        clz = MakeTempCardInHandAction.class, method = "addToDiscard", paramtypez = {int.class}
)
public class RondelPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> patch(MakeTempCardInHandAction __instance, int discardAmt, AbstractCard ___c) {
        if (Shiv.ID.equals(___c.cardID) && AbstractDungeon.player.hasPower(RondelmancyPower.POWER_ID)){
            for (int i = 0; i < discardAmt; i++) {
                AbstractCard card = ReflectionHacks.privateMethod(MakeTempCardInHandAction.class, "makeNewCard").invoke(__instance);
                AbstractDungeon.actionManager.addToBottom(
                        new NewQueueCardAction(card, true, false, true));
            }
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}
