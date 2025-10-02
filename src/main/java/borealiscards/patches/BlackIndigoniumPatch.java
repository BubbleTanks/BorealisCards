package borealiscards.patches;

import borealiscards.SpireFields.BottleFieldHandler;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = UseCardAction.class,
        method = "update"
)
public class BlackIndigoniumPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(UseCardAction __instance, AbstractCard ___targetCard) {
        if(BottleFieldHandler.BottleField.playedByIndigonium.get(___targetCard)) {
            ArrayList<AbstractCard> cardsToReset = new ArrayList<AbstractCard>();
            for (AbstractCard c : new ArrayList<AbstractCard>() {{
                addAll(AbstractDungeon.player.hand.group);
                addAll(AbstractDungeon.player.drawPile.group);
                addAll(AbstractDungeon.player.discardPile.group);
            }}) { if(BottleFieldHandler.BottleField.playedByIndigonium.get(c))
                cardsToReset.add(c);
            }
            cardsToReset.add(___targetCard);
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction()
            {
                public void update() {
                    for(AbstractCard c : cardsToReset) BottleFieldHandler.BottleField.playedByIndigonium.set(c, false);
                    this.isDone = true;
                }
            });
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "freeToPlayOnce");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
