package borealiscards.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class AddCardToPilePatch {

    public interface AddToPileInterface {
        void onAddCard(CardGroup group, AbstractCard card);
    }

    @SpirePatch(clz = CardGroup.class, method = "addToHand")
    public static class onAddCardPatch {
        @SpirePrefixPatch
        public static void Prefix(CardGroup __instance, AbstractCard __c) {
            if((AbstractDungeon.currMapNode == null || AbstractDungeon.player == null)) return;
            if(__instance == AbstractDungeon.player.limbo) return;
            if(__c instanceof AddToPileInterface && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) ((AddToPileInterface) __c).onAddCard(__instance, __c);
        }
    }
}
