package borealiscards.patches.rarities;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.Bonfire;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

import static borealiscards.BorealisCards.makeID;

public class BonfirePatch {

    public static final String EVENT_ID = makeID(BonfirePatch.class.getSimpleName());

    @SpirePatch2(clz = Bonfire.class, method = "setReward")
    public static class BonfireReward {
        @SpireInsertPatch(rloc = 1, localvars = {"dialog"})
        public static void Reward(AbstractCard.CardRarity rarity, @ByRef String[] dialog) {
            if (rarity.equals(CustomRarity.SHOP)) {
                dialog[0] = dialog[0] + CardCrawlGame.languagePack.getEventString(EVENT_ID).DESCRIPTIONS[0];
                AbstractDungeon.effectList.add(new RainingGoldEffect(100));
                AbstractDungeon.player.gainGold(100);
                AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
            }
            if (rarity.equals(CustomRarity.EXOTIC)) {
                dialog[0] = dialog[0] + CardCrawlGame.languagePack.getEventString(EVENT_ID).DESCRIPTIONS[1];
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.VIOLET.cpy(), true));
                AbstractDungeon.player.increaseMaxHp(10, false);
                AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                //if (!AbstractDungeon.player.hasRelic(BonfireHearth.ID)) {
                //    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,
                //            RelicLibrary.getRelic(BonfireHearth.ID).makeCopy());
                //}
            }
        }
    }
}
