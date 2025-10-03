package borealiscards.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomSavable;
import borealiscards.SpireFields.BottleFieldHandler;
import borealiscards.cards.PrismaDisruption;
import borealiscards.ui.ModConfig;
import borealiscards.util.TwoInteger;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.function.Predicate;

import static borealiscards.BorealisCards.makeID;

public class BlackIndigonium extends BaseRelic implements CustomBottleRelic, CustomSavable<TwoInteger> {
    public static final String ID = makeID(BlackIndigonium.class.getSimpleName());
    private boolean cardSelected = true;
    public AbstractCard card = null;
    public AbstractCard card2 = null;
    public boolean indigoVar = false;

    public BlackIndigonium() {
        super(ID, RelicTier.BOSS, LandingSound.SOLID);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + john();
    }

    public void onEquip() {
        if (AbstractDungeon.player.masterDeck.getPurgeableCards().size() > 1) {
            cardSelected = false;
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, DESCRIPTIONS[3] + name + LocalizedStrings.PERIOD, false, false, false, false);
        }

    }

    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                BottleFieldHandler.BottleField.inBlackIndigonium.set(card, false);
            }
        }
    }

    public void onUseCard(AbstractCard c1, UseCardAction action) {
        if (BottleFieldHandler.BottleField.inBlackIndigonium.get(c1) && !BottleFieldHandler.BottleField.playedByIndigonium.get(c1)) {
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            for (AbstractCard c2 : new ArrayList<AbstractCard>() {{
                addAll(AbstractDungeon.player.hand.group);
                addAll(AbstractDungeon.player.drawPile.group);
                addAll(AbstractDungeon.player.discardPile.group);
            }}) {
                if (BottleFieldHandler.BottleField.inBlackIndigonium.get(c2) && c2 != c1) {
                    BottleFieldHandler.BottleField.playedByIndigonium.set(c2, true);
                    AbstractDungeon.player.hand.group.remove(c2);
                    AbstractDungeon.player.drawPile.group.remove(c2);
                    AbstractDungeon.player.discardPile.group.remove(c2);
                    AbstractDungeon.getCurrRoom().souls.remove(c2);
                    AbstractDungeon.player.limbo.group.add(c2);
                    c2.targetAngle = 0.0F;
                    c2.lighten(false);
                    c2.drawScale = 0.12F;
                    c2.targetDrawScale = 0.75F;
                    c2.applyPowers();
                    this.addToTop(new NewQueueCardAction(c2, true, false, true));
                    this.addToTop(new UnlimboAction(c2));
                }
            }
        }
    }

    public void update() {
        super.update();
        if (!cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 2) {
            cardSelected = true;
            card = (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            card2 = (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(1);
            BottleFieldHandler.BottleField.inBlackIndigonium.set(card, true);
            BottleFieldHandler.BottleField.inBlackIndigonium.set(card2, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            description = DESCRIPTIONS[4] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[5] + FontHelper.colorString(card2.name, "y") + DESCRIPTIONS[6];
            tips.clear();
            tips.add(new PowerTip(name, description));
            initializeTips();
            if(ModConfig.ColorsBlack) {
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new PrismaDisruption(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                UnlockTracker.markCardAsSeen(PrismaDisruption.ID);
            } else AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new AscendersBane(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        }
    }

    public void setDescriptionAfterLoading() {
        description = DESCRIPTIONS[4] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[5] + FontHelper.colorString(card2.name, "y") + DESCRIPTIONS[6];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    private String john() {
        if(ModConfig.ColorsBlack) {
            return DESCRIPTIONS[1];
        }
        return DESCRIPTIONS[2];
    }

    public boolean canSpawn() {
        if(!ModConfig.Relics) return false;
        if(AbstractDungeon.player.masterDeck.getPurgeableCards().size() > 1) return true;
        return false;
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return BottleFieldHandler.BottleField.inBlackIndigonium::get;
    }

    @Override
    public TwoInteger onSave()
    {
        return new TwoInteger(AbstractDungeon.player.masterDeck.group.indexOf(card), AbstractDungeon.player.masterDeck.group.indexOf(card2));
    }

    @Override
    public void onLoad(TwoInteger cardIndex)
    {
        if (cardIndex == null) {
            return;
        }
        int var1 = cardIndex.indigo1;
        int var2 = cardIndex.indigo2;
        if (var1 >= 0 && var1 < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(var1);
            if (card != null) {
                BottleFieldHandler.BottleField.inBlackIndigonium.set(card, true);
            }
        }
        if (var2 >= 0 && var2 < AbstractDungeon.player.masterDeck.group.size()) {
            card2 = AbstractDungeon.player.masterDeck.group.get(var2);
            if (card2 != null) {
                BottleFieldHandler.BottleField.inBlackIndigonium.set(card2, true);
                setDescriptionAfterLoading();
            }
        }
    }
}