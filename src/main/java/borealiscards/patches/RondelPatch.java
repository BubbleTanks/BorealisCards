package borealiscards.patches;

import basemod.helpers.CardModifierManager;
import borealiscards.cardmods.MarkingMod;
import borealiscards.powers.RondelmancyPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;

public class RondelPatch {

    @SpirePatch2(
            clz = MakeTempCardInHandAction.class,
            method = "addToDiscard",
            paramtypez = {
                    int.class
            }
    )
    public static class MarkForAutoplayPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard ___c) {
            if (AbstractDungeon.player.hasPower(RondelmancyPower.POWER_ID) && ___c.cardID == Shiv.ID)
                CardModifierManager.addModifier(___c, new MarkingMod());
        }
    }

    @SpirePatch2(clz = ShowCardAndAddToDiscardEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, float.class, float.class})
    public static class AutoplayPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(ShowCardAndAddToDiscardEffect __instance, AbstractCard srcCard) {
            if (CardModifierManager.hasModifier(srcCard, MarkingMod.ID)) {
                AbstractDungeon.actionManager.addToBottom((new NewQueueCardAction(srcCard, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true)));
                AbstractDungeon.player.discardPile.removeCard(srcCard);
                __instance.isDone = true;
                SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = ShowCardAndAddToDiscardEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class})
    public static class AutoplayPatch2 {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(ShowCardAndAddToDiscardEffect __instance, AbstractCard card) {
            if (CardModifierManager.hasModifier(card, MarkingMod.ID)) {
                AbstractDungeon.actionManager.addToBottom((new NewQueueCardAction(card, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true)));
                AbstractDungeon.player.discardPile.removeCard(card);
                __instance.isDone = true;
                SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = ShowCardAndAddToDiscardEffect.class, method = "update")
    public static class ReturnCards {
        @SpirePrefixPatch()
        public static SpireReturn<Void> returnCardsPatch(ShowCardAndAddToDiscardEffect __instance) {
            if (__instance.isDone) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
