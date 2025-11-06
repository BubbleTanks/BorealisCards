package borealiscards.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.stances.CalmStance;

import static borealiscards.BorealisCards.makeID;

public class HeartbeatPower extends BasePower {
    public static final String POWER_ID = makeID(HeartbeatPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private static boolean triggeredThisTurn;

    public HeartbeatPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
        triggeredThisTurn = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && !triggeredThisTurn) {
            addToBot(new ChangeStanceAction(CalmStance.STANCE_ID));
            flash();
            triggeredThisTurn = true;
        }
    }

    public void atEndOfRound() {
        triggeredThisTurn = false;
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0]);
    }
}