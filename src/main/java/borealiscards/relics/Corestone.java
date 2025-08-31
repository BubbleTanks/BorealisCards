package borealiscards.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static borealiscards.BorealisCards.makeID;

public class Corestone extends BaseRelic {
    public static final String ID = makeID(Corestone.class.getSimpleName());

    public Corestone() {
        super(ID, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new InvinciblePower(AbstractDungeon.player, 5)));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!grayscale) {
            flash();
            addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, InvinciblePower.POWER_ID, 5));
            grayscale = true;
        }
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}