package borealiscards.patches;

import borealiscards.cards.ironclad.BlazingVeins;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

@SpirePatch(
        clz= UseCardAction.class,
        method="update"
)
public class BlazingVeinsPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(UseCardAction __instance, AbstractCard ___targetCard) {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID == BlazingVeins.ID) {
                if (___targetCard.exhaust || ___targetCard.exhaustOnUseOnce) {
                    AbstractDungeon.actionManager.addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, c.magicNumber));
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(___targetCard, 1));
                    c.flash();
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "moveToExhaustPile");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}