package borealiscards.patches;

import borealiscards.SpireFields.BottleFieldHandler;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.AbstractList;
import java.util.ArrayList;

public class BottleSinBottomDeckPatch {

    private static AbstractList<AbstractCard> list;

    @SpirePatch2(clz = CardGroup.class, method = "initializeDeck")
    public static class BottleSinNotInnatePatch {
        @SpireInsertPatch(locator = Locator1.class, localvars = {"copy"})
        public static void removeCardsFromList(CardGroup copy) {

            list = new ArrayList<>();

            for (AbstractCard c : copy.group) {
                if (BottleFieldHandler.BottleField.inBottleSin.get(c)) {
                    list.add(c);
                }
            }

            for (AbstractCard c : list) {
                copy.group.remove(c);
            }

        }

        @SpireInsertPatch(locator = Locator2.class)
        public static void addCardsToDeck(CardGroup __instance) {
            for (AbstractCard c : list) {
                __instance.addToBottom(c);
            }
        }
        
        private static class Locator1 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(CardGroup.class, "group");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

        private static class Locator2 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "masterHandSize");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
    
    
}
