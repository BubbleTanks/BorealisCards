package borealiscards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static borealiscards.BorealisCards.makeID;

public class JetFuelPower extends BasePower {
    public static final String POWER_ID = makeID(JetFuelPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public JetFuelPower(AbstractCreature owner, int amount) {super(POWER_ID, TYPE, TURN_BASED, owner, amount);}

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER && this.amount > 0) {
            this.flash();
            for(AbstractPower pow : AbstractDungeon.player.powers) {
                if(pow.ID != JetFuelPower.POWER_ID) {
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, pow, amount));
                }
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