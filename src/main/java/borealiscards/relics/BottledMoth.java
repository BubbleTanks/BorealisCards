package borealiscards.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomSavable;
import borealiscards.SpireFields.BottledMothFieldHandler;
import borealiscards.ui.ModConfig;
import borealiscards.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import static borealiscards.BorealisCards.makeID;
import static borealiscards.BorealisCards.relicPath;

public class BottledMoth extends BaseRelic implements CustomBottleRelic, CustomSavable<Integer> {
    public static final String ID = makeID(BottledMoth.class.getSimpleName());
    private boolean cardSelected = true;
    public AbstractCard card = null;

    public BottledMoth() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public AbstractCard getCard() {
        return card.makeCopy();
    }

    private CardGroup mothCards(){
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c : AbstractDungeon.player.masterDeck.getPurgeableCards().group) {
            if (c.cost == 0) {
                retVal.addToBottom(c);
            }
        }
        return retVal;
    }

    public void onEquip() {
        if (!mothCards().group.isEmpty()) {
            cardSelected = false;
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(mothCards(), 1, DESCRIPTIONS[1] + name + LocalizedStrings.PERIOD, false, false, false, false);
        }
    }

    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                BottledMothFieldHandler.BottledMothField.inBottleMoth.set(card, false);
            }
        }
    }

    public void update() {
        super.update();
        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            card = (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottledMothFieldHandler.BottledMothField.inBottleMoth.set(card, true);
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
        this.addToBot(new DrawCardAction(AbstractDungeon.player, 1));
    }

    public boolean canSpawn() {
        return !mothCards().group.isEmpty();
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return BottledMothFieldHandler.BottledMothField.inBottleMoth::get;
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
                BottledMothFieldHandler.BottledMothField.inBottleMoth.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }

    @Override
    protected void loadTexture() {
        int prideMoth = LocalDateTime.now().getMonthValue();
        if(prideMoth == 6 || ModConfig.HappyPrideMoth) {
            this.img = TextureLoader.getTextureNull(relicPath(imageName + "Pride.png"), true);
        } else {
            this.img = TextureLoader.getTextureNull(relicPath(imageName + ".png"), true);
        }
        if (img != null) {
            outlineImg = TextureLoader.getTextureNull(relicPath(imageName + "Outline.png"), true);
            if (outlineImg == null)
                outlineImg = img;
        }
        else {
            ImageMaster.loadRelicImg("Derp Rock", "derpRock.png");
            this.img = ImageMaster.getRelicImg("Derp Rock");
            this.outlineImg = ImageMaster.getRelicOutlineImg("Derp Rock");
        }
    }

    @Override
    public void loadLargeImg() {
        if (largeImg == null) {
            int prideMoth = LocalDateTime.now().getMonthValue();
            if (prideMoth == 6 || ModConfig.HappyPrideMoth) {
                this.largeImg = ImageMaster.loadImage(relicPath("large/" + imageName + "Pride.png"));
            } else {
                this.largeImg = ImageMaster.loadImage(relicPath("large/" + imageName + ".png"));
            }
        }
    }
}