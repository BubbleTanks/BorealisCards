package borealiscards.rewards;


import basemod.abstracts.CustomReward;
import borealiscards.BorealisCards;
import borealiscards.patches.CustomRewardTypes;
import borealiscards.patches.ParanoiaBoxPreventSkip;
import borealiscards.util.TextureLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static borealiscards.BorealisCards.imagePath;
import static borealiscards.BorealisCards.makeID;

public class ParanoiaReward extends CustomReward {
    public static final String ID = makeID("ParanoiaReward");
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;

    public ParanoiaReward() {
        super(TextureLoader.getTexture(imagePath("rewards/ParanoiaReward.png")), TEXT[0], CustomRewardTypes.ER_PARANOIA);
    }
    @Override
    public boolean claimReward() {
        BorealisCards.choosingTransformCard = true;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.cancelButton.hide();
        AbstractDungeon.topPanel.mapHb.move(ParanoiaBoxPreventSkip.ReplayRewardSkipPositionPatch.HIDE_X, AbstractDungeon.topPanel.mapHb.cY);

        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard card : AbstractDungeon.player.masterDeck.getPurgeableCards().group) {
            tmp.addToTop(card);
        }

        if (tmp.group.isEmpty()) {
            BorealisCards.choosingTransformCard = false;
        } else {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 3, TEXT[1], false, false, false, false);
        }
        return true;
    }
}
