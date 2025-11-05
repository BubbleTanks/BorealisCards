package borealiscards.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomSavable;
import borealiscards.SpireFields.BottleFieldHandler;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.ui.ModConfig;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.function.Predicate;

import static borealiscards.BorealisCards.makeID;

public class BottledBorealis extends BaseRelic implements CustomBottleRelic, CustomSavable<Integer> {
    public static final String ID = makeID(BottledBorealis.class.getSimpleName());
    private boolean cardSelected = true;
    public AbstractCard card = null;

    public BottledBorealis() {
        super(ID, RelicTier.SHOP, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    private CardGroup bottleBorealisCards(){
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c : AbstractDungeon.player.masterDeck.getPurgeableCards().group) {
            if (c.rarity == CustomRarity.SHOP || c.rarity == CustomRarity.EXOTIC) {
                retVal.addToBottom(c);
            }
        }
        return retVal;
    }

    public void onEquip() {
        if (!bottleBorealisCards().isEmpty()) {
            cardSelected = false;
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(bottleBorealisCards(), 1, DESCRIPTIONS[1] + name + LocalizedStrings.PERIOD, false, false, false, false);
        }
    }

    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                BottleFieldHandler.BottleField.inBottleBorealis.set(card, false);
            }
        }
    }

    public void update() {
        super.update();
        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            card = (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottleFieldHandler.BottleField.inBottleBorealis.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            description = DESCRIPTIONS[2] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[3];
            tips.clear();
            tips.add(new PowerTip(name, description));
            initializeTips();
        }
    }

    public void setDescriptionAfterLoading() {
        description = DESCRIPTIONS[2] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[3];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void atBattleStart() {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public boolean canSpawn() {
        if(!ModConfig.Relics) return false;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.rarity == CustomRarity.SHOP || c.rarity == CustomRarity.EXOTIC) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return BottleFieldHandler.BottleField.inBottleBorealis::get;
    }

    @Override
    public Integer onSave()
    {
        return AbstractDungeon.player.masterDeck.group.indexOf(card);
    }

    @Override
    public void onLoad(Integer cardIndex)
    {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (card != null) {
                BottleFieldHandler.BottleField.inBottleBorealis.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }
}