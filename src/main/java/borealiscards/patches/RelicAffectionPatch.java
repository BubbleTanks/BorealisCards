package borealiscards.patches;

import basemod.BaseMod;
import basemod.Pair;
import basemod.helpers.CardModifierManager;
import borealiscards.cardmods.DoubleDamageOnce;
import borealiscards.relics.MarketGardener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.function.Predicate;

public class RelicAffectionPatch {
    private static AbstractRelic mGar = new MarketGardener();

    @SpirePatch(clz = AbstractCard.class, method = "renderCard")
    public static class RenderPatch {
        public static void Postfix(AbstractCard card, SpriteBatch sb, boolean b1, boolean b2) {
            if (Loader.isModLoaded("researchersmod")) return;
            if (CardModifierManager.hasModifier(card, DoubleDamageOnce.ID)) {
                float numRelics = 0;
                float scale = 1.5f * Settings.scale;
                boolean shouldAppear = !combatCheck() || !AbstractDungeon.player.hand.contains(card);
                if(!combatCheck()) {
                    for (Pair<Predicate<AbstractCard>, AbstractRelic> info : BaseMod.getBottledRelicList())
                        if (info.getKey().test(card)) numRelics++;
                    if (card.inBottleFlame) numRelics++;
                }
                if(Loader.isModLoaded("mintyspire")) {
                    shouldAppear = true;
                    if(!AbstractDungeon.player.masterDeck.contains(card)) {
                        scale = Settings.scale;
                        if (mintySpire.patches.cards.RelicAffectionPatch.cardFields.isPenAff.get(card))
                            numRelics += 0.3f;
                        if (mintySpire.patches.cards.RelicAffectionPatch.cardFields.isNecroAff.get(card))
                            numRelics += 0.5f;
                    }
                }
                if(shouldAppear){
                    mGar.currentX = card.current_x + 390.0f * card.drawScale / 3.0f * Settings.scale;
                    mGar.currentY = card.current_y + 546.0f * card.drawScale / 3.0f * Settings.scale - AbstractRelic.PAD_X * numRelics * scale * Settings.scale * card.drawScale;
                    mGar.scale = card.drawScale * scale;
                    mGar.renderOutline(sb, false);
                    mGar.render(sb);
                }
            }
        }
        private static boolean combatCheck() {
            if(AbstractDungeon.getCurrMapNode() != null) {
                AbstractRoom r = AbstractDungeon.getCurrRoom();
                if(r != null) {
                    return r.phase == AbstractRoom.RoomPhase.COMBAT;
                }
            }
            return false;
        }
    }
}
