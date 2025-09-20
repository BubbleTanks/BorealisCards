package borealiscards.patches;

import basemod.helpers.CardModifierManager;
import borealiscards.cardmods.FindThePiecesMod;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class FindThePiecesPatch {

    @SpirePatch2(clz = StSLib.class, method = "onCreateCard")
    public static class FindThePieces {
        @SpirePostfixPatch
        public static void FindThePiecesPostfix(AbstractCard c) {
            CardModifierManager.addModifier(c, new FindThePiecesMod());
        }
    }
}
