package borealiscards.rewards;


import basemod.abstracts.CustomReward;
import borealiscards.cards.watcher.AmbrosialSpice;
import borealiscards.util.TextureLoader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static borealiscards.BorealisCards.imagePath;
import static borealiscards.BorealisCards.makeID;

public class SpiceReward extends CustomReward {
    public static final String ID = makeID("SpiceReward");
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;

    public SpiceReward() {
        super(TextureLoader.getTexture(imagePath("rewards/SpiceReward.png")), TEXT[0], RewardType.CARD);
        cards = new ArrayList<>();
        cards.add(new AmbrosialSpice());
    }
    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[1]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }

        return false;
    }
}
