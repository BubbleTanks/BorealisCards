package borealiscards.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static borealiscards.BorealisCards.makeID;

public class GoldenCookie extends BaseRelic {
    public static final String ID = makeID(GoldenCookie.class.getSimpleName());

    public GoldenCookie() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        flash();
        AbstractDungeon.player.gainGold(200);
    }

    public void onPlayerEndTurn() {
        AbstractDungeon.player.loseGold(25);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}