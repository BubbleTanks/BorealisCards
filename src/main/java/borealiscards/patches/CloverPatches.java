package borealiscards.patches;

import borealiscards.relics.FiftyEightLeafClover;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

public class CloverPatches {

    @SpirePatch(
            clz= RewardItem.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {}
    )
    public static class RewardPatch {
        @SpirePrefixPatch
        public static void Prefix(RewardItem __instance, @ByRef boolean[] ___isBoss) {
            if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite && AbstractDungeon.player.hasRelic(FiftyEightLeafClover.ID))
                ___isBoss[0] = true;
        }
    }

    @SpirePatch(
            clz = RewardItem.class,
            method = "render"
    )
    public static class RareRenderPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess m) throws CannotCompileException {
                    if (m.getClassName().equals(RewardItem.class.getName()) && m.getFieldName().equals("isBoss")) {
                        m.replace("$_ = $proceed($$) || (" + RareRenderPatch.class.getName() + ".Do());");
                    }
                }
            };
        }

        @SuppressWarnings("unused")
        public static boolean Do() {
            if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite && AbstractDungeon.player.hasRelic(FiftyEightLeafClover.ID))
                return true;
            return false;
        }
    }


    @SpirePatch(
            clz= AbstractDungeon.class,
            method = "rollRarity",
            paramtypez = {Random.class}
    )
    public static class RarityPatch {
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard.CardRarity> Prefix() {
            if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite && AbstractDungeon.player.hasRelic(FiftyEightLeafClover.ID))
                return SpireReturn.Return(AbstractCard.CardRarity.RARE);
            return SpireReturn.Continue();
        }
    }

}
