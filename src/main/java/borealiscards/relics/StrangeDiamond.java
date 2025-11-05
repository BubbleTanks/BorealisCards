package borealiscards.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.DivinityStance;

import static borealiscards.BorealisCards.makeID;

public class StrangeDiamond extends BaseRelic {
    public static final String ID = makeID(StrangeDiamond.class.getSimpleName());

    public StrangeDiamond() {
        super(ID, RelicTier.RARE, LandingSound.CLINK);
        relicType = RelicType.PURPLE;
    }

    @Override
    public void atBattleStart() {
        counter = 0;
    }

    public void atTurnStart() {
        if (counter >= 10) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ChangeStanceAction(DivinityStance.STANCE_ID));
            counter = 0;
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (AbstractDungeon.player.stance.ID == DivinityStance.STANCE_ID && !(!(counter < 10))) {
            flash();
            counter++;
        }
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}