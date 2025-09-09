package borealiscards.powers;

import com.megacrit.cardcrawl.actions.defect.DarkImpulseAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static borealiscards.BorealisCards.makeID;

public class PublicVoidPower extends BasePower {
    public static final String POWER_ID = makeID(PublicVoidPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PublicVoidPower(AbstractCreature owner, int amount) {super(POWER_ID, TYPE, TURN_BASED, owner, amount);}

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.cost == 0) {
            this.flash();
            for (int i = 0; i < amount; i++) {
                addToBot(new DarkImpulseAction());
            }
        }
    }

    @Override
    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }
}